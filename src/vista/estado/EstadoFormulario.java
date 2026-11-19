package vista.estado;

import vista.mediador.MediadorVentana;
/**
 * Patrón STATE con TEMPLATE METHOD.
 * Cada estado define cómo funciona el formulario.
 * El método entrar() mantiene el mismo orden de ejecución.
 */
public abstract class EstadoFormulario {

    /** Define los pasos al entrar a un estado. */
    public final void entrar(MediadorVentana m) {
        prepararFormulario(m);                          // Prepara el formulario
        boolean edicion = permiteEditar();              // Verifica si está en edición
        m.configurarControles(!edicion, !edicion, edicion, edicion); // Configura los botones
        m.mostrarEstado(mensaje(m));                    // Muestra el estado actual
    }

// ----- Pasos que varían según el estado -----

// Prepara los campos del formulario.
protected abstract void prepararFormulario(MediadorVentana m);

// Indica si el formulario permite editar.
protected abstract boolean permiteEditar();

// Devuelve el mensaje del estado actual.
protected abstract String mensaje(MediadorVentana m);


// ----- Acciones disponibles en cada estado -----

// Define qué hacer al seleccionar un estudiante.
public abstract void filaSeleccionada(MediadorVentana m);

// Define cómo registrar un estudiante.
public abstract void registrar(MediadorVentana m);

// Define cómo actualizar un estudiante.
public abstract void actualizar(MediadorVentana m);

// Define cómo eliminar un estudiante.
public abstract void eliminar(MediadorVentana m);
}