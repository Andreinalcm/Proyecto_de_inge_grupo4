package main.View.gestionDePublicaciones;

//Paquetes importados
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;

//Librerias
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MisPublicaciones extends JFrame {

    private JPanel panelPrincipal, panelPublicaciones, panelBotonesInferior;
    private JButton dashboardBtn;
    private Usuario usuario;
    private GestorPublicaciones gestor;
    private String directorioPublicaciones; // Directorio de publicaciones

    public MisPublicaciones (GestorPublicaciones gestor, Usuario usuario) {
        this.gestor = gestor;
        this.usuario = usuario;
        this.directorioPublicaciones = "main/Data/Publicaciones/" + usuario.getUsuario() + "/"; // Ruta a la carpeta del usuario

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Mis Publicaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JLabel titulo = new JLabel("Mis Publicaciones");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        panelPublicaciones = new JPanel();
        panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));
        panelPublicaciones.setBackground(Color.BLACK);
        panelPublicaciones.setForeground(Color.WHITE);

        cargarPublicacionesDesdeArchivos(); // Cargar desde archivos en la carpeta del usuario

        JScrollPane scrollPane = new JScrollPane(panelPublicaciones);
        //Añadir desplazamiento solo si es necesario
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.BLACK);
        Border margin = BorderFactory.createEmptyBorder(60, 40, 40, 40);
        panelPrincipal.setBorder(margin);
        panelPrincipal.add(scrollPane, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Panel para el botón "Dashboard"
        panelBotonesInferior = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centrar el botón
        panelBotonesInferior.setBackground(Color.BLACK);

        dashboardBtn = new JButton("Dashboard");
        panelBotonesInferior.add(dashboardBtn);

        add(panelBotonesInferior, BorderLayout.SOUTH); // Añadir el panel al fondo

        setVisible(true);

        //Aciones de los botones

        dashboardBtn.addActionListener(e -> {
            dispose();
        });

    }

    private void cargarPublicacionesDesdeArchivos() {
        File directorio = new File(directorioPublicaciones);

        if (!directorio.exists()) {
            JOptionPane.showMessageDialog(this, "No tienes publicaciones.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return; // Salir si la carpeta no existe
        }

        File[] archivos = directorio.listFiles();

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(this, "No tienes publicaciones.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return; // Salir si la carpeta está vacía
        }

        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                Publicacion publicacion = leerPublicacionDesdeArchivo(archivo);
                if (publicacion != null) {
                    agregarPublicacionAlPanel(publicacion);
                }
            }
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

    private void agregarPublicacionAlPanel(Publicacion publicacion) {
        JPanel panelPublicacion = new JPanel();
        panelPublicacion.setLayout(new BorderLayout()); // Usar BorderLayout
        panelPublicacion.setBackground(Color.BLACK);

        JLabel labelTitulo = new JLabel(publicacion.getTitulo());
        labelTitulo.setForeground(Color.WHITE);
        panelPublicacion.add(labelTitulo, BorderLayout.WEST); // Título a la izquierda

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel para los botones
        panelBotones.setBackground(Color.BLACK);

        JButton botonVer = new JButton("Ver");
        JButton botonEditar = new JButton("Editar");
        JButton botonEliminar = new JButton("Eliminar");

        panelBotones.add(botonVer);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        panelPublicacion.add(panelBotones, BorderLayout.EAST); // Botones a la derecha

        panelPublicaciones.add(panelPublicacion);

        botonVer.addActionListener(e -> {
            String nombreArchivo = publicacion.getTitulo() + ".txt"; // Obtener el nombre del archivo
            SwingUtilities.invokeLater(() -> new MostrarPublicacion(nombreArchivo, gestor, usuario));
        });

        botonEditar.addActionListener(e -> {
            // Verificar si el usuario actual es el creador de la publicación
            if (usuario.getUsuario().equals(publicacion.getCreador())) {
                String nombreArchivo = publicacion.getTitulo() + ".txt";
                File archivo = new File(directorioPublicaciones + nombreArchivo);
                Publicacion publicacion2 = leerPublicacionDesdeArchivo(archivo);

                if (publicacion2 != null) {
                    SwingUtilities.invokeLater(() -> {
                        /* 
                        EditarPublicacion editarPublicacion = new EditarPublicacion(publicacion2, gestor, usuario);
                        editarPublicacion.setVisible(true);
                        */
                        SwingUtilities.invokeLater(() -> new EditarPublicacion(publicacion2, gestor, usuario));
                        dispose();
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Solo el creador de la publicación puede modificarla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonEliminar.addActionListener(e -> {
            String nombreArchivo = publicacion.getTitulo() + ".txt";
            File archivoUsuario = new File(directorioPublicaciones + nombreArchivo);
            File archivoPublicaciones = new File("main/Data/Publicaciones/" + nombreArchivo);

            if (archivoUsuario.exists() || archivoPublicaciones.exists()) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta publicación?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean eliminadoUsuario = true;
                    boolean eliminadoPublicaciones = true;

                    // Verificar si el usuario actual es el creador
                    if (usuario.getUsuario().equals(publicacion.getCreador())) {
                        // Si es el creador, eliminar de todas las carpetas
                        if (archivoUsuario.exists()) {
                            eliminadoUsuario = archivoUsuario.delete();
                        }
                        if (archivoPublicaciones.exists()) {
                            eliminadoPublicaciones = eliminarDeTodasLasCarpetas(publicacion);
                        }
                    } else {
                        // Si no es el creador, eliminar solo de la carpeta del usuario
                        if (archivoUsuario.exists()) {
                            eliminadoUsuario = archivoUsuario.delete();
                        }
                        eliminadoPublicaciones = true; // No intentar eliminar de la carpeta principal
                    }

                    if (eliminadoUsuario && eliminadoPublicaciones) {
                        JOptionPane.showMessageDialog(null, "Publicación eliminada correctamente.");
                        panelPublicaciones.remove(panelPublicacion);
                        panelPublicaciones.revalidate();
                        panelPublicaciones.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar la publicación.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }

    private boolean eliminarDeTodasLasCarpetas(Publicacion publicacion) {
        String nombreArchivo = publicacion.getTitulo() + ".txt";
        File directorioPublicaciones = new File("./main/Data/Publicaciones/");
        boolean eliminado = true;

        // Eliminar de la carpeta principal
        File archivoPrincipal = new File(directorioPublicaciones.getAbsolutePath() + "/" + nombreArchivo);
        if (archivoPrincipal.exists()) {
            eliminado = archivoPrincipal.delete();
        }

        // Eliminar de las subcarpetas
        File[] subdirectorios = directorioPublicaciones.listFiles(File::isDirectory);
        if (subdirectorios != null) {
            for (File subdirectorio : subdirectorios) {
                String rutaArchivoSubdirectorio = subdirectorio.getAbsolutePath() + "/" + nombreArchivo;
                File archivoSubdirectorio = new File(rutaArchivoSubdirectorio);
                if (archivoSubdirectorio.exists()) {
                    eliminado = eliminado && archivoSubdirectorio.delete();
                }
            }
        }

        return eliminado;
    }


}