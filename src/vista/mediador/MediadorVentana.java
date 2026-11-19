package vista.mediador;

import controlador.EstudianteController;
import modelo.Estudiante;
import modelo.iterador.IteradorEstudiante;
import vista.WindowsView;
import vista.estado.EstadoFormulario;
import vista.estado.ModoNuevo;

import javax.swing.JOptionPane;

/**
 * Patron MEDIATOR - mediador concreto.
 * Completa los pasos de la plantilla: reaccionar() le pasa el evento
 * al estado actual (patron STATE) y actualizarVista() refresca el titulo.
 */
public class MediadorVentana extends MediadorPlantilla {
    private final WindowsView vista;
    private final EstudianteController controlador;
    private EstadoFormulario estado;

    public MediadorVentana(WindowsView vista, EstudianteController controlador) {
        this.vista = vista;
        this.controlador = controlador;
        recargarTabla();
        cambiarEstado(new ModoNuevo());
        actualizarVista();
    }

    @Override
    protected void reaccionar(String evento) {
        switch (evento) {
            case "filaSeleccionada" -> estado.filaSeleccionada(this);
            case "registrar" -> estado.registrar(this);
            case "actualizar" -> estado.actualizar(this);
            case "eliminar" -> estado.eliminar(this);
            case "limpiar" -> cambiarEstado(new ModoNuevo());
        }
    }

    @Override
    protected void actualizarVista() {
        vista.setTitle("Estudiantes - " + cantidadEstudiantes() + " registrados");
    }

    public void cambiarEstado(EstadoFormulario nuevoEstado) {
        estado = nuevoEstado;
        estado.entrar(this);
    }

    // ----- Acciones sobre los componentes de la ventana -----

    public void recargarTabla() {
        try {
            vista.getModeloTabla().setRowCount(0);
            IteradorEstudiante iterador = controlador.consultarTodos().crearIterador();
            while (iterador.tieneSiguiente()) {
                Estudiante e = iterador.siguiente();
                vista.getModeloTabla().addRow(new Object[]{
                        e.getId(), e.getNombre(), e.getApellido(), e.getDireccion()});
            }
        } catch (Exception error) {
            mostrarError(error);
        }
    }

    public void llenarFormularioConFila() {
        int fila = vista.getTabla().getSelectedRow();
        if (fila >= 0) {
            vista.getCampoId().setText(String.valueOf(vista.getModeloTabla().getValueAt(fila, 0)));
            vista.getCampoNombre().setText(String.valueOf(vista.getModeloTabla().getValueAt(fila, 1)));
            vista.getCampoApellido().setText(String.valueOf(vista.getModeloTabla().getValueAt(fila, 2)));
            vista.getCampoDireccion().setText(String.valueOf(vista.getModeloTabla().getValueAt(fila, 3)));
        }
    }

    public void limpiarFormulario() {
        vista.getCampoId().setText("");
        vista.getCampoNombre().setText("");
        vista.getCampoApellido().setText("");
        vista.getCampoDireccion().setText("");
        vista.getTabla().clearSelection();
    }

    public void configurarControles(boolean idEditable, boolean registrar,
                                    boolean actualizar, boolean eliminar) {
        vista.getCampoId().setEditable(idEditable);
        vista.getBotonRegistrar().setEnabled(registrar);
        vista.getBotonActualizar().setEnabled(actualizar);
        vista.getBotonEliminar().setEnabled(eliminar);
    }

    public void mostrarEstado(String mensaje) {
        vista.getEtiquetaEstado().setText(mensaje);
    }

    /** Pasos comunes despues de registrar, modificar o eliminar. */
    public void terminarOperacion(String mensaje) {
        recargarTabla();
        cambiarEstado(new ModoNuevo());
        mostrarEstado(mensaje);
    }

    public void mostrarError(Exception error) {
        JOptionPane.showMessageDialog(vista, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(vista, mensaje, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // ----- Datos del formulario -----

    public EstudianteController getControlador() {
        return controlador;
    }

    public String getId() {
        return vista.getCampoId().getText().trim();
    }

    public String getNombre() {
        return vista.getCampoNombre().getText().trim();
    }

    public String getApellido() {
        return vista.getCampoApellido().getText().trim();
    }

    public String getDireccion() {
        return vista.getCampoDireccion().getText().trim();
    }

    public int cantidadEstudiantes() {
        return vista.getModeloTabla().getRowCount();
    }
}
