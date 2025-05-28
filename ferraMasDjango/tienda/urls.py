from django.urls import path
from .import views

urlpatterns = [
    path('empleados/', views.ver_empleados, name='ver_empleados'),
    path('comunas/', views.ver_comunas, name='ver_comunas'),
    path('catalogo/', views.verCatalogo, name='ver_catalogo'),
    path('productos/', views.verProductos, name='ver_producto'),
]
