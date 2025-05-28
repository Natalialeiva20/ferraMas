from django.shortcuts import render
from django.http import HttpResponse
import requests

# Create your views here.
def obtener_empleados():
    url="http://localhost:8089/api/empleados/"
    try:
        response = requests.get(url)
        data=response.json()
        return data
    except Exception as e:
        return None
    
def ver_empleados(request):
    empleados = obtener_empleados()
    context = {'datos': empleados}
    return render(request, 'ver_empleados.html', context)


def obtener_comunas():
    url="http://localhost:8089/api/comunas/"
    try:
        response = requests.get(url)
        data=response.json()
        return data
    except Exception as e:
        return None
    
def ver_comunas(request):
    comunas = obtener_comunas()
    context = {'datos': comunas}
    return render(request, 'ver_comunas.html', context)

def obtenerCatalogo():
    try:
        url = "http://localhost:8089/api/productos/"
        response = requests.get(url)
        data = response.json()
        return data
    except Exception as e:
        return None
    
def verCatalogo(request):
    catalogo = obtenerCatalogo()
    context = {'datos': catalogo}
    return render(request, 'ver_catalogo.html', context)

def obtenerProductos():
    try:
        url = "http://localhost:8089/api/productos/"
        # 
        response = requests.get(url)
        response.raise_for_status()
        data = response.json()
        print(f"API Response: {data}")
        return data
    except Exception as e:
        return None
    
def verProductos(request):
    productos = obtenerProductos()
    context = {'datos': productos}
    return render(request, 'ver_producto.html', context)

def obtenerProductoPorId(producto_id):
    try:
        url = f"http://localhost:8089/api/productos/{producto_id}"
        response = requests.get(url)
        response.raise_for_status()
        data = response.json()
        print(f"API Response for product {producto_id}: {data}")
        return data
    except requests.exceptions.HTTPError as e:
        if e.response.status_code == 404:
            print(f"Producto con ID {producto_id} no encontrado")
            return None
        print(f"Error HTTP: {e}")
        return None
    except Exception as e:
        print(f"Error obteniendo producto {producto_id}: {e}")
        return None

def verProductoDetalle(request, producto_id):
    producto = obtenerProductoPorId(producto_id)
    if producto is None:
        context = {'error': 'Producto no encontrado', 'producto_id': producto_id}
        return render(request, 'producto_no_encontrado.html', context)
    
    context = {'producto': producto}
    return render(request, 'ver_producto_detalle.html', context)
