package main.View.gestionDeEventos;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeUsuario.Dashboard;

public class MisEventos {

    private GestorDeEventos controller;
    private JFrame frame;
    private JPanel eventosPanel;
    private Usuario usuario;

    public MisEventos(GestorDeEventos controller, Usuario usuario) {
        this.controller = controller;
        this.usuario = usuario;
        frame = new JFrame("Mis eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK); // Fondo negro

        // Agregar el título centrado en la parte superior
        agregarTitulo();
        crearPanelBotones();
        crearPanelEventos();

        frame.setVisible(true);
    }

    private void agregarTitulo() {
        JLabel titulo = new JLabel("Mis eventos");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE); // Texto en blanco
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Margen superior de 50 y inferior de 20
        frame.add(titulo, BorderLayout.NORTH);
    }

    private void crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.BLACK); // Fondo negro

        JButton publicarEventoBtn = crearBoton("Publicar evento", new Color(51, 51, 51));
        JButton dashboardBtn = crearBoton("Dashboard", new Color(51, 51, 51));

        publicarEventoBtn.addActionListener(e -> {
            frame.setVisible(false);
            FormularioDeEventos formulario = new FormularioDeEventos(controller, usuario);
            formulario.getFrame().setVisible(true);
        });

        dashboardBtn.addActionListener(e -> {
            frame.setVisible(false);
            Dashboard dashboard = new Dashboard(controller, usuario);
            dashboard.setVisible(true);
        });

        panel.add(publicarEventoBtn);
        panel.add(dashboardBtn);

        frame.add(panel, BorderLayout.SOUTH); // Botones en la parte inferior
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
                size.width = Math.max(size.width, 150); // Ancho mínimo
                size.height = 40; // Altura fija
                return size;
            }
        };

        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(Color.WHITE); // Texto en blanco
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setOpaque(false); // Fondo transparente
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding
        return boton;
    }

    private void crearPanelEventos() {
        eventosPanel = new JPanel();
        eventosPanel.setLayout(new BoxLayout(eventosPanel, BoxLayout.Y_AXIS));
        eventosPanel.setBackground(Color.BLACK); // Fondo negro

        JScrollPane scrollPane = new JScrollPane(eventosPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        scrollPane.getViewport().setBackground(Color.BLACK); // Fondo negro

        frame.add(scrollPane, BorderLayout.CENTER);

        // Leer el archivo y mostrar solo los eventos del usuario
        actualizarEventosArea();
    }

    private void actualizarEventosArea() {
        List<Evento> eventos = leerEventosDelArchivo();
        eventosPanel.removeAll();

        for (Evento evento : eventos) {
            if (evento.getCreador().equals(usuario.getNombre())) {
                JPanel eventoPanel = new JPanel();
                eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.X_AXIS));
                eventoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                eventoPanel.setBackground(new Color(51, 51, 51)); // Fondo oscuro

                JLabel infoLabel = new JLabel("<html><b>" + evento.getTitulo() + "</b><br>"
                        + "Fecha Inicio: " + evento.getFechaHoraInicio() + "<br>"
                        + "Fecha Fin: " + evento.getFechaHoraFin() + "<br>"
                        + "Descripción: " + evento.getDescripcion() + "</html>");
                infoLabel.setForeground(Color.WHITE); // Texto en blanco
                eventoPanel.add(infoLabel);

                JButton verBtn = crearBoton("Ver", new Color(51, 51, 51));
                JButton editarBtn = crearBoton("Editar", new Color(51, 51, 51));
                JButton eliminarBtn = crearBoton("Eliminar", new Color(51, 51, 51));

                verBtn.addActionListener(e -> {
                    // Abrir la vista de detalles del evento
                    VistaDeEvento eventoVista = new VistaDeEvento(evento, frame);
                    eventoVista.getFrame().setVisible(true);
                });

                editarBtn.addActionListener(e -> {
                    // Lógica para editar el evento
                });

                eliminarBtn.addActionListener(e -> {
                    // Lógica para eliminar el evento
                });

                eventoPanel.add(verBtn);
                eventoPanel.add(editarBtn);
                eventoPanel.add(eliminarBtn);

                eventosPanel.add(eventoPanel);
            }
        }

        eventosPanel.revalidate();
        eventosPanel.repaint();
    }

    private List<Evento> leerEventosDelArchivo() {
        List<Evento> eventos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Formato de fecha
    
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/Data/Evento.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 7) { // Asegúrate de que hay 7 campos
                    String titulo = partes[0].trim();
                    String fechaInicioStr = partes[1].trim();
                    String fechaFinStr = partes[2].trim();
                    String ubicacion = partes[3].trim();
                    String descripcion = partes[4].trim();
                    String nombreUsuario = partes[5].trim();
                    String estado = partes[6].trim();
    
                    // Convertir las fechas de String a LocalDateTime
                    LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr, formatter);
                    LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);
    
                    // Crear el evento con el constructor correcto
                    eventos.add(new Evento(titulo, fechaInicio, fechaFin, ubicacion, descripcion, nombreUsuario, estado));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de eventos.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar los datos del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return eventos;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}