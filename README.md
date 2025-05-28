# 🔧 FerraMas - Sistema de Gestión de Ferretería

Sistema integral de gestión para ferretería con arquitectura de microservicios utilizando Django (frontend/API) y Spring Boot (backend/gestión).

## 📋 Índice
- [🔧 FerraMas - Sistema de Gestión de Ferretería](#-ferramas---sistema-de-gestión-de-ferretería)
  - [📋 Índice](#-índice)
  - [🏗️ Arquitectura del Proyecto](#️-arquitectura-del-proyecto)
  - [⚙️ Configuración del Entorno](#️-configuración-del-entorno)
    - [📋 Requisitos Previos](#-requisitos-previos)
    - [🐍 Configuración del Entorno Virtual (Python)](#-configuración-del-entorno-virtual-python)
  - [🚀 Instalación y Ejecución](#-instalación-y-ejecución)
    - [1️⃣ Servicio Django (Frontend/Tienda)](#1️⃣-servicio-django-frontendtienda)
    - [2️⃣ Servicio Spring Boot (Backend/Gestión)](#2️⃣-servicio-spring-boot-backendgestión)
  - [🌐 Acceso a los Servicios](#-acceso-a-los-servicios)
  - [📂 Estructura del Proyecto](#-estructura-del-proyecto)
  - [🛠️ Tecnologías Utilizadas](#️-tecnologías-utilizadas)
  - [📝 Notas Importantes](#-notas-importantes)

## 🏗️ Arquitectura del Proyecto

El proyecto FerraMas está compuesto por dos servicios principales:

- **Django (ferraMasDjango/)**: Frontend web y API REST para la tienda
- **Spring Boot (gestion/)**: Backend para gestión administrativa y datos

## ⚙️ Configuración del Entorno

### 📋 Requisitos Previos

- Python 3.8+
- Java 17+
- Maven 3.6+
- Git

### 🐍 Configuración del Entorno Virtual (Python)

Navega al directorio del proyecto Django:
```powershell
cd ferraMasDjango
```

Crea el entorno virtual:
```powershell
python -m venv env
```

Activa el entorno virtual:
```powershell
.\env\Scripts\activate
```

## 🚀 Instalación y Ejecución

### 1️⃣ Servicio Django (Frontend/Tienda)

#### Instalación de dependencias

Actualiza pip:
```powershell
py -m pip install --upgrade pip
```

Instala los requerimientos:
```powershell
pip install -r requeriments.txt
```

#### Configuración de la base de datos

Crea las migraciones:
```powershell
py manage.py makemigrations
```

Aplica las migraciones:
```powershell
py manage.py migrate
```

Crea un superusuario (opcional):
```powershell
py manage.py createsuperuser
```

#### Iniciar el servidor Django

```powershell
py manage.py runserver
```

El servidor estará disponible en: `http://localhost:8000`

### 2️⃣ Servicio Spring Boot (Backend/Gestión)

#### Compilar el proyecto

Navega al directorio de gestión:
```powershell
cd gestion
```

Compila el proyecto (si no existe el JAR):
```powershell
mvn clean package
```

#### Iniciar el servicio

Navega a la carpeta target:
```powershell
cd target
```

Ejecuta el JAR:
```powershell
java -jar gestion-0.0.1-SNAPSHOT.jar
```

El servicio estará disponible en: `http://localhost:8089`

## 🌐 Acceso a los Servicios

| Servicio | URL | Descripción |
|----------|-----|-------------|
| Django Frontend | `http://localhost:8000` | Interfaz web de la tienda |
| Django Admin | `http://localhost:8000/admin` | Panel de administración |
| Spring Boot API | `http://localhost:8089` | API de gestión |

## 📂 Estructura del Proyecto

```
ferraMas/
├── README.md
├── ferraMasDjango/                 # Servicio Django
│   ├── manage.py
│   ├── requeriments.txt
│   ├── db.sqlite3                  # Base de datos SQLite
│   ├── env/                        # Entorno virtual Python
│   ├── ferraMas/                   # Configuración principal
│   │   ├── settings.py
│   │   ├── urls.py
│   │   └── wsgi.py
│   └── tienda/                     # App principal de la tienda
│       ├── models.py
│       ├── views.py
│       ├── urls.py
│       ├── templates/
│       └── static/
└── gestion/                        # Servicio Spring Boot
    ├── pom.xml                     # Configuración Maven
    ├── src/main/java/com/ferramas/gestion/
    │   └── entity/
    │       └── Sede.java           # Entidad de ejemplo
    └── target/                     # Archivos compilados
        └── gestion-0.0.1-SNAPSHOT.jar
```

## 🛠️ Tecnologías Utilizadas

### Frontend (Django)
- **Django 5.2**: Framework web principal
- **Django REST Framework**: API REST
- **Crispy Forms**: Formularios mejorados
- **SQLite**: Base de datos local

### Backend (Spring Boot)
- **Spring Boot 3.4.5**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **MySQL Connector**: Conectividad con base de datos
- **Java 17**: Versión de Java

## 📝 Notas Importantes

1. **Orden de inicio**: Se recomienda iniciar primero el servicio Spring Boot y luego Django.

2. **Puertos por defecto**:
   - Django: 8000
   - Spring Boot: 8080

3. **Base de datos**: 
   - Django utiliza SQLite por defecto
   - Spring Boot está configurado para MySQL

4. **Hosts permitidos**: El proyecto Django está configurado para funcionar en:
   - `localhost`
   - `127.0.0.1`
   - `uri1000.win`

5. **Entorno virtual**: Siempre activa el entorno virtual antes de trabajar con Django.

---

Para más información sobre configuración específica, revisa los archivos de configuración:
- Django: [`ferraMas/settings.py`](ferraMasDjango/ferraMas/settings.py)
- Spring Boot: [`pom.xml`](gestion/pom.xml)