package main.Model.GestionDeEventos;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Implementación en memoria del repositorio de eventos
public class RepositorioEventosMemoria implements RepositorioEventos {
    private final List<Evento> eventos = new ArrayList<>();

    @Override
    public void agregar(Evento evento) {
        eventos.add(evento);
    }

    @Override
    public boolean eliminar(Evento evento) {
        return eventos.remove(evento);
    }

    @Override
    public List<Evento> obtenerTodos() {
        return new ArrayList<>(eventos);
    }
}