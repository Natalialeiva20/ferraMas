from django.urls import path
from . import views

app_name = 'cart'

urlpatterns = [
    path('add/<str:product_id>/', views.add_to_cart, name='add_to_cart'),
    path('ver_carrito/', views.ver_carrito, name='ver_carrito'),
    path('iniciar_pago/', views.iniciar_pago, name='iniciar_pago'),
    path('remove/<str:clave_producto>/', views.remove_from_cart, name='remove_from_cart'),
    path('clear/', views.clear_cart, name='clear_cart'),
    path('retorno_pago/', views.retorno_pago, name='retorno_pago'),
]