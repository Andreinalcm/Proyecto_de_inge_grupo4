package main.View.gestionDeEventos;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;
import main.View.gestionDePublicaciones.MostrarPublicacion;
import main.View.gestionDeUsuario.Dashboard;
import main.Model.gestionDeEventos.Evento;
import main.View.gestionDePublicaciones.VentanaCrearPublicacion;

public class EventosYPublicaciones {

    private GestorDeEventos controller;
    private JFrame frame;
    private JPanel eventosPanel;
    private JPanel publicacionesPanel;
    private Usuario usuario;
    private String directorioPublicaciones = "main/Data/Publicaciones/";
    private GestorPublicaciones gestor;

    public EventosYPublicaciones(GestorDeEventos controller, Usuario usuario, GestorPublicaciones gestor) {
        this.controller = controller;
        this.usuario = usuario;
        this.gestor = gestor;

        frame = new JFrame("Eventos y publicaciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        agregarTitulo();
        crearPanelBotones();
        crearPanelEventos();
        crearPanelPublicaciones();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(eventosPanel),
                new JScrollPane(publicacionesPanel));
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(0.5);

        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void agregarTitulo() {
        JLabel titulo = new JLabel("Eventos y publicaciones");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
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
            Dashboard dashboard = new Dashboard(usuario);
            dashboard.setVisible(true);
        });

        panel.add(publicarEventoBtn);
        panel.add(dashboardBtn);

        if (!usuario.getRol().equals("Estudiante")) {
            JButton hacerPublicacionBtn = crearBoton("Hacer una publicación", new Color(51, 51, 51));
            hacerPublicacionBtn.addActionListener(e -> {
                // Lógica para abrir la vista de creación de publicaciones
                SwingUtilities.invokeLater(() -> new VentanaCrearPublicacion(gestor, usuario));
            });
            panel.add(hacerPublicacionBtn);
        }

        frame.add(panel, BorderLayout.SOUTH);
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

    private void crearPanelEventos() {
        eventosPanel = new JPanel();
        eventosPanel.setLayout(new BoxLayout(eventosPanel, BoxLayout.Y_AXIS));
        eventosPanel.setBackground(Color.BLACK);

        JPanel contenedorEventos = new JPanel(new BorderLayout());
        contenedorEventos.setBackground(Color.BLACK);
        contenedorEventos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contenedorEventos.add(eventosPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contenedorEventos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.BLACK);

        frame.add(scrollPane, BorderLayout.WEST);

        actualizarEventosArea();
    }

    private void crearPanelPublicaciones() {
        publicacionesPanel = new JPanel();
        publicacionesPanel.setLayout(new BoxLayout(publicacionesPanel, BoxLayout.Y_AXIS));
        publicacionesPanel.setBackground(Color.BLACK);

        JPanel contenedorPublicaciones = new JPanel(new BorderLayout());
        contenedorPublicaciones.setBackground(Color.BLACK);
        contenedorPublicaciones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contenedorPublicaciones.add(publicacionesPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(contenedorPublicaciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.BLACK);

        frame.add(scrollPane, BorderLayout.EAST);

        actualizarPublicacionesArea();
    }

    private void actualizarEventosArea() {
        List<Evento> eventos = leerEventosDelArchivo();
        eventosPanel.removeAll();

        for (Evento evento : eventos) {
            if (!evento.getCreador().equals(usuario.getNombre()) && evento.getEstado().equals("Aprobado")) {
                JPanel eventoPanel = new JPanel();
                eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.X_AXIS));
                eventoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                eventoPanel.setBackground(new Color(51, 51, 51));

                JLabel infoLabel = new JLabel("<html><b>" + evento.getTitulo() + "</b><br>"
                        + "Creador: " + evento.getCreador() + "<br>"
                         + "</html>");
                infoLabel.setForeground(Color.WHITE);
                eventoPanel.add(infoLabel);

                JButton agregarBtn = crearBoton("Agregar al calendario", new Color(51, 51, 51));
                JButton verBtn = crearBoton("Ver", new Color(51, 51, 51));

                agregarBtn.addActionListener(e -> {
                    // Lógica para agregar el evento al calendario
                });

                verBtn.addActionListener(e -> {
                    VistaDeEvento vistaDeEvento = new VistaDeEvento(evento, frame, usuario, controller.getRepositorio());
                    vistaDeEvento.getFrame().setVisible(true);
                });

                eventoPanel.add(agregarBtn);
                eventoPanel.add(verBtn);

                eventosPanel.add(eventoPanel);
            }
        }

