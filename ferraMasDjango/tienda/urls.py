from django.urls import path
from .import views

urlpatterns = [
    path('empleados/', views.ver_empleados, name='ver_empleados'),
    path('comunas/', views.ver_comunas, name='ver_comunas'),
    path('catalogo/', views.verCatalogo, name='ver_catalogo'),
    path('productos/', views.verProductos, name='ver_productos'),
    path('producto/<str:producto_id>/', views.verProductoDetalle, name='ver_producto'),
    path('producto/', views.verProductoSinId, name='ver_producto_sin_id'),
    
]
