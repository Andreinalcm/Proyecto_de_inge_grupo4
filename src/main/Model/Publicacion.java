package main.model;

//Librerias
import java.util.ArrayList;
import java.util.List;

public class Publicacion {

    //atributos
    private final String titulo;
    private final String fechaDePublicacion;
    private final String categoria;
    private final String descripcion;
    private final String creador;
    private String estado; // Estado: "Pendiente", "Aprobado", "Rechazado"
    private final List<Publicacion> Publicaciones = new ArrayList<>(); //Almacenamiento de Publicaciones

    //Metodos

    //Construcctores
    public Publicacion(String titulo, String fechaDePublicacion, String categoria, String descripcion, String creador) {
        this.titulo = titulo;
        this.fechaDePublicacion = fechaDePublicacion;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.creador = creador;
        this.estado = "Pendiente"; // Inicialmente está pendiente
    }

    public Publicacion() {
        this.titulo = null;
        this.fechaDePublicacion = null;
        this.categoria = null;
        this.descripcion = null;
        this.creador = null;
        this.estado = null;
    }

    //Metodos Get
    public String getTitulo() { return titulo; }
    public String getfechaDePublicacion() { return fechaDePublicacion; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public String getCreador() { return creador; }
    public String getEstado() { return estado; }

    //Metodos Set
    public void setEstado(String estado) { this.estado = estado; } // Cambiar estado del Publicacion

    //Metodos del Arreglo
    public void agregar(Publicacion Publicacion) {
        Publicaciones.add(Publicacion);
    }

    public boolean eliminar(Publicacion Publicacion) {
        return Publicaciones.remove(Publicacion);
    }

    public List<Publicacion> obtenerTodos() {
        return new ArrayList<>(Publicaciones);
    }
    
}
