from django.shortcuts import render
from django.http import JsonResponse
import requests
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import redirect
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
                'subtotal': subtotal
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
    return render(request, 'carrito/ver_carrito.html', {'items': items, 'total': total, 'warning': warning, 'error': error})

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
            return render(request, 'carrito/ver_carrito.html', {'items': [], 'total': 0, 'error': 'Transbank SDK no instalado'})
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
            return render(request, 'carrito/webpay_redirect.html', {'url': url, 'token': token})
        else:
            return render(request, 'carrito/ver_carrito.html', {'items': [], 'total': 0, 'error': 'Error iniciando pago'})
    return redirect('cart:ver_carrito')

@csrf_exempt
def retorno_pago(request):
    token = request.POST.get('token_ws')
    if not token:
        return render(request, 'carrito/ver_carrito.html', {'error': 'Token de pago no recibido'})
    # Transbank test credentials
    commerce_code = '597055555532'
    api_key = '597055555532'
    if Transaction is None:
        return render(request, 'carrito/ver_carrito.html', {'error': 'Transbank SDK no instalado'})
    tx = Transaction(commerce_code=commerce_code, api_key=api_key, environment='TEST')
    response = tx.commit(token)
    if response['status'] == 'AUTHORIZED':
        # Limpiar carrito
        request.session['cart'] = {}
        return render(request, 'carrito/pago_exitoso.html', {'response': response})
    else:
        return render(request, 'carrito/pago_fallido.html', {'response': response})