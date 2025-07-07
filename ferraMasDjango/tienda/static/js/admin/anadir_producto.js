// Manejar tanto formularios de añadir como de modificar
const formAnadir = document.getElementById('anadirProducto');
const formModificar = document.getElementById('formModificarProducto');

if (formAnadir) {
    formAnadir.addEventListener('submit', function (e) {
        e.preventDefault();
        manejarEnvioFormulario(this, '/admins/productos/crear/');
    });
}

if (formModificar) {
    formModificar.addEventListener('submit', function (e) {
        e.preventDefault();
        const productoId = '{{ producto.idproducto }}';
        manejarEnvioFormulario(this, `/admins/productos/${productoId}/actualizar/`);
    });
}

function manejarEnvioFormulario(form, url) {
    const formData = new FormData(form);
    const submitBtn = form.querySelector('button[type="submit"]');
    const originalText = submitBtn.innerHTML;

    submitBtn.innerHTML = '<i class="spinner-border spinner-border-sm me-2"></i>Procesando...';
    submitBtn.disabled = true;

    fetch(url, {
        method: 'POST',
        body: formData,
        headers: {
            'X-CSRFToken': getCookie('csrftoken')
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                mostrarNotificacion(data.message, 'success');
                setTimeout(() => {
                    window.location.href = '/admins/productos/';
                }, 1500);
            } else {
                mostrarNotificacion(data.message, 'error');
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            mostrarNotificacion('Error de conexión', 'error');
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        });
}

function mostrarNotificacion(mensaje, tipo) {
    const notification = document.createElement('div');
    notification.className = `alert alert-${tipo === 'success' ? 'success' : 'danger'} position-fixed`;
    notification.style.cssText = 'top: 80px; right: 20px; z-index: 9999; min-width: 300px;';
    notification.innerHTML = `
        <i class="fas fa-${tipo === 'success' ? 'check-circle' : 'exclamation-triangle'}"></i>
        ${mensaje}
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 3000);
}

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