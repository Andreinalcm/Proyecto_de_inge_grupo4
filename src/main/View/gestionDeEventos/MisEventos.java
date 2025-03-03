package main.View.gestionDeEventos;

import java.awt.*;
import javax.swing.*;
import java.util.List; 
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
        frame.getContentPane().setBackground(Color.BLACK);

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
        panel.setBackground(Color.BLACK);

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
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return boton;
    }

    private void crearPanelEventos() {
        eventosPanel = new JPanel();
        eventosPanel.setLayout(new BoxLayout(eventosPanel, BoxLayout.Y_AXIS));
        eventosPanel.setBackground(Color.BLACK);

        // Crear un panel contenedor con margen
        JPanel contenedorEventos = new JPanel(new BorderLayout());
        contenedorEventos.setBackground(Color.BLACK);
        contenedorEventos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen de 20 píxeles en todos los lados
        contenedorEventos.add(eventosPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contenedorEventos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.BLACK);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Leer los eventos desde el repositorio y mostrar solo los eventos del usuario
        actualizarEventosArea();
    }

    private void actualizarEventosArea() {
        // Obtener los eventos desde el GestorDeEventos, que a su vez los obtiene del repositorio
        List<Evento> eventos = controller.getTodosLosEventos();
        eventosPanel.removeAll();

        for (Evento evento : eventos) {
            if (evento.getCreador().equals(usuario.getNombre())) {
                JPanel eventoPanel = new JPanel();
                eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.X_AXIS));
                eventoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                eventoPanel.setBackground(new Color(51, 51, 51));

                JLabel infoLabel = new JLabel("<html><b>" + evento.getTitulo() + "</b><br>"
                        + "Fecha Inicio: " + evento.getFechaHoraInicio() + "<br>"
                        + "Fecha Fin: " + evento.getFechaHoraFin() + "<br>"
                        + "Descripción: " + evento.getDescripcion() + "</html>");
                infoLabel.setForeground(Color.WHITE);
                eventoPanel.add(infoLabel);

                JButton verBtn = crearBoton("Ver", new Color(51, 51, 51));
                JButton editarBtn = crearBoton("Editar", new Color(51, 51, 51));
                JButton eliminarBtn = crearBoton("Eliminar", new Color(51, 51, 51));

                verBtn.addActionListener(e -> {
                    // Abrir la vista de detalles del evento
                    VistaDeEvento vistaDeEvento = new VistaDeEvento(evento, frame, usuario,
                            controller.getRepositorio());
                    vistaDeEvento.getFrame().setVisible(true);
                });

                editarBtn.addActionListener(e -> {
                    // Abrir la vista EditarEvento con los datos del evento seleccionado
                    EditarEvento editarEvento = new EditarEvento(controller, usuario, evento);
                    editarEvento.getFrame().setVisible(true);
                    frame.setVisible(false); // Ocultar la vista actual
                });

                eliminarBtn.addActionListener(e -> {
                    // Falta implementar
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

    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}