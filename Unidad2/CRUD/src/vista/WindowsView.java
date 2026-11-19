package vista;

import controlador.EstudianteController;
import vista.mediador.Mediador;
import vista.mediador.MediadorVentana;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Ventana principal. Solo crea y ubica los componentes.
 * Cuando el usuario hace algo, los componentes le avisan al MEDIADOR.
 */
public class WindowsView extends JFrame {
    private final JTextField campoId = new JTextField();
    private final JTextField campoNombre = new JTextField();
    private final JTextField campoApellido = new JTextField();
    private final JTextField campoDireccion = new JTextField();
    private final JButton botonRegistrar = crearBoton("Registrar");
    private final JButton botonActualizar = crearBoton("Actualizar");
    private final JButton botonEliminar = crearBoton("Eliminar");
    private final JButton botonLimpiar = crearBoton("Limpiar");
    private final JLabel etiquetaEstado = new JLabel(" ");
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Direccion"}, 0) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
    private final JTable tabla = new JTable(modeloTabla);

    public WindowsView(EstudianteController controlador) {
        super("Estudiantes");
        configurarVentana();
        crearControles();

        Mediador mediador = new MediadorVentana(this, controlador);
        botonRegistrar.addActionListener(e -> mediador.notificar("registrar"));
        botonActualizar.addActionListener(e -> mediador.notificar("actualizar"));
        botonEliminar.addActionListener(e -> mediador.notificar("eliminar"));
        botonLimpiar.addActionListener(e -> mediador.notificar("limpiar"));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                mediador.notificar("filaSeleccionada");
            }
        });
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 660);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(16, 16));
        getContentPane().setBackground(new Color(245, 248, 251));
    }

    private void crearControles() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(18, 39, 66));
        encabezado.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        JLabel titulo = new JLabel("Estudiantes");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        encabezado.add(titulo, BorderLayout.WEST);
        add(encabezado, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(2, 2, 12, 8));
        formulario.setOpaque(false);
        formulario.add(crearCampo("ID (L00XXXXXX)", campoId));
        formulario.add(crearCampo("Nombre", campoNombre));
        formulario.add(crearCampo("Apellido", campoApellido));
        formulario.add(crearCampo("Direccion", campoDireccion));

        JPanel botones = new JPanel(new GridLayout(1, 4, 10, 10));
        botones.setOpaque(false);
        botones.add(botonRegistrar);
        botones.add(botonActualizar);
        botones.add(botonEliminar);
        botones.add(botonLimpiar);

        JPanel superior = new JPanel(new BorderLayout(12, 12));
        superior.setOpaque(false);
        superior.add(formulario, BorderLayout.NORTH);
        superior.add(botones, BorderLayout.SOUTH);

        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        etiquetaEstado.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel centro = new JPanel(new BorderLayout(12, 12));
        centro.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        centro.setOpaque(false);
        centro.add(superior, BorderLayout.NORTH);
        centro.add(new JScrollPane(tabla), BorderLayout.CENTER);
        centro.add(etiquetaEstado, BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearCampo(String etiqueta, JTextField campo) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setOpaque(false);
        JLabel label = new JLabel(etiqueta);
        label.setForeground(new Color(18, 39, 66));
        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(31, 66, 106));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return boton;
    }

    public void iniciar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    // ----- Getters que usa el mediador -----
    public JTextField getCampoId() { return campoId; }
    public JTextField getCampoNombre() { return campoNombre; }
    public JTextField getCampoApellido() { return campoApellido; }
    public JTextField getCampoDireccion() { return campoDireccion; }
    public JButton getBotonRegistrar() { return botonRegistrar; }
    public JButton getBotonActualizar() { return botonActualizar; }
    public JButton getBotonEliminar() { return botonEliminar; }
    public JLabel getEtiquetaEstado() { return etiquetaEstado; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTabla() { return tabla; }
}
