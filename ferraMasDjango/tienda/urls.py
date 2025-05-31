from django.urls import path
from .import views

urlpatterns = [
    path('empleados/', views.ver_empleados, name='ver_empleados'),
    path('comunas/', views.ver_comunas, name='ver_comunas'),
    path('catalogo/', views.verCatalogo, name='ver_catalogo'),
    path('productos/', views.verProductos, name='ver_productos'),
    path('producto/<str:producto_id>/', views.verProductoDetalle, name='ver_producto'),
    path('', views.ver_home, name='home'),
    path('sedes/', views.ver_sedes, name='ver_sedes'),  
    path('seleccionar_sede/', views.seleccionar_sede, name='seleccionar_sede'),  
    path('seleccionar_sede/', views.seleccionar_sede, name='seleccionar_sede'),  
]

