package modelo.iterador;

import modelo.Estudiante;

import java.util.NoSuchElementException;

/**
 * Patron ITERATOR con TEMPLATE METHOD.
 * siguiente() es el metodo plantilla: fija el orden de los pasos
 * y las subclases solo dicen cual es el elemento actual y como avanzar.
 */
public abstract class IteradorPlantilla implements IteradorEstudiante {

    /** Metodo plantilla*/
   @Override
    public final Estudiante siguiente() {
    if (!tieneSiguiente()) {                 // Verifica si hay otro estudiante
        throw new NoSuchElementException();
    }
    Estudiante actual = elementoActual();    // Obtiene el estudiante actual
    avanzar();                               // Avanza al siguiente
    return actual;                           // Devuelve el estudiante
}

    protected abstract Estudiante elementoActual();

    protected abstract void avanzar();
}
