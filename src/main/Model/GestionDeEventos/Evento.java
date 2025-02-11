package main.Model.GestionDeEventos;
/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 */

public class Evento {
    private final String titulo;
    private final String descripcion;
    private final String fechaHoraInicio;
    private final String fechaHoraFin;
    private final String ubicacion;
    private final String creador;
    private String estado; // Estado: "Pendiente", "Aprobado", "Rechazado"

    public Evento(String titulo, String descripcion, String fechaHoraInicio, String fechaHoraFin, String ubicacion, String creador) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.ubicacion = ubicacion;
        this.creador = creador;
        this.estado = "Pendiente"; // Inicialmente está pendiente
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getFechaHoraInicio() { return fechaHoraInicio; }
    public String getFechaHoraFin() { return fechaHoraFin; }
    public String getUbicacion() { return ubicacion; }
    public String getCreador() { return creador; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; } // Cambiar estado del evento
}
