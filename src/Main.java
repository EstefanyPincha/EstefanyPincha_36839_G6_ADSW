import controlador.EstudianteController;
import vista.ConsolaView;
import vista.WindowsView;

public class Main {
    public static void main(String[] args) {
        EstudianteController controlador = new EstudianteController();
        if (args.length > 0 && args[0].equalsIgnoreCase("--console")) {
            new ConsolaView(controlador).ejecutar();
        } else {
            new WindowsView(controlador).iniciar();
        }
    }
}
