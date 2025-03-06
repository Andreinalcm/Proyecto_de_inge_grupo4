package main.View.gestionDeEventos;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;
import main.View.gestionDePublicaciones.MostrarPublicacion;
import main.View.gestionDeUsuario.Dashboard;

public class RevisarPublicacionesOEventos {

    private GestorDeEventos controller;
    private JFrame frame;
    private JPanel eventosPanel;
    private JPanel publicacionesPanel;
    private Usuario usuario;
    private String directorioPublicaciones = "main/Data/Publicaciones/";
    private GestorPublicaciones gestor;

    public RevisarPublicacionesOEventos(GestorDeEventos controller, Usuario usuario, GestorPublicaciones gestor) {
        this.controller = controller;
        this.usuario = usuario;
        this.gestor = gestor;
        frame = new JFrame("Publicaciones/Eventos por revisar");
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
        JLabel titulo = new JLabel("Publicaciones/Eventos por revisar");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        frame.add(titulo, BorderLayout.NORTH);
    }

    private void crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.BLACK);

        JButton dashboardBtn = crearBoton("Dashboard", new Color(51, 51, 51));

        dashboardBtn.addActionListener(e -> {
            frame.setVisible(false);
            Dashboard dashboard = new Dashboard(usuario);
            dashboard.setVisible(true);
        });

        panel.add(dashboardBtn);

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
        eventosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        eventosPanel.setPreferredSize(new Dimension(eventosPanel.getPreferredSize().width, 500));

        actualizarEventosArea();
    }

    private void crearPanelPublicaciones() {
        publicacionesPanel = new JPanel();
        publicacionesPanel.setLayout(new BoxLayout(publicacionesPanel, BoxLayout.Y_AXIS));
        publicacionesPanel.setBackground(Color.BLACK);
        publicacionesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        publicacionesPanel.setPreferredSize(new Dimension(eventosPanel.getPreferredSize().width, 500));

        actualizarPublicacionesArea();
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
                eventoPanel.setPreferredSize(new Dimension(eventoPanel.getPreferredSize().width, 100));

                JLabel infoLabel = new JLabel("<html><b>" + evento.getTitulo() + "</b><br>"
                        + "Creador: " + evento.getCreador() + "<br>"
                         + "</html>");
                infoLabel.setForeground(Color.WHITE); // Texto en blanco
                eventoPanel.add(infoLabel);

                JButton verBtn = crearBoton("Ver", new Color(51, 51, 51));
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

                verBtn.addActionListener(e -> {
                    // Abrir la vista de detalles del evento
                    VistaDeEvento vistaDeEvento = new VistaDeEvento(evento, frame, usuario,
                            controller.getRepositorio());
                    vistaDeEvento.getFrame().setVisible(true);
                });

                eventoPanel.add(aprobarBtn);
                eventoPanel.add(rechazarBtn);
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
            if (publicacion.getEstado().equals("Pendiente")) {
                JPanel publicacionPanel = new JPanel();
                publicacionPanel.setLayout(new BoxLayout(publicacionPanel, BoxLayout.X_AXIS));
                publicacionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                publicacionPanel.setBackground(new Color(51, 51, 51));
                publicacionPanel.setPreferredSize(new Dimension(publicacionPanel.getPreferredSize().width, 100));
    
                // Modificación aquí: Eliminar la línea del estado
                JLabel infoLabel = new JLabel("<html><b>" + publicacion.getTitulo() + "</b><br>"
                        + "Creador: " + publicacion.getCreador() + "</html>");
                infoLabel.setForeground(Color.WHITE);
                publicacionPanel.add(infoLabel);
    
                JPanel botonesPanel = new JPanel();
                botonesPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                botonesPanel.setBackground(new Color(51, 51, 51));
    
                JButton aprobarBtn = crearBoton("Aprobar", new Color(51, 51, 51));
                JButton rechazarBtn = crearBoton("Rechazar", new Color(51, 51, 51));
                JButton verBtn = crearBoton("Ver", new Color(51, 51, 51));
    
                aprobarBtn.addActionListener(e -> {
                    cambiarEstadoPublicacion(publicacion, "Aprobado");
                    actualizarPublicacionesArea();
                });
    
                rechazarBtn.addActionListener(e -> {
                    eliminarPublicacion(publicacion);
                    actualizarPublicacionesArea();
                });
    
                verBtn.addActionListener(e -> {
                    String nombreArchivo = publicacion.getTitulo() + ".txt";
                    SwingUtilities.invokeLater(() -> new MostrarPublicacion(nombreArchivo, gestor, usuario));
                });
    
                botonesPanel.add(aprobarBtn);
                botonesPanel.add(rechazarBtn);
                botonesPanel.add(verBtn);
    
                publicacionPanel.add(botonesPanel);
                publicacionesPanel.add(publicacionPanel);
            }
        }
    
        publicacionesPanel.revalidate();
        publicacionesPanel.repaint();
    }

    private void cambiarEstadoPublicacion(Publicacion publicacion, String nuevoEstado) {
        String nombreArchivo = publicacion.getTitulo() + ".txt";
        File directorioPublicacionesFile = new File(directorioPublicaciones); // Crear File a partir del String
    
        // Cambiar el estado en la carpeta principal
        File archivoPrincipal = new File(directorioPublicacionesFile.getAbsolutePath() + "/" + nombreArchivo);
        cambiarEstadoArchivo(archivoPrincipal, nuevoEstado);
    
        // Cambiar el estado en todas las subcarpetas
        File[] subdirectorios = directorioPublicacionesFile.listFiles(File::isDirectory);
        if (subdirectorios != null) {
            for (File subdirectorio : subdirectorios) {
                String rutaArchivoSubdirectorio = subdirectorio.getAbsolutePath() + "/" + nombreArchivo;
                File archivoSubdirectorio = new File(rutaArchivoSubdirectorio);
                cambiarEstadoArchivo(archivoSubdirectorio, nuevoEstado);
            }
        }
    
        JOptionPane.showMessageDialog(null, "Publicación aprobada.");
    }

    //Funcion para aprobar publicacion
    private void cambiarEstadoArchivo(File archivo, String nuevoEstado) {
        if (archivo.exists()) {
            try {
                List<String> lineas = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea;
                while ((linea = br.readLine()) != null) {
                    lineas.add(linea);
                }
                br.close();
    
                if (!lineas.isEmpty()) {
                    lineas.set(lineas.size() - 1, nuevoEstado); // Cambiar la última línea
                }
    
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Funcion para eliminar publicacion
    private void eliminarPublicacion(Publicacion publicacion) {
        String nombreArchivo = publicacion.getTitulo() + ".txt";
        File directorioPublicacionesFile = new File(directorioPublicaciones);
    
        // Eliminar el archivo en la carpeta principal
        File archivoPrincipal = new File(directorioPublicacionesFile.getAbsolutePath() + "/" + nombreArchivo);
        eliminarArchivo(archivoPrincipal);
    
        // Eliminar el archivo en todas las subcarpetas
        File[] subdirectorios = directorioPublicacionesFile.listFiles(File::isDirectory);
        if (subdirectorios != null) {
            for (File subdirectorio : subdirectorios) {
                String rutaArchivoSubdirectorio = subdirectorio.getAbsolutePath() + "/" + nombreArchivo;
                File archivoSubdirectorio = new File(rutaArchivoSubdirectorio);
                eliminarArchivo(archivoSubdirectorio);
            }
        }
    
        JOptionPane.showMessageDialog(null, "Publicación rechazada y eliminada.");
    }
    
    private void eliminarArchivo(File archivo) {
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    private List<Publicacion> leerPublicacionesDesdeArchivos() {
        List<Publicacion> publicaciones = new ArrayList<>();
        File directorio = new File(directorioPublicaciones);
        File[] archivos = directorio.listFiles();
    
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                    // Verificar el estado antes de leer la publicación completa
                    if (verificarEstadoPendiente(archivo)) {
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
    
    private boolean verificarEstadoPendiente(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String ultimaLinea = null;
            String linea;
            while ((linea = br.readLine()) != null) {
                ultimaLinea = linea;
            }
            return ultimaLinea != null && ultimaLinea.trim().equals("Pendiente");
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
    
            // Leer la última línea para obtener el estado
            String estado = null;
            try (BufferedReader br2 = new BufferedReader(new FileReader(archivo))) {
                while ((linea = br2.readLine()) != null) {
                    estado = linea; // Guardar la última línea leída
                }
            }
            if (estado == null) {
                return null; // Si no hay estado, la publicación es inválida
            }
    
            return new Publicacion(titulo, fecha, categoria, descripcion, creador, estado);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}