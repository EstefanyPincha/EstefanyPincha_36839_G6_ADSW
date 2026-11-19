package vista.estado;

import vista.mediador.MediadorVentana;

/**
 * Estado concreto: hay un estudiante seleccionado.
 * El ID queda bloqueado y se activan "Actualizar" y "Eliminar".
 */
public class ModoEdicion extends EstadoFormulario {

    @Override
    protected void prepararFormulario(MediadorVentana m) {
        m.llenarFormularioConFila();
    }

    @Override
    protected boolean permiteEditar() {
        return true;
    }

    @Override
    protected String mensaje(MediadorVentana m) {
        return "Modo edicion - estudiante " + m.getId();
    }

    @Override
    public void filaSeleccionada(MediadorVentana m) {
        m.cambiarEstado(new ModoEdicion());
    }

    @Override
    public void registrar(MediadorVentana m) {
        // No permitido en este estado (el boton esta desactivado)
    }

    @Override
    public void actualizar(MediadorVentana m) {
        try {
            m.getControlador().modificar(m.getId(), m.getNombre(), m.getApellido(), m.getDireccion());
            m.terminarOperacion("Estudiante actualizado.");
        } catch (Exception error) {
            m.mostrarError(error);
        }
    }

    @Override
    public void eliminar(MediadorVentana m) {
        if (!m.confirmar("¿Eliminar el estudiante seleccionado?")) {
            return;
        }
        try {
            m.getControlador().eliminar(m.getId());
            m.terminarOperacion("Estudiante eliminado.");
        } catch (Exception error) {
            m.mostrarError(error);
        }
    }
}
