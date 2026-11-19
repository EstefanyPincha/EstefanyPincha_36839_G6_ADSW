# CRUD de Estudiante con patrones de diseño

CRUD de **Estudiante** en Java (ID, nombre, apellido y dirección) con arquitectura **MVC**, ventana Swing y consola, que guarda los datos en `datos/estudiantes.txt`. Aplica cuatro patrones de diseño: **Iterator**, **Mediator**, **State** y **Template Method**.

## Propósito

Practicar la separación en capas (modelo, vista y controlador) y aplicar cuatro patrones de diseño sobre un mismo CRUD, documentando el diseño con diagramas UML hechos en PlantUML y comprobando el funcionamiento con evidencia de ejecución.

## Productos verificables

| Producto | Dónde está |
|---|---|
| Código fuente en Java | `src/` |
| Datos de prueba | `datos/estudiantes.txt` |
| Diagramas UML (código `.puml` e imagen) | `docs/diagramas/` |
| Capturas de la ventana | `docs/capturas/` |
| Matriz de trazabilidad | Sección "Matriz de trazabilidad" |
| Criterios de aceptación | Sección "Criterios de aceptación" |
| Evidencia de consola | Sección "Evidencia de ejecución por consola" |

## Estructura del proyecto

```text
CRUDestudiante/
├── datos/
│   └── estudiantes.txt
├── docs/
│   ├── capturas/
│   └── diagramas/
├── src/
│   ├── Main.java
│   ├── controlador/
│   │   └── EstudianteController.java
│   ├── modelo/
│   │   ├── Estudiante.java
│   │   ├── EstudianteRepositorio.java
│   │   └── iterador/
│   │       ├── ColeccionEstudiantes.java
│   │       ├── IteradorEstudiante.java
│   │       ├── IteradorPlantilla.java
│   │       └── IteradorSecuencial.java
│   └── vista/
│       ├── ConsolaView.java
│       ├── WindowsView.java
│       ├── estado/
│       │   ├── EstadoFormulario.java
│       │   ├── ModoNuevo.java
│       │   └── ModoEdicion.java
│       └── mediador/
│           ├── Mediador.java
│           ├── MediadorPlantilla.java
│           └── MediadorVentana.java
├── ejecutar.bat
└── README.md
```

## Responsabilidad por capa

| Capa | Clases | Responsabilidad |
|---|---|---|
| Modelo | `Estudiante`, `EstudianteRepositorio` | Guardar y leer los estudiantes en `datos/estudiantes.txt` y validar el formato del ID. |
| Controlador | `EstudianteController` | Recibir los pedidos de las vistas, validar los datos y llamar al repositorio. |
| Vista | `ConsolaView`, `WindowsView` | Mostrar la información y recibir las acciones del usuario (consola o ventana). |
| Arranque | `Main` | Abrir la ventana por defecto o la consola con `--console`. |

## Patrones de diseño aplicados

| Patrón | Problema que resuelve | Clases | Método clave |
|---|---|---|---|
| **Iterator** | Recorrer los estudiantes sin depender de cómo está guardada la lista. | `ColeccionEstudiantes`, `IteradorEstudiante`, `IteradorSecuencial` | `siguiente()` |
| **Mediator** | Coordinar los componentes de la ventana desde un solo punto. | `Mediador`, `MediadorVentana` | `notificar(evento)` |
| **State** | Que el formulario se comporte distinto en modo nuevo y en modo edición. | `EstadoFormulario`, `ModoNuevo`, `ModoEdicion` | `entrar(mediador)` |
| **Template Method** | Fijar el orden de los pasos y dejar que cada clase complete lo que cambia. | `IteradorPlantilla`, `MediadorPlantilla`, `EstadoFormulario` | `siguiente()`, `notificar()`, `entrar()` (todos `final`) |

## Requisitos funcionales

| Código | Requisito |
|---|---|
| RF-01 | Registrar un estudiante. |
| RF-02 | Consultar todos los estudiantes o uno por ID. |
| RF-03 | Modificar nombre, apellido y dirección de un estudiante. |
| RF-04 | Eliminar un estudiante. |
| RF-05 | Validar el formato del ID (`L00` más 6 números) y que los campos no estén vacíos. |
| RF-06 | No permitir IDs repetidos. |
| RF-07 | Guardar los datos en `datos/estudiantes.txt`. |

