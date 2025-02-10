package main.Controller.GestionDeEventos;
import javax.swing.*;

import main.Model.GestionDeEventos.Evento;
import main.Model.GestionDeEventos.RepositorioEventos;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Implementación del gestor de eventos con el patrón Singleton
public class GestorEventos implements GestionEventos {
    private static GestorEventos instancia;
    private final RepositorioEventos repositorio;
    private final List<Evento> eventosAprobados; // Lista para eventos aprobados
    
    private GestorEventos(RepositorioEventos repositorio) {
        this.repositorio = repositorio;
        this.eventosAprobados = new ArrayList<>();
    }

    public static GestorEventos getInstancia(RepositorioEventos repositorio) {
        if (instancia == null) {
            instancia = new GestorEventos(repositorio);
        }
        return instancia;
    }

    @Override
    public void crearEvento(String titulo, String descripcion, String fecha) {
        Evento evento = new Evento(titulo, descripcion, fecha);
        repositorio.agregar(evento);
    }

    @Override
    public List<Evento> getEventosPendientes() {
        return repositorio.obtenerTodos();
    }

    @Override
    public void aprobarPublicacion(Evento evento) {
        if (repositorio.eliminar(evento)) {
            eventosAprobados.add(evento); // Agregar a eventos aprobados
        }
    }

    @Override
    public List<Evento> getEventosAprobados() {
        return eventosAprobados;
    }
}