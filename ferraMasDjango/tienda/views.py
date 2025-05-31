from django.shortcuts import render
from django.http import HttpResponse
import requests

def ver_home(request):
    return render(request, 'verHome.html') 

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


def obtener_sedes():
    url="http://localhost:8089/api/sedes/"
    try:
        response = requests.get(url)
        data=response.json()
        return data
    except Exception as e:
        return None

def ver_sedes(request):
    sedes = obtener_sedes()
    context = {'datos': sedes}
    return render(request, 'ver_sedes.html', context)

def seleccionar_sede(request):
    if request.method == 'POST':
        sede_id = request.POST.get('sede_id')
        # Almacenar la sede seleccionada en la sesión
        request.session['sede_seleccionada'] = sede_id
        return redirect('home')
    
    sedes = obtener_sedes()
    context = {'sedes': sedes}
    return render(request, 'seleccionar_sede.html', context)

def cambiar_sede_ajax(request):
    if request.method == 'POST':
        try:
            data = json.loads(request.body)
            sede_id = data.get('sede_id')
            
            if sede_id:
                # Obtener información de la sede seleccionada
                sedes = obtener_sedes()
                sede_seleccionada = None
                
                for sede in sedes:
                    if str(sede['idsede']) == str(sede_id):
                        sede_seleccionada = sede
                        break
                
                if sede_seleccionada:
                    request.session['sede_seleccionada'] = sede_id
                    request.session['sede_nombre'] = sede_seleccionada['nombresede']
                    
                    return JsonResponse({
                        'success': True,
                        'message': f'Sede cambiada a {sede_seleccionada["nombresede"]}',
                        'sede_nombre': sede_seleccionada['nombresede']
                    })
                else:
                    return JsonResponse({'success': False, 'message': 'Sede no encontrada'})
            else:
                return JsonResponse({'success': False, 'message': 'ID de sede no válido'})
                
        except Exception as e:
            return JsonResponse({'success': False, 'message': 'Error al cambiar sede'})
    
    return JsonResponse({'success': False, 'message': 'Método no permitido'}, status=405)