        eventosPanel.revalidate();
        eventosPanel.repaint();
    }

    private void actualizarPublicacionesArea() {
        List<Publicacion> publicaciones = leerPublicacionesDesdeArchivos();
        publicacionesPanel.removeAll();

        for (Publicacion publicacion : publicaciones) {
            JPanel publicacionPanel = new JPanel();
            publicacionPanel.setLayout(new BoxLayout(publicacionPanel, BoxLayout.X_AXIS));
            publicacionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            publicacionPanel.setBackground(new Color(51, 51, 51));

            JLabel infoLabel = new JLabel("<html><b>" + publicacion.getTitulo() + "</b><br>"
                    + "Creador: " + publicacion.getCreador() + "</html>");
            infoLabel.setForeground(Color.WHITE);
            publicacionPanel.add(infoLabel);

            JPanel botonesPanel = new JPanel();
            botonesPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            botonesPanel.setBackground(new Color(51, 51, 51));

            JButton verBtn = crearBoton("Ver", new Color(51, 51, 51));
            JButton agregarBtn = crearBoton("Añadir", new Color(51, 51, 51));

           // Lógica para el botón "Ver"
            verBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, "Ver publicación: " + publicacion.getTitulo()); // Temporal
                String nombreArchivo = publicacion.getTitulo() + ".txt"; // Obtener el nombre del archivo
                SwingUtilities.invokeLater(() -> new MostrarPublicacion(nombreArchivo, gestor, usuario));
            });

            // Lógica para el botón "agregar"
            agregarBtn.addActionListener(e -> {
                String nombreArchivo = publicacion.getTitulo() + ".txt";
                String rutaOrigen = directorioPublicaciones + nombreArchivo;
                String carpetaUsuario = directorioPublicaciones + usuario.getUsuario(); // Ruta de la carpeta del usuario

                // Verificar si la carpeta del usuario existe
                File directorioUsuario = new File(carpetaUsuario);
                if (!directorioUsuario.exists()) {
                    // Si no existe, crear la carpeta
                    if (directorioUsuario.mkdirs()) {
                        JOptionPane.showMessageDialog(frame, "Carpeta del usuario creada.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error al crear la carpeta del usuario.");
                        return; // Detener la ejecución si no se puede crear la carpeta
                    }
                }

                String rutaDestino = carpetaUsuario + "/" + nombreArchivo; // La ruta destino ahora usa la carpeta del usuario

                try {
                    Files.copy(Paths.get(rutaOrigen), Paths.get(rutaDestino), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(frame, "Publicación añadida a tu carpeta.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error al añadir la publicación: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });

            botonesPanel.add(verBtn);
            botonesPanel.add(agregarBtn);

            publicacionPanel.add(botonesPanel);
            publicacionesPanel.add(publicacionPanel);
        }

        publicacionesPanel.revalidate();
        publicacionesPanel.repaint();
    }

    private List<Publicacion> leerPublicacionesDesdeArchivos() {
        List<Publicacion> publicaciones = new ArrayList<>();
        File directorio = new File(directorioPublicaciones);
        File[] archivos = directorio.listFiles();
    
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                    if (ultimaLineaEsAprobado(archivo)) { // Verificar la última línea
                        Publicacion publicacion = leerPublicacionDesdeArchivo(archivo);
                        if (publicacion != null) {
                            publicaciones.add(publicacion);
                        }
                    }
                }
            }
        }
        return publicaciones;
    }
    
    private boolean ultimaLineaEsAprobado(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String ultimaLinea = null;
            String lineaActual;
            while ((lineaActual = br.readLine()) != null) {
                ultimaLinea = lineaActual;
            }
            return ultimaLinea != null && ultimaLinea.trim().equalsIgnoreCase("Aprobado");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Publicacion leerPublicacionDesdeArchivo(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String titulo = br.readLine();
            String fecha = br.readLine();
            String categoria = br.readLine();
            String creador = br.readLine();
            StringBuilder descripcionBuilder = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                descripcionBuilder.append(linea).append("\n");
            }
            String descripcion = descripcionBuilder.toString();

            return new Publicacion(titulo, fecha, categoria, descripcion, creador);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Evento> leerEventosDelArchivo() {
        List<Evento> eventos = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader("main/Data/Evento.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 7) {
                    String titulo = partes[0].trim();
                    String fechaInicioStr = partes[1].trim();
                    String fechaFinStr = partes[2].trim();
                    String ubicacion = partes[3].trim();
                    String descripcion = partes[4].trim();
                    String nombreUsuario = partes[5].trim();
                    String estado = partes[6].trim();

                    LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr, formatter);
                    LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);

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