# Generated by Django 5.2.1 on 2025-05-27 03:29

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('tienda', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='itemcarrito',
            name='carrito',
        ),
        migrations.RemoveField(
            model_name='producto',
            name='categoria',
        ),
        migrations.AlterUniqueTogether(
            name='itemcarrito',
            unique_together=None,
        ),
        migrations.RemoveField(
            model_name='itemcarrito',
            name='producto',
        ),
        migrations.DeleteModel(
            name='Carrito',
        ),
        migrations.DeleteModel(
            name='Categoria',
        ),
        migrations.DeleteModel(
            name='ItemCarrito',
        ),
        migrations.DeleteModel(
            name='Producto',
        ),
    ]
