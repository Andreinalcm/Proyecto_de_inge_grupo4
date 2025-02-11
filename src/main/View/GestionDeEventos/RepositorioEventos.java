package main.View.GestionDeEventos;
import main.Model.GestionDeEventos.Evento;
import java.util.List;
/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 */

public interface RepositorioEventos {
    void agregar(Evento evento);
    boolean eliminar(Evento evento);
    List<Evento> obtenerTodos();
}
