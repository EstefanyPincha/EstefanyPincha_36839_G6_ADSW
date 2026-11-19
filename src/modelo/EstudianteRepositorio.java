package modelo;

import modelo.iterador.ColeccionEstudiantes;
import modelo.iterador.IteradorEstudiante;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepositorio {
    private final Path rutaArchivo;

    public EstudianteRepositorio() {
        this(Paths.get("datos", "estudiantes.txt"));
    }

    public EstudianteRepositorio(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public ColeccionEstudiantes obtenerTodos() throws IOException {
        ColeccionEstudiantes estudiantes = new ColeccionEstudiantes();
        if (!Files.exists(rutaArchivo)) {
            return estudiantes;
        }

        List<String> lineas = Files.readAllLines(rutaArchivo, StandardCharsets.UTF_8);
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i).trim();
            if (linea.isEmpty()) {
                continue;
            }

            try {
                estudiantes.agregar(leerRegistro(linea));
            } catch (IllegalArgumentException error) {
                throw new IOException(
                        "Registro invalido en " + rutaArchivo + ", linea " + (i + 1) + ".");
            }
        }
        return estudiantes;
    }

    public Estudiante obtenerPorId(String id) throws IOException {
        IteradorEstudiante iterador = obtenerTodos().crearIterador();
        while (iterador.tieneSiguiente()) {
            Estudiante estudiante = iterador.siguiente();
            if (estudiante.getId().equals(id)) {
                return estudiante;
            }
        }
        throw new IllegalArgumentException("No se encontro un estudiante con ese ID.");
    }

    public void registrar(Estudiante estudiante) throws IOException {
        ColeccionEstudiantes estudiantes = obtenerTodos();
        IteradorEstudiante iterador = estudiantes.crearIterador();
        while (iterador.tieneSiguiente()) {
            if (iterador.siguiente().getId().equals(estudiante.getId())) {
                throw new IllegalArgumentException("Ya existe un estudiante con ese ID.");
            }
        }
        estudiantes.agregar(estudiante);
        guardar(estudiantes);
    }

    public void actualizar(Estudiante estudiante) throws IOException {
        ColeccionEstudiantes estudiantes = obtenerTodos();
        if (!estudiantes.reemplazar(estudiante)) {
            throw new IllegalArgumentException("No se encontro un estudiante con ese ID.");
        }
        guardar(estudiantes);
    }

    public void eliminar(String id) throws IOException {
        ColeccionEstudiantes estudiantes = obtenerTodos();
        if (!estudiantes.eliminar(id)) {
            throw new IllegalArgumentException("No se encontro un estudiante con ese ID.");
        }
        guardar(estudiantes);
    }

    private void guardar(ColeccionEstudiantes estudiantes) throws IOException {
        Path directorio = rutaArchivo.getParent();
        if (directorio != null) {
            Files.createDirectories(directorio);
        }

        StringBuilder contenido = new StringBuilder();
        IteradorEstudiante iterador = estudiantes.crearIterador();
        while (iterador.tieneSiguiente()) {
            Estudiante estudiante = iterador.siguiente();
            contenido.append(estudiante.getId()).append(' ')
                    .append(entreComillas(estudiante.getNombre())).append(' ')
                    .append(entreComillas(estudiante.getApellido())).append(' ')
                    .append(entreComillas(estudiante.getDireccion()))
                    .append(System.lineSeparator());
        }
        Files.writeString(rutaArchivo, contenido.toString(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private Estudiante leerRegistro(String linea) {
        List<String> campos = leerCampos(linea);
        if (campos.size() == 4) {
            campos.set(0, convertirIdAntiguo(campos.get(0)));
        }
        if (campos.size() != 4
                || !Estudiante.esIdValido(campos.get(0))) {
            throw new IllegalArgumentException();
        }
        return new Estudiante(
                campos.get(0), campos.get(1), campos.get(2), campos.get(3));
    }

    /** Convierte IDs del formato viejo (L00-7867) al nuevo (L00007867). */
    private String convertirIdAntiguo(String id) {
        if (id.matches("L00-[0-9]{1,6}")) {
            String numero = id.substring(4);
            return "L00" + "0".repeat(6 - numero.length()) + numero;
        }
        return id;
    }

    private List<String> leerCampos(String linea) {
        List<String> campos = new ArrayList<>();
        int posicion = 0;

        while (posicion < linea.length()) {
            while (posicion < linea.length()
                    && Character.isWhitespace(linea.charAt(posicion))) {
                posicion++;
            }
            if (posicion >= linea.length()) {
                break;
            }

            if (linea.charAt(posicion) == '"') {
                posicion++;
                StringBuilder valor = new StringBuilder();
                boolean cerrado = false;
                while (posicion < linea.length()) {
                    char caracter = linea.charAt(posicion++);
                    if (caracter == '\\' && posicion < linea.length()) {
                        valor.append(linea.charAt(posicion++));
                    } else if (caracter == '"') {
                        cerrado = true;
                        break;
                    } else {
                        valor.append(caracter);
                    }
                }
                if (!cerrado) {
                    throw new IllegalArgumentException();
                }
                campos.add(valor.toString());
            } else {
                int inicio = posicion;
                while (posicion < linea.length()
                        && !Character.isWhitespace(linea.charAt(posicion))) {
                    posicion++;
                }
                campos.add(linea.substring(inicio, posicion));
            }
        }
        return campos;
    }

    private String entreComillas(String valor) {
        return "\"" + valor.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
