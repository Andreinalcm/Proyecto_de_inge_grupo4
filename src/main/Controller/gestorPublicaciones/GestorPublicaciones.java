package main.Controller.gestorPublicaciones;

//Paquetes importados
//import main.Entidad.Publicacion;
import main.Model.gestionPublicacion.Publicacion;
import main.Controller.gestionDeNotificaciones.GestorNotificaciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Librerias
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorPublicaciones {

    //atributos
    private static GestorPublicaciones instancia;
    private final Publicacion repositorio; //Atributo donde se almacenan las publicaciones a gestionar
    private final List<Publicacion> PublicacionesAprobados;
    private final List<Publicacion> PublicacionesRechazados;
    
    //Metodos
    private GestorPublicaciones(Publicacion repositorio) {
        this.repositorio = repositorio;
        this.PublicacionesAprobados = new ArrayList<>();
        this.PublicacionesRechazados = new ArrayList<>();
    }

    public static GestorPublicaciones getInstancia(Publicacion repositorio) {
        if (instancia == null) {
            instancia = new GestorPublicaciones(repositorio);
        }
        return instancia;
    }

    public void crearPublicacion(String titulo, String fechaDePublicacion, String categoria, String descripcion, String creador) {
        if (!validarFechaHora(fechaDePublicacion)) {
            JOptionPane.showMessageDialog(null, "El formato de fecha y hora debe ser: YYYY-MM-DD HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Publicacion publicacion = new Publicacion(titulo, fechaDePublicacion, categoria, descripcion, creador);
        repositorio.agregar(publicacion);
    }

    public List<Publicacion> getPublicacionesPendientes() {
        return repositorio.obtenerTodos();
    }

    public void aprobarPublicacion(Publicacion Publicacion) {
        Publicacion.setEstado("Aprobado");
        repositorio.eliminar(Publicacion);
        PublicacionesAprobados.add(Publicacion);
        
        // Formatear la fecha correctamente para la notificación
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            LocalDateTime fechaHora = LocalDateTime.parse(Publicacion.getfechaDePublicacion(), inputFormatter);
            
            GestorNotificaciones.agregarNotificacionPublicacion(
                Publicacion.getTitulo(),
                Publicacion.getCreador(),
                fechaHora.format(outputFormatter)
            );
        } catch (Exception e) {
            System.out.println("Error al crear la notificación: " + e.getMessage());
        }
    }

    public void rechazarPublicacion(Publicacion Publicacion) {
        Publicacion.setEstado("Rechazado");
        repositorio.eliminar(Publicacion);
        PublicacionesRechazados.add(Publicacion);
    }

    public List<Publicacion> getPublicacionesAprobados() {
        return PublicacionesAprobados;
    }

    public List<Publicacion> getPublicacionesRechazados() {
        return PublicacionesRechazados;
    }

    public List<Publicacion> getTodosLosPublicaciones() {
        List<Publicacion> todosLosPublicaciones = new ArrayList<>(getPublicacionesPendientes());
        todosLosPublicaciones.addAll(getPublicacionesAprobados());
        todosLosPublicaciones.addAll(getPublicacionesRechazados());
        return todosLosPublicaciones;
    }

    private boolean validarFechaHora(String fechaHora) {
        String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fechaHora);
        return matcher.matches();
    }

    //Metodo para cargar el texto desde un archivo
    public String obtenerContenidoArchivo(String rutaArchivo) {
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

    public Publicacion getRepositorio(){
        return repositorio;
    }

}


