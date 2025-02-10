package main;
import javax.swing.*;


import main.Controller.GestionDeEventos.GestorEventos;
import main.Model.GestionDeEventos.Evento;
import main.Model.GestionDeEventos.RepositorioEventos;
import main.Model.GestionDeEventos.RepositorioEventosMemoria;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Ventana principal de la interfaz gráfica
public class Main {
    private static GestorEventos gestor;

    public static void main(String[] args) {
        RepositorioEventos repositorio = new RepositorioEventosMemoria();
        gestor = GestorEventos.getInstancia(repositorio);

        // Crear la ventana principal
        JFrame frame = new JFrame("Gestión de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Aumentamos el tamaño para más espacio
        frame.setLayout(new BorderLayout());

        // Crear el panel de botones
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);

        // Botón para crear evento
        JButton crearEventoBtn = new JButton("Crear Evento");
        panel.add(crearEventoBtn);

        // Botón para aprobar evento
        JButton aprobarEventoBtn = new JButton("Aprobar Evento");
        panel.add(aprobarEventoBtn);

        // Botón para mostrar eventos pendientes y aprobados
        JButton mostrarEventosBtn = new JButton("Mostrar Eventos");
        panel.add(mostrarEventosBtn);

        // Lista de eventos
        JTextArea eventosArea = new JTextArea();
        eventosArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventosArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Acción para crear evento
        crearEventoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog("Título del evento:");
                String descripcion = JOptionPane.showInputDialog("Descripción del evento:");
                String fecha = JOptionPane.showInputDialog("Fecha del evento:");
                if (titulo != null && descripcion != null && fecha != null) {
                    gestor.crearEvento(titulo, descripcion, fecha);
                    actualizarEventosArea(eventosArea, "Pendientes");
                }
            }
        });

        // Acción para aprobar evento
        aprobarEventoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog("Ingrese el título del evento a aprobar:");
                Evento evento = buscarEventoPorTitulo(titulo);
                if (evento != null) {
                    gestor.aprobarPublicacion(evento);
                    actualizarEventosArea(eventosArea, "Pendientes");
                    JOptionPane.showMessageDialog(frame, "Evento aprobado: " + evento.getTitulo());
                } else {
                    JOptionPane.showMessageDialog(frame, "Evento no encontrado.");
                }
            }
        });

        // Acción para mostrar eventos pendientes y aprobados
        mostrarEventosBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = JOptionPane.showInputDialog("Mostrar eventos: Pendientes / Aprobados");
                if ("Pendientes".equalsIgnoreCase(tipo)) {
                    actualizarEventosArea(eventosArea, "Pendientes");
                } else if ("Aprobados".equalsIgnoreCase(tipo)) {
                    actualizarEventosArea(eventosArea, "Aprobados");
                } else {
                    JOptionPane.showMessageDialog(frame, "Opción no válida.");
                }
            }
        });

        // Inicializar la ventana
        frame.setVisible(true);
    }

    // Método para actualizar la lista de eventos en el área de texto
    private static void actualizarEventosArea(JTextArea eventosArea, String tipo) {
        List<Evento> eventos = null;
        if ("Pendientes".equalsIgnoreCase(tipo)) {
            eventos = gestor.getEventosPendientes();
        } else if ("Aprobados".equalsIgnoreCase(tipo)) {
            eventos = gestor.getEventosAprobados();
        }

        eventosArea.setText(""); // Limpiar el área
        for (Evento evento : eventos) {
            eventosArea.append("Título: " + evento.getTitulo() + "\n");
            eventosArea.append("Descripción: " + evento.getDescripcion() + "\n");
            eventosArea.append("Fecha: " + evento.getFecha() + "\n\n");
        }
    }

    // Método para buscar un evento por su título
    private static Evento buscarEventoPorTitulo(String titulo) {
        for (Evento evento : gestor.getEventosPendientes()) {
            if (evento.getTitulo().equalsIgnoreCase(titulo)) {
                return evento;
            }
        }
        return null;
    }
}