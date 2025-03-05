package main.View.gestionDeEventos;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeUsuario.Usuario;

public class EditarEvento {

    private GestorDeEventos controller;
    private JFrame frame;
    private Usuario usuario;
    private Evento evento;

    public EditarEvento(GestorDeEventos controller, Usuario usuario, Evento evento) {
        this.controller = controller;
        this.usuario = usuario;
        this.evento = evento;
        frame = new JFrame("Editar evento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        agregarTitulo();
        crearFormulario();

        frame.setVisible(true); // Hacer visible la ventana
    }

    private void agregarTitulo() {
        JLabel titulo = new JLabel("Editar evento");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        frame.add(titulo, BorderLayout.NORTH);
    }

    private void crearFormulario() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.BLACK);

        // Campos del formulario
        JTextField tituloField = crearTextField(evento.getTitulo());
        JTextField fechaHoraInicioField = crearTextField(evento.getFechaHoraInicio());
        JTextField fechaHoraFinField = crearTextField(evento.getFechaHoraFin());
        JTextField ubicacionField = crearTextField(evento.getUbicacion());
        JTextField descripcionField = crearTextField(evento.getDescripcion());

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

        // Botones
        JButton editarBtn = crearBoton("Editar evento", new Color(51, 51, 51));
        JButton cancelarBtn = crearBoton("Cancelar", new Color(51, 51, 51));

        editarBtn.addActionListener(e -> {
            String titulo = tituloField.getText().trim();
            String fechaHoraInicio = fechaHoraInicioField.getText().trim();
            String fechaHoraFin = fechaHoraFinField.getText().trim();
            String ubicacion = ubicacionField.getText().trim();
            String descripcion = descripcionField.getText().trim();
        
            // Validar campos vacíos
            if (titulo.isEmpty() || descripcion.isEmpty() || fechaHoraInicio.isEmpty()
                    || fechaHoraFin.isEmpty() || ubicacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                // Convertir las fechas a LocalDateTime
                LocalDateTime inicio = LocalDateTime.parse(fechaHoraInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime fin = LocalDateTime.parse(fechaHoraFin, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        
                // Actualizar el evento
                evento.setTitulo(titulo);
                evento.setFechaHoraInicio(inicio);
                evento.setFechaHoraFin(fin);
                evento.setUbicacion(ubicacion);
                evento.setDescripcion(descripcion);
        
                // Mostrar los valores actualizados (para depuración)
                System.out.println("Evento actualizado:");
                System.out.println("Título: " + evento.getTitulo());
                System.out.println("Fecha Inicio: " + evento.getFechaHoraInicio());
                System.out.println("Fecha Fin: " + evento.getFechaHoraFin());
                System.out.println("Ubicación: " + evento.getUbicacion());
                System.out.println("Descripción: " + evento.getDescripcion());
        
                // Actualizar el evento en el archivo
                controller.getRepositorio().guardarEnArchivo();
        
                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Evento actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
                // Volver a la vista MisEventos
                MisEventos misEventos = new MisEventos(controller, usuario);
                misEventos.setVisible(true);
                frame.dispose(); // Cerrar la ventana actual
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Use YYYY-MM-DD HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarBtn.addActionListener(e -> {
            MisEventos misEventos = new MisEventos(controller, usuario);
            misEventos.setVisible(true);
            frame.dispose(); // Cerrar la ventana actual
        });

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonPanel.setBackground(Color.BLACK);
        botonPanel.add(editarBtn);
        botonPanel.add(cancelarBtn);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botonPanel, BorderLayout.SOUTH);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        return etiqueta;
    }

    private JTextField crearTextField(String texto) {
        JTextField textField = new JTextField(texto, 15);
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
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