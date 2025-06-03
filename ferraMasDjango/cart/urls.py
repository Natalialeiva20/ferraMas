from django.urls import path
from . import views

app_name = 'cart'

urlpatterns = [
    path('add/<str:product_id>/', views.add_to_cart, name='add_to_cart'),
    path('ver_carrito/', views.ver_carrito, name='ver_carrito'),
    path('iniciar_pago/', views.iniciar_pago, name='iniciar_pago'),
    path('retorno_pago/', views.retorno_pago, name='retorno_pago'),
]