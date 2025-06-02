import json
from django.shortcuts import redirect, render
from django.http import HttpResponse, JsonResponse
import requests


def get_context_with_sedes():
    sedes = obtener_sedes()
    return {'sedes': sedes or []}

def filtrarProductosPorSede(sede_id):
    try:
        todos_productos = obtenerProductos()
        if not todos_productos:
            return []
        
        productos_filtrados = [
            producto for producto in todos_productos 
            if str(producto.get('idsede', '')) == str(sede_id)
        ]
        
        print(f"Productos filtrados para sede {sede_id}: {len(productos_filtrados)}")
        return productos_filtrados
        
    except Exception as e:
        print(f"Error filtrando productos por sede: {e}")
        return []

def obtenerProductosPorSede(sede_id):
    try:
        url = f"http://localhost:8089/api/productos/sedes/{sede_id}"
        response = requests.get(url)
        
        if response.status_code == 200:
            data = response.json()
            print(f"Productos por sede {sede_id}: {len(data) if data else 0}")
            return data
        elif response.status_code == 404:
            # Si no existe endpoint específico, obtener todos y filtrar
            print(f"Endpoint /sede/{sede_id} no encontrado, filtrando localmente")
            return filtrarProductosPorSede(sede_id)
        else:
            print(f"Error al obtener productos por sede: {response.status_code}")
            return filtrarProductosPorSede(sede_id)
            
    except Exception as e:
        print(f"Error obteniendo productos por sede {sede_id}: {e}")
        return filtrarProductosPorSede(sede_id)


def ver_home(request):
    context = get_context_with_sedes()
    # Agregar productos filtrados por sede para mostrar en home
    sede_seleccionada = request.session.get('sede_seleccionada')
    if sede_seleccionada:
        productos_destacados = obtenerProductosPorSede(sede_seleccionada)
        context['productos_destacados'] = productos_destacados[:6] if productos_destacados else []
    return render(request, 'verHome.html', context) 

def obtener_empleados():
    url="http://localhost:8089/api/empleados/"
    try:
        response = requests.get(url)
        data=response.json()
        return data
    except Exception as e:
        return None
    
def ver_empleados(request):
    context = get_context_with_sedes()
    empleados = obtener_empleados()
    context['datos'] = empleados
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
    context = get_context_with_sedes()
    sede_seleccionada = request.session.get('sede_seleccionada')
    
    if sede_seleccionada:
        productos = obtenerProductosPorSede(sede_seleccionada)
        sede_nombre = request.session.get('sede_nombre', 'Sede seleccionada')
        context['sede_filtro'] = {
            'id': sede_seleccionada,
            'nombre': sede_nombre
        }
    else:
        productos = obtenerProductos()
    
    context['datos'] = productos
    return render(request, 'ver_Catalogo.html', context)

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
    context = get_context_with_sedes()
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
        return render(request, 'producto_no_encontrado.html', {'producto_id': producto_id})
    
    # Forzamos el nombre de la imagen
    producto['imagen'] = f"{producto_id.lower()}.jpg"
    print("Nombre de imagen:", producto['imagen'])  # Para depuración
    
    return render(request, 'ver_producto_detalle.html', {'producto': producto})


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
    context = get_context_with_sedes()
    context['datos'] = context['sedes']
    return render(request, 'ver_sedes.html', context)

def seleccionar_sede(request):
    if request.method == 'POST':
        sede_id = request.POST.get('sede_id')
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
