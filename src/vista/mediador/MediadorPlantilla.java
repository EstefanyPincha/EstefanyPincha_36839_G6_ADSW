package vista.mediador;

/**
 * Patron MEDIATOR con TEMPLATE METHOD.
 * notificar() es el metodo plantilla: todo aviso de un componente
 * pasa por los mismos pasos, en el mismo orden.
 */
public abstract class MediadorPlantilla implements Mediador {

    /** Metodo plantilla  */
    @Override
   public final void notificar(String evento) {
    registrarEvento(evento);   // Registra el evento
    reaccionar(evento);        // Ejecuta la acción correspondiente
    actualizarVista();        // Actualiza la ventana
}


/** Muestra el evento en consola. */
protected void registrarEvento(String evento) {
    System.out.println("[Mediador] evento: " + evento);
}

    protected abstract void reaccionar(String evento);

    protected abstract void actualizarVista();
}
