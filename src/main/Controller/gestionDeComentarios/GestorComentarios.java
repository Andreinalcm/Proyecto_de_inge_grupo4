package main.Controller.gestionDeComentarios;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;

public class GestorComentarios {
    private static GestorComentarios instance;  // Instancia única

    private GestorComentarios() {}  // Constructor privado

    // Método estático para obtener la única instancia
    public static GestorComentarios getInstance() {
        if (instance == null) {
            instance = new GestorComentarios();
        }
        return instance;
    }

    // Método para guardar un comentario
    public void guardarComentario(String directorioComentarios, String nombreArchivo, String usuario, String comentario) {
        if (comentario != null && !comentario.trim().isEmpty()) {
            String rutaComentarios = directorioComentarios + "comentarios_" + nombreArchivo + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaComentarios, true))) {
                String fechaHora = LocalDateTime.now().toString(); 

                writer.write(usuario + " | " + fechaHora + " | " + comentario);
                writer.newLine();

                JOptionPane.showMessageDialog(null, "Comentario guardado correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el comentario.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // Método para mostrar comentarios
    public void mostrarComentarios(String directorioComentarios, String nombreArchivo) {
        String rutaComentarios = directorioComentarios + "comentarios_" + nombreArchivo + ".txt";
        StringBuilder comentarios = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaComentarios))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                comentarios.append(linea).append("\n");
            }
        } catch (IOException e) {
            comentarios.append("No hay comentarios para esta publicación.");
        }

        // Crear un JTextArea para mostrar los comentarios correctamente
        JTextArea textAreaComentarios = new JTextArea(comentarios.toString());
        textAreaComentarios.setEditable(false);
        textAreaComentarios.setLineWrap(true);
        textAreaComentarios.setWrapStyleWord(true);

        // Crear un JScrollPane para permitir scroll en los comentarios
        JScrollPane scrollPane = new JScrollPane(textAreaComentarios);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Mostrar la ventana con los comentarios en scroll
        JOptionPane.showMessageDialog(null, scrollPane, "Comentarios", JOptionPane.INFORMATION_MESSAGE);
    }
}