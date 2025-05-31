function seleccionarSede(sedeId, sedeNombre) {
    // Mostrar loading en el botón
    const sedeActual = document.getElementById('sede-actual');
    const originalText = sedeActual.textContent;
    sedeActual.innerHTML = '<i class="spinner-border spinner-border-sm me-2"></i>Cambiando...';

    // Obtener token CSRF
    const csrfToken = getCookie('csrftoken') || document.querySelector('[name=csrfmiddlewaretoken]')?.value;

    fetch('/cambiar-sede/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRFToken': csrfToken
        },
        body: JSON.stringify({
            sede_id: sedeId
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Actualizar la interfaz
                sedeActual.textContent = sedeNombre;

                // Mostrar notificación de éxito
                mostrarNotificacion(data.message, 'success');

                // Cerrar el dropdown
                const dropdown = bootstrap.Dropdown.getInstance(document.getElementById('sedeDropdown'));
                if (dropdown) {
                    dropdown.hide();
                }

                // Opcional: recargar la página después de un breve delay
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            } else {
                sedeActual.textContent = originalText;
                mostrarNotificacion(data.message || 'Error al cambiar sede', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            sedeActual.textContent = originalText;
            mostrarNotificacion('Error de conexión', 'error');
        });
}

function mostrarNotificacion(mensaje, tipo) {
    // Crear notificación temporal
    const notification = document.createElement('div');
    notification.className = `alert alert-${tipo === 'success' ? 'success' : 'danger'} position-fixed`;
    notification.style.cssText = 'top: 80px; right: 20px; z-index: 9999; min-width: 300px; animation: slideInRight 0.3s ease;';
    notification.innerHTML = `
        <div class="d-flex align-items-center">
            <i class="fas fa-${tipo === 'success' ? 'check-circle' : 'exclamation-triangle'} me-2"></i>
            <span>${mensaje}</span>
            <button type="button" class="btn-close ms-auto" onclick="this.parentElement.parentElement.remove()"></button>
        </div>
    `;

    document.body.appendChild(notification);

    // Eliminar después de 4 segundos
    setTimeout(() => {
        if (notification.parentNode) {
            notification.remove();
        }
    }, 4000);
}

// Función helper para obtener cookie CSRF
function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

// Agregar animación CSS
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    .sede-dropdown {
        min-width: 280px;
    }
    
    .sede-dropdown .dropdown-item:hover {
        background-color: #f8f9fa;
    }
    
    .sede-dropdown .dropdown-item small {
        font-size: 0.8em;
    }
`;
document.head.appendChild(style);