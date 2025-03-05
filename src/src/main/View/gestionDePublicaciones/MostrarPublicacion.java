package main.View.gestionDePublicaciones;

import javax.swing.*;
import javax.swing.border.Border;

import main.Controller.gestionDeComentarios.GestorComentarios;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MostrarPublicacion extends JFrame {

    private JTextArea textArea;
    private JScrollPane scrollPanel;
    private JPanel panel, panelBotonesInferior;
    private JButton misPublicacionesButton, comentarBoton, comentariosBoton;
    private String directorioPublicaciones = "main/Data/Publicaciones/";
    private String directorioComentarios = "main/Data/Comentarios/Publicaciones/";
    private String rutaArchivo, contenido, nombreArchivo;
    private Usuario usuario;
    private GestorPublicaciones gestor;
    private GestorComentarios gestorComentarios;

    public MostrarPublicacion(String nombreArchivo, GestorPublicaciones gestor, Usuario usuario) {
        this.gestor = gestor;
        this.usuario = usuario;
        this.nombreArchivo = nombreArchivo; //Para obtener el nombre del archivo
        this.gestorComentarios = GestorComentarios.getInstance(); //Instanciando el gestor de Comentarios

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Mostrar Publicación");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana
        setSize(600, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JLabel titulo = new JLabel("Mostrar Publicación");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPanel = new JScrollPane(textArea);

        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        Border margin = BorderFactory.createEmptyBorder(0, 40, 100, 40);
        panel.setBorder(margin);
        panel.add(scrollPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);

        // Cargar contenido del archivo
        rutaArchivo = directorioPublicaciones + nombreArchivo;
        contenido = obtenerContenidoArchivo(rutaArchivo);
        textArea.setText(contenido);

        setVisible(true);

        // Panel para el botón "Dashboard"
        panelBotonesInferior = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Centrar el botón
        panelBotonesInferior.setBackground(Color.BLACK);

        misPublicacionesButton = new JButton("Volver");
        comentarBoton = new JButton("Comentar");
        comentariosBoton = new JButton("Comentarios");
        panelBotonesInferior.add(misPublicacionesButton);
        panelBotonesInferior.add(comentarBoton);
        panelBotonesInferior.add(comentariosBoton);

        add(panelBotonesInferior, BorderLayout.SOUTH); // Añadir el panel al fondo

        setVisible(true);

        //Aciones de los botones

        misPublicacionesButton.addActionListener(e -> {
            //Volver a Mis Publicaciones
            SwingUtilities.invokeLater(() -> new MisPublicaciones(gestor, usuario));
            dispose();
        });

        comentarBoton.addActionListener(e -> {
            String comentario = JOptionPane.showInputDialog(this, "Escribe tu comentario:");
            
                gestorComentarios.guardarComentario(directorioComentarios, nombreArchivo, usuario.getUsuario(), comentario);
            
        });

        comentariosBoton.addActionListener(e -> gestorComentarios.mostrarComentarios(directorioComentarios, nombreArchivo));
    }
    
    private String obtenerContenidoArchivo(String rutaArchivo) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al cargar la publicación.";
        }
        return contenido.toString();
    }

}
