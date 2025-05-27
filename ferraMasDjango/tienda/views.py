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
        url = "http://localhost:8089/api/catalogo/"
        response = requests.get(url)
        data = response.json()
        return data
    except Exception as e:
        return None
    
def verCatalogo(request):
    catalogo = obtenerCatalogo()
    context = {'datos': catalogo}
    return render(request, 'ver_catalogo.html', context)
