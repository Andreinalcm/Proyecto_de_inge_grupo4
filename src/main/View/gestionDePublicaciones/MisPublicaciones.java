package main.View.gestionDePublicaciones;

//Paquetes importados
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionPublicacion.Publicacion;
import main.View.gestionDeUsuario.Dashboard;

//Librerias
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MisPublicaciones extends JFrame{

    private JPanel panelPrincipal, panelPublicaciones, panelBotonesInferior;
    private JButton dashboardBtn;
    private GestorPublicaciones gestor;
    private String directorioPublicaciones = "main/Data/Publicaciones/"; // Directorio de publicaciones

    public MisPublicaciones(GestorPublicaciones gestor) {
        this.gestor = gestor;

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

        cargarPublicacionesDesdeArchivos(); // Cambiado para cargar desde archivos

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

        /*dashboardBtn.addActionListener(e -> {
            frame.setVisible(false);
            Dashboard dashboard = new Dashboard(controller, usuario);
            dashboard.setVisible(true);
        });*/
    }

    private void cargarPublicacionesDesdeArchivos() {
        File directorio = new File(directorioPublicaciones);
        File[] archivos = directorio.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                    Publicacion publicacion = leerPublicacionDesdeArchivo(archivo);
                    if (publicacion != null) {
                        agregarPublicacionAlPanel(publicacion);
                    }
                }
            }
        }
    }

    private Publicacion leerPublicacionDesdeArchivo(File archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String titulo = br.readLine();
            String fecha = br.readLine();
            String categoria = br.readLine();
            StringBuilder descripcionBuilder = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (br.ready()) { // Verifica si hay más líneas antes de agregar un salto de línea
                    descripcionBuilder.append(linea).append("\n");
                } else {
                    descripcionBuilder.append(linea); // Evita el salto de línea en la última línea
                }
            }
            String descripcion = descripcionBuilder.toString();
            String creador = linea; // La última línea es el creador

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
            SwingUtilities.invokeLater(() -> new MostrarPublicacion(nombreArchivo));
        });

        /* 
        botonEditar.addActionListener(e -> {
            String nombreArchivo = publicacion.getTitulo() + ".txt";
            File archivo = new File(directorioPublicaciones + nombreArchivo);
            String contenido = leerContenidoArchivo(archivo); // Método para leer el contenido del archivo
        
            if (contenido != null) {
                SwingUtilities.invokeLater(() -> {
                    EditarPublicacion editarPublicacion = new EditarPublicacion(contenido, publicacion); // Pasar el contenido y la publicacion
                    editarPublicacion.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        */

        botonEliminar.addActionListener(e -> {
            String nombreArchivo = publicacion.getTitulo() + ".txt"; // Obtener el nombre del archivo
            File archivoAEliminar = new File(directorioPublicaciones + nombreArchivo); // Ruta completa del archivo
        
            if (archivoAEliminar.exists()) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta publicación?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (archivoAEliminar.delete()) {
                        JOptionPane.showMessageDialog(null, "Publicación eliminada correctamente.");
                        // Actualizar la vista eliminando el panel de la publicación eliminada
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
    

}
