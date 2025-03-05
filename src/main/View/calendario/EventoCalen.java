package main.View.calendario;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeEventos.FormularioDeEventos;
import main.View.gestionDeUsuario.Dashboard;

public class EventoCalen extends JPanel {

    private GestorDeEventos controller;
    private Usuario usuario;
    private JPanel eventosPanel;

    public EventoCalen(GestorDeEventos controller, Usuario usuario) {
        this.controller = controller;
        this.usuario = usuario;
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        eventosPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        eventosPanel.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(eventosPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonesPanel.setBackground(Color.BLACK);

        JButton nuevoEventoBtn = new JButton("NUEVO");
        nuevoEventoBtn.setFont(new Font("Helvetica", Font.PLAIN, 18)); // Aplica el mismo estilo
        nuevoEventoBtn.setBackground(Color.decode("#808b8c")); // Aplica el mismo estilo
        nuevoEventoBtn.setForeground(Color.WHITE); // Aplica el mismo estilo
        nuevoEventoBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Aplica el mismo estilo
        botonesPanel.add(nuevoEventoBtn);

        nuevoEventoBtn.addActionListener(e -> {
            FormularioDeEventos formulario = new FormularioDeEventos(controller, usuario);
            formulario.getFrame().setVisible(true);
            SwingUtilities.getWindowAncestor(this).setVisible(false);
        });

        JButton dashboardBtn = new JButton("Dashboard");
        botonesPanel.add(dashboardBtn);
        dashboardBtn.setFont(new Font("Helvetica", Font.PLAIN, 18));
        dashboardBtn.setBackground(Color.decode("#808b8c"));
        dashboardBtn.setForeground(Color.WHITE);
        dashboardBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dashboardBtn.addActionListener(e -> {
            Dashboard dashboard = new Dashboard(usuario);
            dashboard.setVisible(true);
            SwingUtilities.getWindowAncestor(this).setVisible(false);
        });

        add(botonesPanel, BorderLayout.SOUTH); // Agrega el panel de botones correctamente

        mostrarEventosConEstiloCalenEvento();
    }

    private void mostrarEventosConEstiloCalenEvento() {
        List<Evento> eventos = controller.getTodosLosEventos();
        System.out.println("Eventos encontrados: " + eventos.size());
        eventosPanel.removeAll();

        for (Evento evento : eventos) {
            if (evento.getCreador().equals(usuario.getNombre())) {
                System.out.println("Evento del usuario: " + evento.getTitulo());
                JPanel eventoPanel = new JPanel(new GridLayout(3, 1)); // Cambia a GridLayout(3, 1)
                eventoPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#00d1e8"))
                ));
                eventoPanel.setBackground(Color.decode("#404747"));
                eventoPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                JLabel tituloLabel = new JLabel(evento.getTitulo()); // Muestra el nombre del evento
                tituloLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                tituloLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
                tituloLabel.setForeground(Color.WHITE);
                eventoPanel.add(tituloLabel);

                JLabel descripcionLabel = new JLabel(evento.getDescripcion()); // Muestra la descripción
                descripcionLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 4, 15));
                descripcionLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
                descripcionLabel.setForeground(Color.WHITE);
                eventoPanel.add(descripcionLabel);

                JLabel fechaInicioLabel = new JLabel("Inicio: " + evento.getFechaHoraInicio().toString()); // Muestra la fecha de inicio
                fechaInicioLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 4, 15));
                fechaInicioLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
                fechaInicioLabel.setForeground(Color.WHITE);
                eventoPanel.add(fechaInicioLabel);

                JLabel fechaFinLabel = new JLabel("Fin: " + evento.getFechaHoraFin().toString()); // Muestra la fecha de fin
                fechaFinLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 4, 15));
                fechaFinLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
                fechaFinLabel.setForeground(Color.WHITE);
                eventoPanel.add(fechaFinLabel);

                eventosPanel.add(eventoPanel);
            }
        }

        eventosPanel.revalidate();
        eventosPanel.repaint();
    }
}