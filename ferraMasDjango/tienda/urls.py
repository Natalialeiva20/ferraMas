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
    path('cambiar-sede/', views.cambiar_sede_ajax, name='cambiar_sede_ajax'),
    path('anadirProducto/', views.anadirProducto, name='anadirProducto'),
    path('eliminarProducto/', views.eliminarProducto, name='eliminarProducto'),
    path('modificarProducto/', views.modificarProducto, name='modificarProducto'),
    path('ver_anadir_producto/', views.ver_anadir_producto, name='ver_anadir_producto'),

    path('admins/productos/', views.lista_productos, name='lista_productos'),
    path('admins/productos/anadir/', views.ver_anadir_producto, name='ver_anadir_producto'),
    path('admins/productos/crear/', views.anadirProducto, name='anadirProducto'),
    path('admins/productos/<str:producto_id>/editar/', views.ver_modificar_producto, name='ver_modificar_producto'),
    path('admins/productos/<str:producto_id>/actualizar/', views.modificarProducto, name='modificarProducto'),
    path('admins/productos/<str:producto_id>/eliminar/', views.eliminarProducto, name='eliminarProducto'),
]

