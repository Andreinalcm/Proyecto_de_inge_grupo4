package main.Controller.GestionDeEventos;
import javax.swing.*;

import main.Model.GestionDeEventos.Evento;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Interfaz para la gestión de eventos
public interface GestionEventos {
    void crearEvento(String titulo, String descripcion, String fecha);
    void aprobarPublicacion(Evento evento);
    List<Evento> getEventosPendientes();
    List<Evento> getEventosAprobados();
}