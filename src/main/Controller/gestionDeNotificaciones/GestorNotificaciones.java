package main.Controller.gestionDeNotificaciones;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class GestorNotificaciones {
    private static GestorNotificaciones instance;
    private static final String RUTA_NOTIFICACIONES = "main/Data/Notificaciones.txt";

    private GestorNotificaciones() {}

    public static GestorNotificaciones getInstance() {
        if (instance == null) {
            instance = new GestorNotificaciones();
        }
        return instance;
    }

    // Método estático para agregar notificación de evento aprobado
    public static void agregarNotificacionEvento(String nombreEvento, String creador, String fecha) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_NOTIFICACIONES, true))) {
            // Agregar un salto de línea si el archivo no está vacío
            if (new File(RUTA_NOTIFICACIONES).length() > 0) {
                writer.newLine();
            }
            writer.write("EVENTO," + nombreEvento + "," + creador + "," + fecha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método estático para agregar notificación de publicación
    public static void agregarNotificacionPublicacion(String tituloPublicacion, String creador, String fecha) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_NOTIFICACIONES, true))) {
            // Agregar un salto de línea si el archivo no está vacío
            if (new File(RUTA_NOTIFICACIONES).length() > 0) {
                writer.newLine();
            }
            writer.write("PUBLICACION," + tituloPublicacion + "," + creador + "," + fecha);
        } catch (IOException e) {
            System.out.println("Error al escribir notificación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener notificaciones para un usuario específico
    public void mostrarNotificaciones(String usuarioActual) {
        List<String[]> notificaciones = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_NOTIFICACIONES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4 && !partes[2].equals(usuarioActual)) {
                    notificaciones.add(partes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear ventana de notificaciones con estilo similar al dashboard
        JFrame frame = new JFrame("Notificaciones");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        
        // Panel principal con fondo negro
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // Panel para las notificaciones con GridLayout
        JPanel notificacionesPanel = new JPanel();
        notificacionesPanel.setLayout(new BoxLayout(notificacionesPanel, BoxLayout.Y_AXIS));
        notificacionesPanel.setBackground(Color.BLACK);

        // Agregar cada notificación como un panel individual
        for (String[] notificacion : notificaciones) {
            JPanel panelNotificacion = new JPanel();
            panelNotificacion.setLayout(new BorderLayout());
            panelNotificacion.setBackground(new Color(50, 50, 50));
            panelNotificacion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Texto de la notificación
            String tipo = notificacion[0];
            String titulo = notificacion[1];
            String creador = notificacion[2];
            String fecha = notificacion[3];
            
            JLabel labelTitulo = new JLabel(tipo + ": " + titulo);
            labelTitulo.setForeground(Color.WHITE);
            labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));
            
            JLabel labelDetalles = new JLabel("Creado por: " + creador + " - Fecha: " + fecha);
            labelDetalles.setForeground(Color.LIGHT_GRAY);
            
            // Botón para ver detalles
            JButton btnVerDetalles = new JButton("Ver Detalles");
            btnVerDetalles.setBackground(new Color(70, 70, 70));
            btnVerDetalles.setForeground(Color.WHITE);
            btnVerDetalles.setFocusPainted(false);
            
            btnVerDetalles.addActionListener(e -> {
                if (tipo.equals("EVENTO")) {
                    // Llamar a la vista de detalles del evento
                    mostrarDetallesEvento(titulo);
                } else {
                    // Llamar a la vista de detalles de la publicación
                    mostrarDetallesPublicacion(titulo);
                }
            });
            
            // Panel para el contenido de la notificación
            JPanel contenidoPanel = new JPanel();
            contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
            contenidoPanel.setBackground(new Color(50, 50, 50));
            contenidoPanel.add(labelTitulo);
            contenidoPanel.add(labelDetalles);
            
            panelNotificacion.add(contenidoPanel, BorderLayout.CENTER);
            panelNotificacion.add(btnVerDetalles, BorderLayout.EAST);
            
            // Agregar margen entre notificaciones
            panelNotificacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            notificacionesPanel.add(panelNotificacion);
            notificacionesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Scroll pane con estilo
        JScrollPane scrollPane = new JScrollPane(notificacionesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void mostrarDetallesEvento(String tituloEvento) {
        try (BufferedReader reader = new BufferedReader(new FileReader("main/Data/Evento.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(tituloEvento)) {
                    // Crear panel de detalles del evento
                    JPanel panelDetalles = new JPanel();
                    panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
                    panelDetalles.setBackground(new Color(50, 50, 50));
                    
                    JLabel titulo = new JLabel("Evento: " + partes[0]);
                    JLabel fechaInicio = new JLabel("Fecha Inicio: " + partes[1]);
                    JLabel fechaFin = new JLabel("Fecha Fin: " + partes[2]);
                    JLabel ubicacion = new JLabel("Ubicación: " + partes[3]);
                    JLabel descripcion = new JLabel("Descripción: " + partes[4]);
                    JLabel creador = new JLabel("Creador: " + partes[5]);
                    
                    // Estilo de las etiquetas
                    Component[] labels = {titulo, fechaInicio, fechaFin, ubicacion, descripcion, creador};
                    for (Component label : labels) {
                        ((JLabel)label).setForeground(Color.WHITE);
                        ((JLabel)label).setFont(new Font("Arial", Font.PLAIN, 14));
                        panelDetalles.add(label);
                        panelDetalles.add(Box.createRigidArea(new Dimension(0, 10)));
                    }
                    
                    // Mostrar detalles en un JOptionPane personalizado
                    JOptionPane.showMessageDialog(null, panelDetalles, 
                        "Detalles del Evento", 
                        JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetallesPublicacion(String tituloPublicacion) {
        // Buscar en todas las carpetas de usuarios
        File dataDir = new File("main/Data/Publicaciones");
        for (File userDir : dataDir.listFiles()) {
            if (userDir.isDirectory()) {
                File publicacionFile = new File(userDir, tituloPublicacion + ".txt");
                if (publicacionFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(publicacionFile))) {
                        // Leer los datos de la publicación
                        String titulo = reader.readLine();
                        String fecha = reader.readLine();
                        String categoria = reader.readLine();
                        String creador = reader.readLine();
                        StringBuilder contenido = new StringBuilder();
                        String linea;
                        while ((linea = reader.readLine()) != null && !linea.equals("Aprobado") && !linea.equals("Rechazado")) {
                            contenido.append(linea).append("\n");
                        }
                        
                        // Crear panel de detalles
                        JPanel panelDetalles = new JPanel();
                        panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
                        panelDetalles.setBackground(new Color(50, 50, 50));
                        
                        JLabel labelTitulo = new JLabel("Título: " + titulo);
                        JLabel labelFecha = new JLabel("Fecha: " + fecha);
                        JLabel labelCategoria = new JLabel("Categoría: " + categoria);
                        JLabel labelCreador = new JLabel("Creador: " + creador);
                        JLabel labelContenido = new JLabel("Contenido: " + contenido.toString());
                        
                        // Estilo de las etiquetas
                        Component[] labels = {labelTitulo, labelFecha, labelCategoria, labelCreador, labelContenido};
                        for (Component label : labels) {
                            ((JLabel)label).setForeground(Color.WHITE);
                            ((JLabel)label).setFont(new Font("Arial", Font.PLAIN, 14));
                            panelDetalles.add(label);
                            panelDetalles.add(Box.createRigidArea(new Dimension(0, 10)));
                        }
                        
                        // Mostrar detalles en un JOptionPane personalizado
                        JOptionPane.showMessageDialog(null, panelDetalles, 
                            "Detalles de la Publicación", 
                            JOptionPane.PLAIN_MESSAGE);
                        return;
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
} 