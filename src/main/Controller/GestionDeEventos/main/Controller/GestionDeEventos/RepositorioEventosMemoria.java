package main.Controller.GestionDeEventos;
//package main.Controller.GestionDeEventos;

import java.util.ArrayList;
import java.util.List;
import main.View.GestionDeEventos.RepositorioEventos;
import main.Model.GestionDeEventos.Evento;
/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 */

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
