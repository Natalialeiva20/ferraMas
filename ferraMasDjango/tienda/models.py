from django.db import models

class Producto(models.Model):
    idproducto = models.CharField(max_length=15, primary_key=True, db_column='idproducto')
    nombre = models.CharField(max_length=30, db_column='nombre')
    precio = models.IntegerField(db_column='precio')
    stockminimo = models.IntegerField(db_column='stockminimo')
    idcategoria = models.IntegerField(db_column='idcategoria')
    idsede = models.IntegerField(db_column='idsede')

    class Meta:
        db_table = 'producto'

    def __str__(self):
        return self.nombre