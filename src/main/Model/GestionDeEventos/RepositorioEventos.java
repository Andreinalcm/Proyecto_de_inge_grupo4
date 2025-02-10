package main.Model.GestionDeEventos;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Interfaz para el repositorio de eventos
public interface RepositorioEventos {
    void agregar(Evento evento);
    boolean eliminar(Evento evento);
    List<Evento> obtenerTodos();
}