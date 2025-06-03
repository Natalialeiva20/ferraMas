# IMPORTS CORREGIDOS
from django.shortcuts import render, redirect
from django.http import JsonResponse
from django.contrib import messages  # ← CORREGIDO: era pyexpat.errors
import requests
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
from django.urls import reverse
import uuid

# Transbank SDK import
try:
    from transbank.webpay.webpay_plus.transaction import Transaction
except ImportError:
    Transaction = None  # Will error if not installed

def add_to_cart(request, product_id):
    cart = request.session.get('cart', {})
    cart[product_id] = cart.get(product_id, 0) + 1
    request.session['cart'] = cart
    total_items = sum(cart.values())
    return JsonResponse({'total_items': total_items})

def ver_carrito(request):
    cart = request.session.get('cart', {})
    items = []
    total = 0
    for product_id, quantity in cart.items():
        url = f"http://localhost:8089/api/productos/{product_id}"
        response = requests.get(url)
        if response.status_code == 200:
            product = response.json()
            precio = float(product.get('precio', 0))
            subtotal = precio * quantity
            total += subtotal
            items.append({
                'nombre': product.get('nombre'),
                'precio': precio,
                'cantidad': quantity,
                'subtotal': subtotal,
                'clave': product_id  # ← AGREGADO: necesario para el botón eliminar
            })
    
    # Depuración
    print('DEBUG cart:', cart)
    print('DEBUG items:', items)
    print('DEBUG total:', total)
    
    # Si hay items pero el total es 0, mostrar advertencia
    warning = None
    if items and total == 0:
        warning = 'El total del carrito es $0. No se puede pagar.'
    error = None
    return render(request, 'ver_carrito.html', {'items': items, 'total': total, 'warning': warning, 'error': error})

def iniciar_pago(request):
    if request.method == 'POST':
        cart = request.session.get('cart', {})
        if not cart:
            return redirect('cart:ver_carrito')
        # Calcular el total
        total = 0
        for product_id, quantity in cart.items():
            url = f"http://localhost:8089/api/productos/{product_id}"
            response = requests.get(url)
            if response.status_code == 200:
                product = response.json()
                precio = float(product.get('precio', 0))
                total += precio * quantity
        if total <= 0:
            return redirect('cart:ver_carrito')
        # Transbank test credentials
        commerce_code = '597055555532'
        api_key = '597055555532'
        return_url = request.build_absolute_uri(reverse('cart:retorno_pago'))
        buy_order = str(uuid.uuid4())[:26]
        session_id = str(uuid.uuid4())
        amount = int(total)
        if Transaction is None:
            return render(request, 'ver_carrito.html', {'items': [], 'total': 0, 'error': 'Transbank SDK no instalado'})
        tx = Transaction(commerce_code=commerce_code, api_key=api_key, environment='TEST')
        response = tx.create(buy_order, session_id, amount, return_url)
        if response['status'] == 'CREATED':
            url = response['url']
            token = response['token']
            # Guardar datos de la orden en sesión para validación posterior
            request.session['webpay_order'] = {
                'buy_order': buy_order,
                'session_id': session_id,
                'amount': amount,
                'cart': cart
            }
            return render(request, 'webpay_redirect.html', {'url': url, 'token': token})
        else:
            return render(request, 'ver_carrito.html', {'items': [], 'total': 0, 'error': 'Error iniciando pago'})
    return redirect('cart:ver_carrito')

@csrf_exempt
def retorno_pago(request):
    token = request.POST.get('token_ws')
    if not token:
        return render(request, 'ver_carrito.html', {'error': 'Token de pago no recibido'})
    # Transbank test credentials
    commerce_code = '597055555532'
    api_key = '597055555532'
    if Transaction is None:
        return render(request, 'ver_carrito.html', {'error': 'Transbank SDK no instalado'})
    tx = Transaction(commerce_code=commerce_code, api_key=api_key, environment='TEST')
    response = tx.commit(token)
    if response['status'] == 'AUTHORIZED':
        # Limpiar carrito
        request.session['cart'] = {}
        return render(request, 'pago_exitoso.html', {'response': response})
    else:
        return render(request, 'pago_fallido.html', {'response': response})

# ← FUNCIONES CORREGIDAS:
def remove_from_cart(request, clave_producto):
    """Eliminar producto del carrito"""
    cart = request.session.get('cart', {})
    
    if clave_producto in cart:
        # Obtener nombre del producto antes de eliminarlo
        try:
            url = f"http://localhost:8089/api/productos/{clave_producto}"
            response = requests.get(url)
            if response.status_code == 200:
                product = response.json()
                producto_nombre = product.get('nombre', 'Producto')
            else:
                producto_nombre = 'Producto'
        except:
            producto_nombre = 'Producto'
        
        # Eliminar del carrito
        del cart[clave_producto]
        request.session['cart'] = cart
        request.session.modified = True
        
        messages.success(request, f'"{producto_nombre}" eliminado del carrito')
    else:
        messages.warning(request, 'El producto no está en el carrito')
    
    return redirect('cart:ver_carrito')

def clear_cart(request):
    """Vaciar todo el carrito"""
    if request.method == 'POST':  # ← AGREGADO: solo por POST para seguridad
        request.session['cart'] = {}
        request.session.modified = True
        messages.success(request, 'Carrito vaciado completamente')
    
    return redirect('cart:ver_carrito')