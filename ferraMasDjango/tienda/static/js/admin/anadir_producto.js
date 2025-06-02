document.getElementById('formModificarProducto').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const submitBtn = this.querySelector('button[type="submit"]');
    const originalText = submitBtn.innerHTML;

    submitBtn.innerHTML = '<i class="spinner-border spinner-border-sm me-2"></i>Actualizando...';
    submitBtn.disabled = true;

    fetch('/admin/productos/{{ producto.idproducto }}/actualizar/', {
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
                    window.location.href = '/admin/productos/';
                }, 1500);
            } else {
                mostrarNotificacion(data.message, 'error');
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            }
        })
        .catch(error => {
            mostrarNotificacion('Error de conexión', 'error');
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        });
});

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

document.addEventListener('DOMContentLoaded', function () {
    // Obtén los valores actuales del producto desde atributos data o variables de Django
    const categoriaSeleccionada = "{{ producto.idcategoria |default: '' }}";
    const sedeSeleccionada = "{{ producto.idsede |default: '' }}";

    // Selecciona el select de categoría y sede
    const selectCategoria = document.getElementById('idcategoria');
    const selectSede = document.getElementById('idsede');

    if (selectCategoria && categoriaSeleccionada) {
        for (let option of selectCategoria.options) {
            if (option.value == categoriaSeleccionada) {
                option.selected = true;
                break;
            }
        }
    }

    if (selectSede && sedeSeleccionada) {
        for (let option of selectSede.options) {
            if (option.value == sedeSeleccionada) {
                option.selected = true;
                break;
            }
        }
    }
});
