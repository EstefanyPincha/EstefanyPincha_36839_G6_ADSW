package modelo.iterador;

import modelo.Estudiante;

/**
 * Patron ITERATOR - interfaz del iterador.
 * Permite recorrer estudiantes sin conocer como estan guardados.
 */
public interface IteradorEstudiante {
    boolean tieneSiguiente();

    Estudiante siguiente();
}
