import datetime
from django.shortcuts import render
from django.http import JsonResponse
import requests
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import redirect
from django.conf import settings
from django.urls import reverse
import uuid
from datetime import datetime

try:
    from transbank.webpay.webpay_plus.transaction import Transaction
    from transbank.common.options import WebpayOptions
    
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

    print('DEBUG cart:', cart)
    print('DEBUG items:', items)
    print('DEBUG total:', total)

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

        import uuid
        from datetime import datetime
        buy_order = f"orden_{uuid.uuid4().hex[:8]}_{int(datetime.now().timestamp())}"
        session_id = f"session_{uuid.uuid4().hex[:8]}"
        amount = int(total)
        return_url = request.build_absolute_uri(reverse('cart:retorno_pago'))

        request.session['orden_datos'] = {
            'buy_order': buy_order,
            'session_id': session_id,
            'carrito': items,
            'total': total,
        }

        response = transaction.create(buy_order, session_id, amount, return_url)
        print("Transbank response:", response) 
        if 'token' in response and 'url' in response:
            request.session['orden_datos']['token'] = response['token']
            return render(request, 'carrito/webpay_redirect.html', {'url': response['url'], 'token': response['token']})
        else:
            error_msg = response.get('error_message', 'Error iniciando pago')
            return render(request, 'carrito/ver_carrito.html', {'items': items, 'total': total, 'error': error_msg})


