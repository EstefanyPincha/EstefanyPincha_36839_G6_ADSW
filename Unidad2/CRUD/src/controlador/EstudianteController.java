package controlador;

import modelo.Estudiante;
import modelo.EstudianteRepositorio;
import modelo.iterador.ColeccionEstudiantes;

import java.io.IOException;

public class EstudianteController {
    private final EstudianteRepositorio repositorio = new EstudianteRepositorio();

    public void registrar(String id, String nombre, String apellido,
                          String direccion) throws IOException {
        validar(id, nombre, apellido, direccion);
        repositorio.registrar(new Estudiante(id, nombre, apellido, direccion));
    }

    public ColeccionEstudiantes consultarTodos() throws IOException {
        return repositorio.obtenerTodos();
    }

    public Estudiante consultarPorId(String id) throws IOException {
        if (!Estudiante.esIdValido(id)) {
            throw new IllegalArgumentException(
                    "El ID debe tener el formato L00 seguido de 6 numeros (ej: L00123456).");
        }
        return repositorio.obtenerPorId(id);
    }

    public void modificar(String id, String nombre, String apellido,
                          String direccion) throws IOException {
        validar(id, nombre, apellido, direccion);
        repositorio.actualizar(new Estudiante(id, nombre, apellido, direccion));
    }

    public void eliminar(String id) throws IOException {
        if (!Estudiante.esIdValido(id)) {
            throw new IllegalArgumentException(
                    "El ID debe tener el formato L00 seguido de 6 numeros (ej: L00123456).");
        }
        repositorio.eliminar(id);
    }

    private void validar(String id, String nombre, String apellido,
                         String direccion) {
        if (!Estudiante.esIdValido(id)
                || nombre == null || nombre.trim().isEmpty()
                || apellido == null || apellido.trim().isEmpty()
                || direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El ID debe tener el formato L00 seguido de 6 numeros (ej: L00123456) y los campos no pueden quedar vacios.");
        }
    }
}
