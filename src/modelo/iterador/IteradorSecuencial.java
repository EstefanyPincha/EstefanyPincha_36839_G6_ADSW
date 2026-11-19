package modelo.iterador;

import modelo.Estudiante;

import java.util.List;

/** Iterador concreto: recorre los estudiantes uno por uno, en orden. */
public class IteradorSecuencial extends IteradorPlantilla {
    private final List<Estudiante> estudiantes;
    private int posicion = 0;

    public IteradorSecuencial(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    @Override
    public boolean tieneSiguiente() {
        return posicion < estudiantes.size();
    }

    @Override
    protected Estudiante elementoActual() {
        return estudiantes.get(posicion);
    }

    @Override
    protected void avanzar() {
        posicion++;
    }
}
