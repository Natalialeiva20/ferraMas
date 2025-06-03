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
    from transbank.common.options import WebpayOptions
    
    # Configuración de integración (solo para pruebas)
    commerce_code = '597055555532'
    api_key = '579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C'
    options = WebpayOptions(commerce_code, api_key, integration_type='TEST')
    transaction = Transaction(options)
    
    TRANSBANK_AVAILABLE = True
    print("Transbank SDK configurado exitosamente para pruebas con WebpayOptions")
except ImportError as e:
    print(f"Transbank SDK no está disponible: {e}")
    TRANSBANK_AVAILABLE = False
    transaction = None
except Exception as e:
    print(f"Error al configurar Transbank: {e}")
    TRANSBANK_AVAILABLE = False
    transaction = None

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
        print(f"API response for {product_id}: {response.status_code} {response.text}")
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

@csrf_exempt
def iniciar_pago(request):
    if not TRANSBANK_AVAILABLE or transaction is None:
        return render(request, 'carrito/ver_carrito.html', {'items': [], 'total': 0, 'error': 'Transbank SDK no configurado'})
    
    if request.method == 'POST':
        cart = request.session.get('cart', {})
        if not cart:
            return render(request, 'carrito/ver_carrito.html', {'items': [], 'total': 0, 'error': 'El carrito está vacío'})
        
        # Calcular el total y armar el carrito para la sesión
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
                    'id': product_id,
                    'nombre': product.get('nombre'),
                    'precio': precio,
                    'cantidad': quantity,
                    'subtotal': subtotal
                })
        if total <= 0:
            return render(request, 'carrito/ver_carrito.html', {'items': items, 'total': total, 'error': 'El total del carrito es $0. No se puede pagar.'})

        # Generar datos para Transbank
        import uuid
        from datetime import datetime
        buy_order = f"orden_{uuid.uuid4().hex[:8]}_{int(datetime.now().timestamp())}"
        session_id = f"session_{uuid.uuid4().hex[:8]}"
        amount = int(total)
        return_url = request.build_absolute_uri(reverse('cart:retorno_pago'))

        # Guardar datos de la orden en sesión
        request.session['orden_datos'] = {
            'buy_order': buy_order,
            'session_id': session_id,
            'carrito': items,
            'total': total,
        }

        # Crear transacción con Transbank
        response = transaction.create(buy_order, session_id, amount, return_url)
        print("Transbank response:", response)  # <-- Pega esta línea aquí para depuración
        if 'token' in response and 'url' in response:
            request.session['orden_datos']['token'] = response['token']
            return render(request, 'carrito/webpay_redirect.html', {'url': response['url'], 'token': response['token']})
        else:
            error_msg = response.get('error_message', 'Error iniciando pago')
            return render(request, 'carrito/ver_carrito.html', {'items': items, 'total': total, 'error': error_msg})

@csrf_exempt
def retorno_pago(request):
    if not TRANSBANK_AVAILABLE or transaction is None:
        return render(request, 'carrito/ver_carrito.html', {'error': 'Transbank SDK no configurado'})
    
    token = request.GET.get('token_ws') or request.POST.get('token_ws')
    if not token:
        return render(request, 'carrito/ver_carrito.html', {'error': 'Token de pago no recibido'})
    
    try:
        result = transaction.commit(token)
        orden_datos = request.session.get('orden_datos', {})
        if result.get('status') == 'AUTHORIZED':
            # Limpiar carrito y sesión de orden
            request.session['cart'] = {}
            if 'orden_datos' in request.session:
                del request.session['orden_datos']
            return render(request, 'carrito/pago_exitoso.html', {
                'response': result,
                'orden': orden_datos.get('buy_order'),
                'total': orden_datos.get('total'),
                'carrito': orden_datos.get('carrito', [])
            })
        else:
            return render(request, 'carrito/pago_fallido.html', {'response': result})
    except Exception as e:
        return render(request, 'carrito/pago_fallido.html', {'response': {'error': str(e)}})