package main.Controller.gestionNotificacion;

import main.Model.gestionNotificacion.Notificacion;
import main.Model.gestionDeUsuario.Usuario;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorNotificaciones {
    private static final String ARCHIVO_NOTIFICACIONES = "src/main/Data/NOTIFICATION.txt";
    private static GestorNotificaciones instancia;

    private GestorNotificaciones() {
        crearArchivoSiNoExiste();
    }

    public static GestorNotificaciones getInstancia() {
        if (instancia == null) {
            instancia = new GestorNotificaciones();
        }
        return instancia;
    }

    private void crearArchivoSiNoExiste() {
        File archivo = new File(ARCHIVO_NOTIFICACIONES);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void agregarNotificacion(String titulo, String fecha, String creador, String tipo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_NOTIFICACIONES, true))) {
            String notificacion = String.format("%s,%s,%s,%s\n", titulo, fecha, creador, tipo);
            writer.write(notificacion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarNotificacionesParaUsuario(Usuario usuarioActual) {
        List<Notificacion> notificaciones = leerNotificaciones();
        StringBuilder mensaje = new StringBuilder("Eventos y publicaciones recientes:\n\n");
        boolean hayNotificaciones = false;

        for (Notificacion notif : notificaciones) {
            if (!notif.getCreador().equals(usuarioActual.getNombre())) {
                hayNotificaciones = true;
                mensaje.append(String.format("%s: %s\nFecha: %s\nCreado por: %s\n\n",
                    notif.getTipo(), notif.getTitulo(), notif.getFecha(), notif.getCreador()));
            }
        }

        if (hayNotificaciones) {
            JOptionPane.showMessageDialog(null, mensaje.toString(), 
                "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<Notificacion> leerNotificaciones() {
        List<Notificacion> notificaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_NOTIFICACIONES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    notificaciones.add(new Notificacion(partes[0], partes[1], partes[2], partes[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notificaciones;
    }
} 