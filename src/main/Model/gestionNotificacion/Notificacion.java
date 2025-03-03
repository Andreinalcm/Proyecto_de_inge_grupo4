package main.Model.gestionNotificacion;

public class Notificacion {
    private String titulo;
    private String fecha;
    private String creador;
    private String tipo; // "Evento" o "Publicacion"

    public Notificacion(String titulo, String fecha, String creador, String tipo) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.creador = creador;
        this.tipo = tipo;
    }

    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public String getCreador() { return creador; }
    public String getTipo() { return tipo; }
} 