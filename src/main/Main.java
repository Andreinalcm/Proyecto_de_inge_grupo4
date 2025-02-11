package main;

import javax.swing.*;

import main.Controller.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Controller.GestionDeEventos.GestorDeEventos;
import main.Controller.GestionDeEventos.RepositorioEventosMemoria;
import main.View.GestionDeEventos.RepositorioEventos;
import main.View.GestionDeEventos.GestionEventos;
import main.Model.GestionDeEventos.Evento;
//import main.Controller.GestionDeEventos.GestorDeEventos;

public class Main {
    private static GestorDeEventos gestor;

    public static void main(String[] args) {
        RepositorioEventos repositorio = new RepositorioEventosMemoria();
        gestor = GestorDeEventos.getInstancia(repositorio);

        // Crear la ventana principal
        JFrame frame = new JFrame("Gestión de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Crear el panel de botones con nuevo diseño 3D
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 204)); // Color de fondo más llamativo
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.add(panel, BorderLayout.NORTH);

        // Botón para crear eventos con un estilo más atractivo
        JButton crearEventoBtn = new JButton("Crear Evento");
        crearEventoBtn.setFont(new Font("Arial", Font.BOLD, 14));
        crearEventoBtn.setBackground(new Color(255, 69, 0)); // Color llamativo
        crearEventoBtn.setForeground(Color.WHITE);
        crearEventoBtn.setFocusPainted(false);
        crearEventoBtn.setBorder(BorderFactory.createRaisedBevelBorder()); // Botón 3D
        panel.add(crearEventoBtn);

        // Panel para los eventos
        JPanel eventosPanel = new JPanel();
        eventosPanel.setLayout(new BoxLayout(eventosPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(eventosPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Acción para crear evento con un formulario
        crearEventoBtn.addActionListener(e -> mostrarFormularioCrearEvento(eventosPanel));

        // Inicializar la ventana
        frame.setVisible(true);
    }

    private static void mostrarFormularioCrearEvento(JPanel eventosPanel) {
        JTextField tituloField = new JTextField(15);
        JTextField descripcionField = new JTextField(15);
        JTextField fechaHoraInicioField = new JTextField(15);
        JTextField fechaHoraFinField = new JTextField(15);
        JTextField ubicacionField = new JTextField(15);
        JTextField creadorField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Fecha Inicio (YYYY-MM-DD HH:mm):"));
        panel.add(fechaHoraInicioField);
        panel.add(new JLabel("Fecha Fin (YYYY-MM-DD HH:mm):"));
        panel.add(fechaHoraFinField);
        panel.add(new JLabel("Ubicación:"));
        panel.add(ubicacionField);
        panel.add(new JLabel("Creador:"));
        panel.add(creadorField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Crear Nuevo Evento",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String titulo = tituloField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            String fechaHoraInicio = fechaHoraInicioField.getText().trim();
            String fechaHoraFin = fechaHoraFinField.getText().trim();
            String ubicacion = ubicacionField.getText().trim();
            String creador = creadorField.getText().trim();

            if (!titulo.isEmpty() && !descripcion.isEmpty() && !fechaHoraInicio.isEmpty() &&
                !fechaHoraFin.isEmpty() && !ubicacion.isEmpty() && !creador.isEmpty()) {
                gestor.crearEvento(titulo, descripcion, fechaHoraInicio, fechaHoraFin, ubicacion, creador);
                actualizarEventosArea(eventosPanel);
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void actualizarEventosArea(JPanel eventosPanel) {
        List<Evento> eventos = gestor.getTodosLosEventos();
        eventosPanel.removeAll(); // Limpiar el panel de eventos antes de agregar los nuevos

        for (Evento evento : eventos) {
            // Crear un panel para cada evento
            JPanel eventoPanel = new JPanel();
            eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.X_AXIS));
            eventoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Bordes de los eventos
            eventoPanel.setBackground(new Color(245, 245, 245)); // Fondo más suave para los eventos

            // Información del evento
            JLabel infoLabel = new JLabel("<html><b>" + evento.getTitulo() + "</b><br>" +
                    "Fecha Inicio: " + evento.getFechaHoraInicio() + "<br>Estado: " + evento.getEstado() + "</html>");
            eventoPanel.add(infoLabel);

            // Si el evento está pendiente, mostrar los botones de aprobar y rechazar
            if (evento.getEstado().equals("Pendiente")) {
                JButton aprobarBtn = new JButton("Aprobar");
                JButton rechazarBtn = new JButton("Rechazar");

                // Acción para aprobar
                aprobarBtn.addActionListener(e -> {
                    gestor.aprobarPublicacion(evento);
                    actualizarEventosArea(eventosPanel);
                });

                // Acción para rechazar
                rechazarBtn.addActionListener(e -> {
                    gestor.rechazarEvento(evento);
                    actualizarEventosArea(eventosPanel);
                });

                // Estilo 3D para los botones
                aprobarBtn.setBorder(BorderFactory.createRaisedBevelBorder());
                rechazarBtn.setBorder(BorderFactory.createRaisedBevelBorder());

                // Agregar los botones al panel
                eventoPanel.add(aprobarBtn);
                eventoPanel.add(rechazarBtn);
            }

            // Agregar el panel del evento al panel principal
            eventosPanel.add(eventoPanel);
        }

        // Recargar el panel para que se muestren los eventos actualizados
        eventosPanel.revalidate();
        eventosPanel.repaint();
    }
}
