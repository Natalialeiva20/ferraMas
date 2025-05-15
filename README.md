# proyecto FerraMas

## Índice
- [proyecto FerraMas](#proyecto-ferramas)
  - [Índice](#índice)
  - [instalacion](#instalacion)
    - [venv](#venv)
    - [instalacion de requerimientos y bd](#instalacion-de-requerimientos-y-bd)
    - [iniciar servidor](#iniciar-servidor)
  - [proyecto django](#proyecto-django)

## instalacion

### venv

lo primero es la venv 

```powershell
python -m venv env
```
luego de crear la venv se tiene que activar con el siguiente comando

```powershell
.\env\Scripts\activate
```
### instalacion de requerimientos y bd

para poder ejecutar el proyecto luego de clonarlo primero se tiene que instalar los requerimientos con el siguiente comando

```powershell
pip install -r requeriments.txt

```
luego de instalar los requerimientos se tiene que crear la base de datos con el siguiente comando

```powershell
py manage.py makemigrations

py manage.py migrate
```

luego de crear la base de datos se tiene que crear un superusuario para poder acceder al panel de administracion con el siguiente comando

```powershell
py manage.py createsuperuser
```
luego de crear el superusuario se tiene que ejecutar el servidor con el siguiente comando

### iniciar servidor

```powershell
py manage.py runserver
```

## proyecto django

las cosas que tengo en el proyecto son las siguientes:
- tengo configurado ferramas como el proyecto
- tienda es la app

