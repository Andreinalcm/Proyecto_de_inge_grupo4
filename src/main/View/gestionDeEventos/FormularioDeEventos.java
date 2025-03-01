package main.View.gestionDeEventos;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeUsuario.Dashboard;

public class FormularioDeEventos {

    private GestorDeEventos controller;
    private JFrame frame;
    private Usuario usuario;

    public FormularioDeEventos(GestorDeEventos controller, Usuario usuario) {
        this.controller = controller;
        this.usuario = usuario;
        frame = new JFrame("Publicar evento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        agregarTitulo();
        crearFormulario();
    }

    private void agregarTitulo() {
        JLabel titulo = new JLabel("Publicar evento");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
    }

    private void crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.BLACK); // Fondo negro

        JTextField tituloField = crearTextField();
        JTextField fechaHoraInicioField = crearTextField();
        JTextField fechaHoraFinField = crearTextField();
        JTextField ubicacionField = crearTextField();
        JTextField descripcionField = crearTextField();

        panel.add(crearEtiqueta("Título:"));
        panel.add(tituloField);
        panel.add(crearEtiqueta("Fecha Inicio (YYYY-MM-DD HH:mm):"));
        panel.add(fechaHoraInicioField);
        panel.add(crearEtiqueta("Fecha Fin (YYYY-MM-DD HH:mm):"));
        panel.add(fechaHoraFinField);
        panel.add(crearEtiqueta("Ubicación:"));
        panel.add(ubicacionField);
        panel.add(crearEtiqueta("Descripción:"));
        panel.add(descripcionField);

        JButton publicarBtn = crearBoton("Publicar evento", new Color(51, 51, 51));
        JButton descartarBtn = crearBoton("Descartar evento", new Color(51, 51, 51));

        publicarBtn.addActionListener(e -> {
            String titulo = tituloField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            String fechaHoraInicio = fechaHoraInicioField.getText().trim();
            String fechaHoraFin = fechaHoraFinField.getText().trim();
            String ubicacion = ubicacionField.getText().trim();
            String estado = "Pendiente";
        
            // Verificar si todos los campos están llenos
            if (titulo.isEmpty() || descripcion.isEmpty() || fechaHoraInicio.isEmpty()
                    || fechaHoraFin.isEmpty() || ubicacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Detener la ejecución si hay campos vacíos
            }
        
            // Intentar crear el evento
            boolean eventoCreado = controller.crearEvento(titulo, descripcion, fechaHoraInicio, fechaHoraFin, ubicacion, usuario, estado);
        
            // Solo abrir la ventana MisEventos si el evento se creó correctamente
            if (eventoCreado) {
                MisEventos misEventos = new MisEventos(controller, usuario);
                misEventos.setVisible(true);
                frame.setVisible(false); // Cerrar la ventana actual
            }
        });

        descartarBtn.addActionListener(e -> {
            Dashboard dashboard = new Dashboard(controller, usuario);
            dashboard.setVisible(true);
            frame.setVisible(false);
        });

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonPanel.setBackground(Color.BLACK);
        botonPanel.add(publicarBtn);
        botonPanel.add(descartarBtn);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botonPanel, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        return etiqueta;
    }

    private JTextField crearTextField() {
        JTextField textField = new JTextField(15);
        textField.setBackground(new Color(51, 51, 51));
        textField.setForeground(Color.WHITE); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return textField;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Radio de 30
                super.paintComponent(g2d);
                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = Math.max(size.width, 150);
                size.height = 40;
                return size;
            }
        };

        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return boton;
    }

    public JFrame getFrame() {
        return frame;
    }
}