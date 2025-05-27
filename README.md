# proyecto FerraMas

## Índice
- [proyecto FerraMas](#proyecto-ferramas)
  - [Índice](#índice)
  - [instalacion](#instalacion)
    - [venv](#venv)
    - [instalacion de requerimientos y bd](#instalacion-de-requerimientos-y-bd)
    - [iniciar servidor](#iniciar-servidor)
  - [proyecto django](#proyecto-django)

## Instalacion

### Venv

lo primero es la venv 

```powershell
python -m venv env
```
luego de crear la venv se tiene que activar con el siguiente comando

```powershell
.\env\Scripts\activate
```

### Iniciar java

entrar a la carpeta target del proyecto gestion y ejecutar el siguiente comando para iniciar el proyecto gestion

```powershell
java -jar .\gestion-0.0.1-SNAPSHOT.jar
```


### Instalacion Django

actualizar pip

```powershell
py -m pip install --upgrade pip
```

para poder ejecutar el proyecto luego de clonarlo primero se tiene que instalar los requerimientos con el siguiente comando

```powershell
pip install -r requeriments.txt

```
luego de instalar los requerimientos se tiene que crear la base de datos con el siguiente comando

```powershell
py manage.py makemigrations
```

```powershell
py manage.py migrate
```

luego de crear la base de datos se tiene que crear un superusuario para poder acceder al panel de administracion con el siguiente comando

```powershell
py manage.py createsuperuser
```
luego de crear el superusuario se tiene que ejecutar el servidor con el siguiente comando

### Iniciar servidor

```powershell
py manage.py runserver
```

