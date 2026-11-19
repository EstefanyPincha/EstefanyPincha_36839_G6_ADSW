package vista.mediador;

/**
 * Patron MEDIATOR - interfaz.
 * Los botones y la tabla solo avisan al mediador; no se hablan entre ellos.
 */
public interface Mediador {
    void notificar(String evento);
}
