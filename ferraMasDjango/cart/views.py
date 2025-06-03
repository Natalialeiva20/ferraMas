from django.shortcuts import render
from django.http import JsonResponse
import requests

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
    return render(request, 'carrito/ver_carrito.html', {'items': items, 'total': total})