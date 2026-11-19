package vista;

import controlador.EstudianteController;
import modelo.Estudiante;
import modelo.iterador.ColeccionEstudiantes;
import modelo.iterador.IteradorEstudiante;

import java.util.Scanner;

public class ConsolaView {
    private final EstudianteController controlador;
    private final Scanner entrada = new Scanner(System.in);

    public ConsolaView(EstudianteController controlador) {
        this.controlador = controlador;
    }

    public void ejecutar() {
        int opcion;
        do {
            System.out.println("\n=== CRUD de estudiantes ===");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Consultar estudiantes");
            System.out.println("3. Modificar estudiante");
            System.out.println("4. Eliminar estudiante");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> consultar();
                case 3 -> modificar();
                case 4 -> eliminar();
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!entrada.hasNextLine()) {
                return 0;
            }
            String texto = entrada.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException error) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!entrada.hasNextLine()) {
                throw new IllegalStateException("No hay mas datos de entrada.");
            }
            String valor = entrada.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("El campo no puede quedar vacio.");
        }
    }

    private void registrar() {
        try {
            controlador.registrar(
                    leerTexto("ID (L00XXXXXX): "),
                    leerTexto("Nombre: "),
                    leerTexto("Apellido: "),
                    leerTexto("Direccion: "));
            System.out.println("Estudiante registrado.");
        } catch (Exception error) {
            mostrarError(error);
        }
    }

    private void consultar() {
        try {
            String id = leerTexto("ID (L00XXXXXX, o 0 para consultar todos): ");
            if ("0".equals(id)) {
                ColeccionEstudiantes estudiantes = controlador.consultarTodos();
                if (estudiantes.tamanio() == 0) {
                    System.out.println("No hay estudiantes registrados.");
                    return;
                }
                mostrarTodos(estudiantes.crearIterador());
            } else {
                mostrar(controlador.consultarPorId(id));
            }
        } catch (Exception error) {
            mostrarError(error);
        }
    }

    /** Patron ITERATOR: recorre los estudiantes sin conocer la lista interna. */
    private void mostrarTodos(IteradorEstudiante iterador) {
        while (iterador.tieneSiguiente()) {
            mostrar(iterador.siguiente());
        }
    }

    private void modificar() {
        try {
            controlador.modificar(
                    leerTexto("ID (L00XXXXXX): "),
                    leerTexto("Nuevo nombre: "),
                    leerTexto("Nuevo apellido: "),
                    leerTexto("Nueva direccion: "));
            System.out.println("Estudiante modificado.");
        } catch (Exception error) {
            mostrarError(error);
        }
    }

    private void eliminar() {
        try {
            controlador.eliminar(leerTexto("ID (L00XXXXXX): "));
            System.out.println("Estudiante eliminado.");
        } catch (Exception error) {
            mostrarError(error);
        }
    }

    private void mostrar(Estudiante estudiante) {
        System.out.println("\nID: " + estudiante.getId());
        System.out.println("Nombre: " + estudiante.getNombre());
        System.out.println("Apellido: " + estudiante.getApellido());
        System.out.println("Direccion: " + estudiante.getDireccion());
    }

    private void mostrarError(Exception error) {
        System.out.println("Error: " + error.getMessage());
    }
}
