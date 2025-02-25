package main.Model.gestionDeEventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String titulo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String descripcion;
    private String ubicacion;
    private String creador;
    private String estado;

    public Evento(String titulo, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin,
            String ubicacion, String descripcion, String creador, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.ubicacion = ubicacion;
        this.creador = creador;
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaHoraInicioOriginal() {
        return fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFinOriginal() {
        return fechaHoraFin;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // Formatear para mostrar
    }

    public String getFechaHoraFin() {
        return fechaHoraFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // Formatear para mostrar
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCreador() {
        return creador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    } // Cambiar estado del evento

    
}
