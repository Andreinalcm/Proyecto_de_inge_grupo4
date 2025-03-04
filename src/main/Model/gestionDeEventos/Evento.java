package main.Model.gestionDeEventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        return fechaHoraInicio.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getFechaHoraFin() {
        return fechaHoraFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Evento evento = (Evento) o;
        return Objects.equals(titulo, evento.titulo) &&
                Objects.equals(getFechaHoraInicio(), evento.getFechaHoraInicio()) &&
                Objects.equals(getFechaHoraFin(), evento.getFechaHoraFin()) &&
                Objects.equals(ubicacion, evento.ubicacion) &&
                Objects.equals(descripcion, evento.descripcion) &&
                Objects.equals(creador, evento.creador) &&
                Objects.equals(estado, evento.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador, estado);
    }
}