## Matriz de trazabilidad

| Requisito | Caso de uso | Vista | Controlador | Modelo | Evidencia |
|---|---|---|---|---|---|
| RF-01 | Registrar estudiante | `registrar()` en `ConsolaView` y `WindowsView` | `registrar(...)` | `EstudianteRepositorio.registrar` | Consola, opción 1 |
| RF-02 | Consultar estudiantes | `consultar()` y tabla de `WindowsView` | `consultarTodos()`, `consultarPorId(id)` | `obtenerTodos()`, `obtenerPorId(id)` | Consola, opción 2 |
| RF-03 | Modificar estudiante | `modificar()` | `modificar(...)` | `actualizar(estudiante)` | Consola, opción 3 |
| RF-04 | Eliminar estudiante | `eliminar()` | `eliminar(id)` | `eliminar(id)` | Consola, opción 4 |
| RF-05 | Validar datos | Muestra el mensaje de error | `validar(...)` | `Estudiante.esIdValido(id)` | Error de formato de ID |
| RF-06 | Registrar estudiante | Muestra el mensaje de error | (propaga el error) | `registrar` revisa el ID repetido | Error de ID repetido |
| RF-07 | Todos | (no interviene) | (no interviene) | `guardar(...)`, `obtenerTodos()` | `datos/estudiantes.txt` |

## Criterios de aceptación

| Código | Criterio | Resultado |
|---|---|---|
| RF-01 | Con ID válido y campos llenos se guarda el estudiante y se muestra "Estudiante registrado." | Cumple |
| RF-02 | La opción 2 con `0` lista todos; con un ID muestra solo ese estudiante. | Cumple |
| RF-03 | Con un ID existente actualiza los datos y muestra "Estudiante modificado." | Cumple |
| RF-04 | Con un ID existente elimina al estudiante y muestra "Estudiante eliminado." | Cumple |
| RF-05 | Rechaza IDs con formato incorrecto y campos vacíos con un mensaje de error. | Cumple |
| RF-06 | Al registrar un ID existente muestra "Ya existe un estudiante con ese ID." | Cumple |
| RF-07 | Los estudiantes siguen en `datos/estudiantes.txt` al reabrir el programa. | Cumple |
| Patrones | `siguiente()`, `notificar()` y `entrar()` son `final`; en edición el campo ID queda bloqueado. | Cumple |

## Diagramas UML (PlantUML)

Los diagramas están escritos en PlantUML. Cada archivo `.puml` está en `docs/diagramas/` junto con su imagen `.png`.

### Diagrama de casos de uso

Qué puede hacer el usuario con el sistema.

