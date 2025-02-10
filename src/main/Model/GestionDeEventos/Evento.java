package main.Model.GestionDeEventos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Clase Evento con encapsulación
public class Evento {
    private final String titulo;
    private final String descripcion;
    private final String fecha;

    public Evento(String titulo, String descripcion, String fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getFecha() { return fecha; }
}