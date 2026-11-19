package vista.estado;

import vista.mediador.MediadorVentana;

/**
 * Estado concreto: formulario vacio.
 * El ID se puede escribir y solo esta activo "Registrar".
 */
public class ModoNuevo extends EstadoFormulario {

    @Override
    protected void prepararFormulario(MediadorVentana m) {
        m.limpiarFormulario();
    }

    @Override
    protected boolean permiteEditar() {
        return false;
    }

    @Override
    protected String mensaje(MediadorVentana m) {
        return "Modo nuevo - escriba los datos y pulse Registrar";
    }

    @Override
    public void filaSeleccionada(MediadorVentana m) {
        m.cambiarEstado(new ModoEdicion());
    }

    @Override
    public void registrar(MediadorVentana m) {
        try {
            m.getControlador().registrar(m.getId(), m.getNombre(), m.getApellido(), m.getDireccion());
            m.terminarOperacion("Estudiante registrado.");
        } catch (Exception error) {
            m.mostrarError(error);
        }
    }

    @Override
    public void actualizar(MediadorVentana m) {
        // No permitido en este estado (el boton esta desactivado)
    }

    @Override
    public void eliminar(MediadorVentana m) {
        // No permitido en este estado (el boton esta desactivado)
    }
}