![Diagrama de casos de uso](docs/diagramas/m01_casos_uso.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m01_casos_uso.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
left to right direction
actor "Usuario" as U
rectangle "CRUD de Estudiante" {
  usecase "Registrar estudiante" as UC1
  usecase "Consultar estudiantes" as UC2
  usecase "Modificar estudiante" as UC3
  usecase "Eliminar estudiante" as UC4
  usecase "Validar datos" as V
  usecase "Guardar en archivo" as G
}
U --> UC1
U --> UC2
U --> UC3
U --> UC4
UC1 ..> V : <<include>>
UC3 ..> V : <<include>>
UC1 ..> G : <<include>>
UC3 ..> G : <<include>>
UC4 ..> G : <<include>>
@enduml
```

</details>

### Diagrama de paquetes

Los paquetes de MVC y sus dependencias.

![Diagrama de paquetes](docs/diagramas/m02_paquetes.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m02_paquetes.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
package "vista" {
  [ConsolaView]
  [WindowsView]
}
package "controlador" {
  [EstudianteController]
}
package "modelo" {
  [Estudiante]
  [EstudianteRepositorio]
}
[Main] ..> [ConsolaView] : --console
[Main] ..> [WindowsView] : por defecto
[ConsolaView] --> [EstudianteController]
[WindowsView] --> [EstudianteController]
[EstudianteController] --> [EstudianteRepositorio]
[EstudianteRepositorio] ..> [Estudiante]
@enduml
```

</details>

### Diagrama de clases (MVC)

Clases del proyecto base con atributos, métodos y relaciones.

![Diagrama de clases (MVC)](docs/diagramas/m03_clases.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m03_clases.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
package modelo {
  class Estudiante {
    -id: String
    -nombre: String
    -apellido: String
    -direccion: String
    +esIdValido(id): boolean
  }
  class EstudianteRepositorio {
    -rutaArchivo: Path
    +obtenerTodos(): List<Estudiante>
    +obtenerPorId(id): Estudiante
    +registrar(estudiante)
    +actualizar(estudiante)
    +eliminar(id)
  }
}
package controlador {
  class EstudianteController {
    -repositorio: EstudianteRepositorio
    +registrar(id, nombre, apellido, direccion)
    +consultarTodos(): List<Estudiante>
    +consultarPorId(id): Estudiante
    +modificar(id, nombre, apellido, direccion)
    +eliminar(id)
  }
}
package vista {
  class ConsolaView {
    -controlador: EstudianteController
    +ejecutar()
  }
  class WindowsView {
    -controlador: EstudianteController
    +iniciar()
  }
}
class Main {
  +main(args)
}
Main ..> EstudianteController : crea
Main ..> ConsolaView : --console
Main ..> WindowsView : por defecto
ConsolaView --> EstudianteController
WindowsView --> EstudianteController
EstudianteController --> EstudianteRepositorio
EstudianteRepositorio ..> Estudiante : guarda en datos/estudiantes.txt
@enduml
```

</details>

### Diagrama de secuencia: registrar

Mensajes entre la ventana, el controlador, el repositorio y el archivo al registrar.

![Diagrama de secuencia: registrar](docs/diagramas/m04_secuencia_registrar.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m04_secuencia_registrar.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
actor Usuario
participant WindowsView as V
participant EstudianteController as C
participant EstudianteRepositorio as R
database "estudiantes.txt" as F
Usuario -> V : llena campos y pulsa Registrar
V -> C : registrar(id, nombre, apellido, direccion)
C -> C : validar(...)
alt datos inválidos
  C --> V : IllegalArgumentException
  V --> Usuario : cuadro de error
else datos válidos
  C -> R : registrar(estudiante)
  R -> R : obtenerTodos() y revisar ID repetido
  alt ID ya existe
    R --> V : IllegalArgumentException
  else ID nuevo
    R -> F : guardar(lista)
    V -> C : consultarTodos()
    V --> Usuario : tabla actualizada
  end
end
@enduml
```

</details>

### Diagrama de secuencia: eliminar

Mensajes al eliminar, con la confirmación de la ventana.

![Diagrama de secuencia: eliminar](docs/diagramas/m05_secuencia_eliminar.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m05_secuencia_eliminar.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
actor Usuario
participant WindowsView as V
participant EstudianteController as C
participant EstudianteRepositorio as R
database "estudiantes.txt" as F
Usuario -> V : selecciona fila y pulsa Eliminar
V --> Usuario : ¿Eliminar el estudiante seleccionado?
Usuario -> V : Sí
V -> C : eliminar(id)
C -> C : esIdValido(id)
C -> R : eliminar(id)
R -> R : obtenerTodos() y buscar el ID
R -> F : guardar(lista sin el estudiante)
V -> C : consultarTodos()
V --> Usuario : tabla actualizada
@enduml
```

</details>

### Diagrama de actividades: registrar

Validación del formato y revisión del ID repetido.

![Diagrama de actividades: registrar](docs/diagramas/m06_actividad_registrar.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m06_actividad_registrar.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
start
:Usuario ingresa ID, nombre, apellido y dirección;
if (¿ID con formato L00XXXXXX\ny campos con texto?) then (sí)
  :Leer estudiantes del archivo;
  if (¿El ID ya existe?) then (sí)
    :Mostrar "Ya existe un estudiante con ese ID";
    stop
  else (no)
    :Agregar estudiante a la lista;
    :Guardar lista en estudiantes.txt;
    :Mostrar "Estudiante registrado";
  endif
else (no)
  :Mostrar mensaje de formato del ID\no campos vacíos;
endif
stop
@enduml
```

</details>

### Diagrama de componentes

Componentes del sistema y sus conexiones.

![Diagrama de componentes](docs/diagramas/m07_componentes.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m07_componentes.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
component "Main" as M
component "Vista\n(ConsolaView, WindowsView)" as V
component "Controlador\n(EstudianteController)" as C
component "Modelo\n(Estudiante, EstudianteRepositorio)" as MO
database "datos/estudiantes.txt" as F
M --> V : abre
V --> C : solicita operaciones
C --> MO : valida y delega
MO --> F : lee y escribe
@enduml
```

</details>

### Diagrama de despliegue

Dónde se ejecuta el programa y dónde se guardan los datos.

![Diagrama de despliegue](docs/diagramas/m08_despliegue.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/m08_despliegue.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
node "Computador del estudiante" {
  node "JVM (Java 17+)" {
    artifact "bin/*.class" as B
  }
  artifact "ejecutar.bat" as E
  database "datos/estudiantes.txt" as F
}
E --> B : compila y ejecuta
B --> F : lee y escribe
@enduml
```

</details>

### Diagramas de los patrones

Los diagramas de Iterator, Mediator y State corresponden a las versiones individuales de cada patrón (clases `IteradorEstudiantes`, `EstudianteMediator`, `EstadoNuevo` y `EstadoEdicion`). El diagrama de Template Method corresponde al proyecto con los cuatro patrones.

### Iterator: diagrama de clases



![Iterator: diagrama de clases](docs/diagramas/p01_iterator_clases.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p01_iterator_clases.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
interface Iterable<Estudiante>
interface Iterator<Estudiante> {
  +hasNext(): boolean
  +next(): Estudiante
}
class ColeccionEstudiantes {
  -estudiantes: List<Estudiante>
  +iterator(): Iterator<Estudiante>
}
class IteradorEstudiantes {
  -estudiantes: List<Estudiante>
  -posicion: int
  +hasNext(): boolean
  +next(): Estudiante
}
class EstudianteRepositorio {
  +registrar(estudiante)
  +actualizar(estudiante)
  +eliminar(id)
}
class Estudiante
Iterable <|.. ColeccionEstudiantes
Iterator <|.. IteradorEstudiantes
ColeccionEstudiantes ..> IteradorEstudiantes : crea
EstudianteRepositorio ..> ColeccionEstudiantes : recorre con for
ColeccionEstudiantes o-- Estudiante
@enduml
```

</details>

### Iterator: diagrama de secuencia



![Iterator: diagrama de secuencia](docs/diagramas/p02_iterator_secuencia.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p02_iterator_secuencia.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
participant EstudianteRepositorio as R
participant ColeccionEstudiantes as C
participant IteradorEstudiantes as I
R -> C : new ColeccionEstudiantes(lista)
R -> C : iterator()
C --> R : IteradorEstudiantes
loop mientras hasNext()
  R -> I : hasNext()
  I --> R : true
  R -> I : next()
  I --> R : estudiante actual
  R -> R : compara el ID
end
R -> I : hasNext()
I --> R : false
@enduml
```

</details>

### Mediator: diagrama de clases



![Mediator: diagrama de clases](docs/diagramas/p03_mediator_clases.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p03_mediator_clases.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
interface EstudianteMediator {
  +registrar(id, nombre, apellido, direccion)
  +consultarTodos(): List<Estudiante>
  +consultarPorId(id): Estudiante
  +modificar(id, nombre, apellido, direccion)
  +eliminar(id)
}
class EstudianteController {
  -repositorio: EstudianteRepositorio
}
class WindowsView {
  -controlador: EstudianteMediator
}
class ConsolaView {
  -controlador: EstudianteMediator
}
class EstudianteRepositorio
EstudianteMediator <|.. EstudianteController
WindowsView --> EstudianteMediator
ConsolaView --> EstudianteMediator
EstudianteController --> EstudianteRepositorio
@enduml
```

</details>

### Mediator: diagrama de secuencia



![Mediator: diagrama de secuencia](docs/diagramas/p04_mediator_secuencia.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p04_mediator_secuencia.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
participant WindowsView as W
participant ConsolaView as K
participant "<<interface>>\nEstudianteMediator" as M
participant EstudianteController as C
participant EstudianteRepositorio as R
W -> M : registrar(id, nombre, apellido, direccion)
M -> C : (implementación)
C -> R : registrar(estudiante)
R --> W : listo
K -> M : consultarTodos()
M -> C : (implementación)
C -> R : obtenerTodos()
R --> K : lista de estudiantes
note over W, K : Las dos vistas solo conocen la interfaz
@enduml
```

</details>

### State: diagrama de clases



![State: diagrama de clases](docs/diagramas/p05_state_clases.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p05_state_clases.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
interface EstadoFormulario {
  +aplicar(campoId: JTextField)
  +esEdicion(): boolean
}
class EstadoNuevo {
  +aplicar(campoId)
  +esEdicion(): false
}
class EstadoEdicion {
  +aplicar(campoId)
  +esEdicion(): true
}
class WindowsView {
  -estadoFormulario: EstadoFormulario
  -seleccionarEstudiante()
  -limpiarCampos()
}
EstadoFormulario <|.. EstadoNuevo
EstadoFormulario <|.. EstadoEdicion
WindowsView o--> EstadoFormulario : estado actual
@enduml
```

</details>

### State: diagrama de estados



![State: diagrama de estados](docs/diagramas/p06_state_estados.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p06_state_estados.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
[*] --> EstadoNuevo
EstadoNuevo : campo ID editable
EstadoEdicion : campo ID bloqueado
EstadoNuevo --> EstadoEdicion : seleccionar fila de la tabla
EstadoEdicion --> EstadoNuevo : Limpiar / registrar / actualizar / eliminar
@enduml
```

</details>

### Template Method: diagrama de clases



![Template Method: diagrama de clases](docs/diagramas/p07_template_clases.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p07_template_clases.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
abstract class IteradorPlantilla {
  +{final} siguiente(): Estudiante
  #{abstract} elementoActual(): Estudiante
  #{abstract} avanzar()
}
class IteradorSecuencial
abstract class MediadorPlantilla {
  +{final} notificar(evento)
  #registrarEvento(evento)
  #{abstract} reaccionar(evento)
  #{abstract} actualizarVista()
}
class MediadorVentana
abstract class EstadoFormulario {
  +{final} entrar(m: MediadorVentana)
  #{abstract} prepararFormulario(m)
  #{abstract} permiteEditar(): boolean
  #{abstract} mensaje(m): String
}
class ModoNuevo
class ModoEdicion
IteradorPlantilla <|-- IteradorSecuencial
MediadorPlantilla <|-- MediadorVentana
EstadoFormulario <|-- ModoNuevo
EstadoFormulario <|-- ModoEdicion
@enduml
```

</details>

### Template Method: diagrama de actividades



![Template Method: diagrama de actividades](docs/diagramas/p08_template_actividad.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p08_template_actividad.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
|IteradorPlantilla.siguiente()|
start
:1. tieneSiguiente()? (paso fijo);
:2. elementoActual() (paso que cambia);
:3. avanzar() (paso que cambia);
:4. devolver el elemento (paso fijo);
|MediadorPlantilla.notificar()|
:1. registrarEvento(evento) (paso fijo);
:2. reaccionar(evento) (paso que cambia);
:3. actualizarVista() (paso que cambia);
|EstadoFormulario.entrar()|
:1. prepararFormulario(m) (cambia);
:2. permiteEditar() (cambia);
:3. configurarControles(...) (fijo);
:4. mostrarEstado(mensaje(m)) (fijo);
stop
@enduml
```

</details>

### Template Method: diagrama de secuencia



![Template Method: diagrama de secuencia](docs/diagramas/p09_template_secuencia.png)

<details>
<summary>Ver código PlantUML (<code>docs/diagramas/p09_template_secuencia.puml</code>)</summary>

```plantuml
@startuml
skinparam shadowing false
participant WindowsView as W
participant MediadorVentana as M
participant "estado\n(ModoNuevo/ModoEdicion)" as S
W -> M : notificar("filaSeleccionada")
note right of M : método plantilla (final)
M -> M : registrarEvento(evento)
M -> M : reaccionar(evento)
M -> S : filaSeleccionada(m)
S -> M : cambiarEstado(new ModoEdicion())
M -> S : entrar(m)
S -> S : prepararFormulario(m)
S -> S : permiteEditar()
M -> M : actualizarVista()
@enduml
```

</details>

## Capturas del programa

| Versión | Captura |
|---|---|
| Iterator | ![Iterator](docs/capturas/iterator_1_inicio.png) |
| Mediator | ![Mediator](docs/capturas/mediator_2_seleccion.png) |
| State (modo nuevo) | ![State nuevo](docs/capturas/state_1_inicio.png) |
| State (modo edición) | ![State edición](docs/capturas/state_2_seleccion.png) |
| Cuatro patrones | ![Cuatro patrones](docs/capturas/template_2_seleccion.png) |

## Evidencia de ejecución por consola

Prueba con dos registros, un ID repetido, un ID con formato incorrecto, consulta de todos, consulta por ID, modificación y eliminación:

```text
=== CRUD de estudiantes ===
1. Registrar estudiante
2. Consultar estudiantes
3. Modificar estudiante
4. Eliminar estudiante
0. Salir
Opcion: 1
ID (L00XXXXXX): L00123456
Nombre: Ana
Apellido: Torres
Direccion: Calle 10
Estudiante registrado.

Opcion: 1
ID (L00XXXXXX): L00654321
Nombre: Luis
Apellido: Perez
Direccion: Av. Quito
Estudiante registrado.

Opcion: 1
ID (L00XXXXXX): L00123456
Nombre: Repetido
Apellido: Prueba
Direccion: Centro
Error: Ya existe un estudiante con ese ID.

Opcion: 1
ID (L00XXXXXX): L00-12
Nombre: X
Apellido: Y
Direccion: Z
Error: El ID debe tener el formato L00 seguido de 6 numeros (ej: L00123456) y los campos no pueden quedar vacios.

Opcion: 2
ID (L00XXXXXX, o 0 para consultar todos): 0

ID: L00123456
Nombre: Ana
Apellido: Torres
Direccion: Calle 10

ID: L00654321
Nombre: Luis
Apellido: Perez
Direccion: Av. Quito

Opcion: 2
ID (L00XXXXXX, o 0 para consultar todos): L00123456

ID: L00123456
Nombre: Ana
Apellido: Torres
Direccion: Calle 10

Opcion: 3
ID (L00XXXXXX): L00123456
Nuevo nombre: Ana Maria
Nuevo apellido: Ruiz
Nueva direccion: Carrera 7
Estudiante modificado.

Opcion: 4
ID (L00XXXXXX): L00654321
Estudiante eliminado.

Opcion: 2
ID (L00XXXXXX, o 0 para consultar todos): 0

ID: L00123456
Nombre: Ana Maria
Apellido: Ruiz
Direccion: Carrera 7

Opcion: 0
Hasta luego.
```

## Cómo ejecutar

Requisitos: JDK 17 o superior con `javac` en el PATH.

```bash
# Ventana (por defecto)
ejecutar.bat

# Consola
ejecutar.bat --console
```

También se puede compilar a mano:

```bash
mkdir bin
javac -encoding UTF-8 -d bin -sourcepath src src/Main.java
java -cp bin Main            # ventana
java -cp bin Main --console  # consola
```

Los datos se guardan en `datos/estudiantes.txt`.

## Notas

- Las versiones individuales de Iterator, Mediator y State validan el ID con el formato `L00-0000`; esta versión con los cuatro patrones usa `L00` más 6 números (por ejemplo `L00007867`).

## Integrantes

- Rubén Bustos
- Estefany Pincha
- Mateo Venegas