@csrf_exempt
def retorno_pago(request):
    print("=== INICIO retorno_pago ===")
    if not TRANSBANK_AVAILABLE or transaction is None:
        print("ERROR: Transbank SDK no configurado")
        return render(request, 'carrito/ver_carrito.html', {'error': 'Transbank SDK no configurado'})
    
    token = request.GET.get('token_ws') or request.POST.get('token_ws')
    print(f"Token recibido: {token}")
    if not token:
        print("ERROR: Token de pago no recibido")
        return render(request, 'carrito/ver_carrito.html', {'error': 'Token de pago no recibido'})
    
    try:
        print("Intentando commit de Transbank...")
        result = transaction.commit(token)
        print("Resultado commit Transbank:", result)
        orden_datos = request.session.get('orden_datos', {})
        print("Datos de la orden recuperados de sesión:", orden_datos)

        if result.get('status') == 'AUTHORIZED':
            print("Pago autorizado, creando venta...")
            try:
                formapago_response = requests.get("http://localhost:8089/api/formas-pago/2")
                if formapago_response.status_code != 200:
                    print("ERROR: No se pudo obtener FormaPago")
                    return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error configurando pago'}})
                formapago = formapago_response.json()
                
                formapago_limpio = {
                    "idformapago": formapago.get("idformapago"),
                    "nombreformapago": formapago.get("nombreformapago"),
                    "descripcion": formapago.get("descripcion")
                }
                print("FormaPago limpio:", formapago_limpio)
            except Exception as e:
                print(f"ERROR obteniendo FormaPago: {e}")
                return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error obteniendo forma de pago'}})
            
            rutcliente = "12345678-9"
            try:
                cliente_response = requests.get(f"http://localhost:8089/api/clientes/{rutcliente}")
                if cliente_response.status_code == 404:
                    print("Cliente no existe, creando cliente temporal...")
                    cliente_payload = {
                        "rutcliente": rutcliente,
                        "nombrecliente": "Cliente",
                        "apellidocliente": "Temporal",
                        "comuna": {"idcomuna": 1}
                    }
                    cliente_create_response = requests.post("http://localhost:8089/api/clientes/", json=cliente_payload)
                    if cliente_create_response.status_code == 201:
                        cliente = cliente_create_response.json()
                        print("Cliente temporal creado:", cliente)
                    else:
                        print("ERROR creando cliente:", cliente_create_response.text)
                        return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error creando cliente'}})
                elif cliente_response.status_code == 200:
                    cliente = cliente_response.json()
                    print("Cliente existente obtenido:", cliente)
                else:
                    print("ERROR obteniendo cliente:", cliente_response.text)
                    return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error obteniendo cliente'}})
            except Exception as e:
                print(f"ERROR con cliente: {e}")
                return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error procesando cliente'}})
            
            idsede = request.session.get('sede_seleccionada', 1)
            try:
                sede_response = requests.get(f"http://localhost:8089/api/sedes/{idsede}")
                if sede_response.status_code != 200:
                    print("ERROR obteniendo sede:", sede_response.text)
                    return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error obteniendo sede'}})
                sede = sede_response.json()
                print("Sede obtenida:", sede)
            except Exception as e:
                print(f"ERROR obteniendo sede: {e}")
                return render(request, 'carrito/pago_fallido.html', {'response': {'error': 'Error obteniendo sede'}})

            nuevo_numero = obtener_ultimo_numerodocumento() + 1

            venta_payload = {
                "tipodocumento": "BOLETA",
                "fechaventa": datetime.now().strftime("%Y-%m-%d"),
                "totalventa": int(orden_datos.get('total', 0)),
                "formapago": {"idformapago": formapago_limpio["idformapago"]},
                "cliente": {"rutcliente": cliente["rutcliente"]},
                "sede": {"idsede": sede["idsede"]}
            }
            
            print("Payload para /api/ventas/:", venta_payload)
            venta_response = requests.post("http://localhost:8089/api/ventas/", json=venta_payload)
            print("Respuesta de /api/ventas/:", venta_response.status_code, venta_response.text)

            if venta_response.status_code == 201:
                venta = venta_response.json()
                print("Venta creada:", venta)
                numerodocumento = venta.get("numerodocumento", nuevo_numero)
                print("Numero documento obtenido:", numerodocumento)

                if numerodocumento is not None:
                    for item in orden_datos.get('carrito', []):
                        try:
                            producto_response = requests.get(f"http://localhost:8089/api/productos/{item['id']}")
                            if producto_response.status_code == 200:
                                producto = producto_response.json()
                                
                                detalle_payload = {
                                    "numerodocumento": numerodocumento,
                                    "idproducto": item['id'],
                                    "cantidad": item['cantidad'],
                                    "venta": venta,
                                    "producto": producto,
                                    "empleado": None
                                }
                                print("Payload para /api/detalle-venta/:", detalle_payload)
                                detalle_response = requests.post("http://localhost:8089/api/detalle-venta/", json=detalle_payload)
                                print("Detalle venta:", detalle_response.status_code, detalle_response.text)
                                
                                if detalle_response.status_code != 201:
                                    print(f"ERROR guardando detalle de venta para producto {item['id']}: {detalle_response.text}")
                            else:
                                print(f"ERROR: No se pudo obtener producto {item['id']}")
                        except Exception as e:
                            print(f"ERROR procesando detalle para producto {item['id']}: {e}")
                else:
                    print("ERROR: No se obtuvo numerodocumento de la venta:", venta)
            else:
                print("ERROR guardando venta:", venta_response.text)
                return render(request, 'carrito/pago_fallido.html', {'response': result})
            
            print("Limpiando carrito y sesión de orden...")
            request.session['cart'] = {}
            if 'orden_datos' in request.session:
                del request.session['orden_datos']
            print("=== FIN retorno_pago (éxito) ===")

            return render(request, 'carrito/pago_exitoso.html', {
                'response': result,
                'orden': orden_datos.get('buy_order'),
                'total': orden_datos.get('total'),
                'carrito': orden_datos.get('carrito', []),
                'numerodocumento': nuevo_numero
            })
        else:
            print("ERROR: Pago no autorizado o fallido:", result)
            print("=== FIN retorno_pago (fallido) ===")
            return render(request, 'carrito/pago_fallido.html', {'response': result})
    except Exception as e:
        print("EXCEPCIÓN en retorno_pago:", str(e))
        print("=== FIN retorno_pago (excepción) ===")
        return render(request, 'carrito/pago_fallido.html', {'response': {'error': str(e)}})
    
def obtener_ultimo_numerodocumento():
    try:
        resp = requests.get("http://localhost:8089/api/ventas/ultimo-numero/")
        if resp.status_code == 200:
            data = resp.json()
            return data.get("ultimo_numero", 0)
        else:
            print("No se pudo obtener el último numerodocumento, usando 0")
            return 0
    except Exception as e:
        print(f"Error obteniendo último numerodocumento: {e}")
        return 0