package main.View.GestionDePublicaciones;

//Paquetes importados
//import main.Entidad.Publicacion;
import main.Controller.GestorPublicaciones.GestorPublicaciones;
import main.model.Publicacion;


//Librerias
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class VentanaPublicaciones extends JFrame {

    //Atributos
    JPanel panel;
    JButton crearPublicacionBtn;
    JPanel PublicacionesPanel;
    private GestorPublicaciones gestor;

    // Crear la ventana principal
    public VentanaPublicaciones(GestorPublicaciones gestor) {
        this.gestor = gestor; //asignar gestor de la clase Main
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        // Crear el panel de botones con nuevo diseño 3D
        panel = new JPanel();
        panel.setBackground(new Color(0, 102, 204)); // Color de fondo más llamativo
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(panel, BorderLayout.NORTH);

        // Botón para crear Publicacions con un estilo más atractivo
        crearPublicacionBtn = new JButton("Crear Publicacion");
        crearPublicacionBtn.setFont(new Font("Arial", Font.BOLD, 14));
        crearPublicacionBtn.setBackground(new Color(255, 69, 0)); // Color llamativo
        crearPublicacionBtn.setForeground(Color.WHITE);
        crearPublicacionBtn.setFocusPainted(false);
        crearPublicacionBtn.setBorder(BorderFactory.createRaisedBevelBorder()); // Botón 3D
        panel.add(crearPublicacionBtn);

        // Panel para los Publicacions
        PublicacionesPanel = new JPanel();
        PublicacionesPanel.setLayout(new BoxLayout(PublicacionesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(PublicacionesPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Acción para crear Publicacion
        //crearPublicacionBtn.addActionListener(e -> mostrarFormularioCrearPublicacion(PublicacionesPanel));
        //Crear Publicacion con otra ventana
        crearPublicacionBtn.addActionListener(e -> {
            VentanaCrearPublicacion ventanaCrearPublicacion = new VentanaCrearPublicacion(gestor, this); // Pasar la instancia actual
            ventanaCrearPublicacion.setVisible(true);
            setVisible(false); //Visibilidad de la ventana actual
        });

    }

    /* 
    private void mostrarFormularioCrearPublicacion(JPanel PublicacionesPanel) {

        //atributos del formulario
        JTextField tituloField = new JTextField(15);
        JTextField fechaDePublicacionField = new JTextField(15);
        JTextField categoriaField = new JTextField(15);
        JTextField descripcionField = new JTextField(15);
        JTextField creadorField = new JTextField(15);

        //Crear etiquetas
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Fecha de Publicacion (YYYY-MM-DD HH:mm):"));
        panel.add(fechaDePublicacionField);
        panel.add(new JLabel("Categoria:"));
        panel.add(categoriaField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Creador:"));
        panel.add(creadorField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Crear Nuevo Publicacion",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String titulo = tituloField.getText().trim();
            String fechaDePublicacion = fechaDePublicacionField.getText().trim();
            String categoria = categoriaField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            String creador = creadorField.getText().trim();

            if (!titulo.isEmpty() && !fechaDePublicacion.isEmpty() && !categoria.isEmpty() && !descripcion.isEmpty() && !creador.isEmpty()) {
                gestor.crearPublicacion(titulo, fechaDePublicacion, categoria, descripcion,  creador);
                actualizarPublicacionsArea(PublicacionesPanel);
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    */

    //Get de PublicacionesPanel
    public JPanel getPublicacionesPanel() {
        return PublicacionesPanel;
    }

    public void actualizarPublicacionsArea(JPanel PublicacionsPanel) {
        List<Publicacion> Publicacions = gestor.getTodosLosPublicaciones();
        PublicacionsPanel.removeAll(); // Limpiar el panel de Publicacions antes de agregar los nuevos

        for (Publicacion Publicacion : Publicacions) {
            // Crear un panel para cada Publicacion
            JPanel PublicacionPanel = new JPanel();
            PublicacionPanel.setLayout(new BoxLayout(PublicacionPanel, BoxLayout.X_AXIS));
            PublicacionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Bordes de los Publicacions
            PublicacionPanel.setBackground(new Color(245, 245, 245)); // Fondo más suave para los Publicacions

            // Información del Publicacion
            JLabel infoLabel = new JLabel("<html><b>" + Publicacion.getTitulo() + "</b><br>" +
                    "Fecha Inicio: " + Publicacion.getfechaDePublicacion() + "<br>Estado: " + Publicacion.getEstado() + "</html>");
            PublicacionPanel.add(infoLabel);

            // Si el Publicacion está pendiente, mostrar los botones de aprobar y rechazar
            if (Publicacion.getEstado().equals("Pendiente")) {
                JButton aprobarBtn = new JButton("Aprobar");
                JButton rechazarBtn = new JButton("Rechazar");

                // Acción para aprobar
                aprobarBtn.addActionListener(e -> {
                    gestor.aprobarPublicacion(Publicacion);
                    actualizarPublicacionsArea(PublicacionsPanel);
                });

                // Acción para rechazar
                rechazarBtn.addActionListener(e -> {
                    gestor.rechazarPublicacion(Publicacion);
                    actualizarPublicacionsArea(PublicacionsPanel);
                });

                // Estilo 3D para los botones
                aprobarBtn.setBorder(BorderFactory.createRaisedBevelBorder());
                rechazarBtn.setBorder(BorderFactory.createRaisedBevelBorder());

                // Agregar los botones al panel
                PublicacionPanel.add(aprobarBtn);
                PublicacionPanel.add(rechazarBtn);
            }

            // Agregar el panel del Publicacion al panel principal
            PublicacionsPanel.add(PublicacionPanel);
        }

        // Recargar el panel para que se muestren los Publicacions actualizados
        PublicacionsPanel.revalidate();
        PublicacionsPanel.repaint();
    }
    
}
