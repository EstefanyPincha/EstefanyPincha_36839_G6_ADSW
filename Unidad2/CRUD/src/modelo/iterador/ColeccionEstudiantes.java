package modelo.iterador;

import modelo.Estudiante;

import java.util.ArrayList;
import java.util.List;

/**
 * Patron ITERATOR - coleccion.
 * Guarda los estudiantes y entrega un iterador para recorrerlos,
 * sin que nadie toque la lista interna.
 */
public class ColeccionEstudiantes {
    private final List<Estudiante> estudiantes = new ArrayList<>();

    public void agregar(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public boolean reemplazar(Estudiante estudiante) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getId().equals(estudiante.getId())) {
                estudiantes.set(i, estudiante);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String id) {
        return estudiantes.removeIf(e -> e.getId().equals(id));
    }

    public int tamanio() {
        return estudiantes.size();
    }

    public IteradorEstudiante crearIterador() {
        return new IteradorSecuencial(estudiantes);
    }
}
