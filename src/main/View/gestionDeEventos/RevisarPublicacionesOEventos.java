package main.View.gestionDeEventos;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeUsuario.Dashboard;

public class RevisarPublicacionesOEventos {

    private GestorDeEventos controller;
    private JFrame frame;
    private JPanel eventosPanel;
    private Usuario usuario;

    public RevisarPublicacionesOEventos(GestorDeEventos controller, Usuario usuario) {
        this.controller = controller;
        this.usuario = usuario;
        frame = new JFrame("Revisar publicaciones o eventos");
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
        JLabel titulo = new JLabel("Publicaciones/Eventos por revisar");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE); // Texto en blanco
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Margen superior de 50 y inferior de 20
        frame.add(titulo, BorderLayout.NORTH);
    }

    private void crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.BLACK); // Fondo negro

        JButton dashboardBtn = crearBoton("Dashboard", new Color(51, 51, 51));

        dashboardBtn.addActionListener(e -> {
            frame.setVisible(false);
            Dashboard dashboard = new Dashboard(controller, usuario);
            dashboard.setVisible(true);
        });

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

        // Crear un panel contenedor con margen
        JPanel contenedorEventos = new JPanel(new BorderLayout());
        contenedorEventos.setBackground(Color.BLACK); // Fondo negro
        contenedorEventos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen de 20 píxeles en todos los lados
        contenedorEventos.add(eventosPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contenedorEventos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        scrollPane.getViewport().setBackground(Color.BLACK); // Fondo negro

        frame.add(scrollPane, BorderLayout.CENTER);

        // Leer los eventos desde el repositorio y mostrar solo los eventos pendientes
        actualizarEventosArea();
    }

    private void actualizarEventosArea() {
        // Obtener los eventos desde el GestorDeEventos, que a su vez los obtiene del repositorio
        List<Evento> eventos = controller.getTodosLosEventos(); // Ahora esto funciona correctamente
        eventosPanel.removeAll();

        for (Evento evento : eventos) {
            if (evento.getEstado().equals("Pendiente")) {
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

                JButton aprobarBtn = crearBoton("Aprobar", new Color(51, 51, 51));
                JButton rechazarBtn = crearBoton("Rechazar", new Color(51, 51, 51));

                aprobarBtn.addActionListener(e -> {
                    // Lógica para aprobar el evento
                    controller.aprobarEvento(evento); // Cambia a aprobarEvento
                    actualizarEventosArea(); // Actualizar la lista después de aprobar
                });

                rechazarBtn.addActionListener(e -> {
                    // Lógica para rechazar el evento
                    controller.rechazarEvento(evento); // Cambia a rechazarEvento
                    actualizarEventosArea(); // Actualizar la lista después de rechazar
                });

                eventoPanel.add(aprobarBtn);
                eventoPanel.add(rechazarBtn);

                eventosPanel.add(eventoPanel);
            }
        }

        eventosPanel.revalidate();
        eventosPanel.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}