from django.urls import path
from . import views

urlpatterns = [
    path('catalogo/', views.verCatalogo, name='ver_catalogo'),
    path('productos/', views.verProductos, name='ver_productos'),
    path('producto/<str:producto_id>/', views.verProductoDetalle, name='ver_producto'),
    path('', views.ver_home, name='home'),
    path('sedes/', views.ver_sedes, name='ver_sedes'),
    path('administracion/', views.administracion, name='administracion'),
]