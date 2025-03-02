package main.View.gestionDePublicaciones;

import javax.swing.*;
import javax.swing.border.Border;

import main.Model.gestionPublicacion.Publicacion;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MostrarPublicacion extends JFrame {

    private JTextArea textArea;
    private JScrollPane scrollPanel;
    private JPanel panel, panelBotonesInferior;
    private JButton misPublicacionesButton;
    private String directorioPublicaciones = "main/Data/Publicaciones/";
    String rutaArchivo, contenido;

    public MostrarPublicacion(String nombreArchivo) {
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

        misPublicacionesButton = new JButton("Mis Publicaciones");
        panelBotonesInferior.add(misPublicacionesButton);

        add(panelBotonesInferior, BorderLayout.SOUTH); // Añadir el panel al fondo

        setVisible(true);

        //Aciones de los botones

        misPublicacionesButton.addActionListener(e -> {
            dispose();
        });
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
