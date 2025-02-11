package main.Controller.GestionDeEventos;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.View.GestionDeEventos.GestionEventos;
import main.Model.GestionDeEventos.Evento;
import main.View.GestionDeEventos.RepositorioEventos;
/*
 * import java.awt.*;
import java.awt.event.*;
 */

public class GestorDeEventos implements GestionEventos {
    private static GestorDeEventos instancia;
    private final RepositorioEventos repositorio;
    private final List<Evento> eventosAprobados;
    private final List<Evento> eventosRechazados;

    private GestorDeEventos(RepositorioEventos repositorio) {
        this.repositorio = repositorio;
        this.eventosAprobados = new ArrayList<>();
        this.eventosRechazados = new ArrayList<>();
    }

    public static GestorDeEventos getInstancia(RepositorioEventos repositorio) {
        if (instancia == null) {
            instancia = new GestorDeEventos(repositorio);
        }
        return instancia;
    }

    @Override
    public void crearEvento(String titulo, String descripcion, String fechaHoraInicio, String fechaHoraFin, String ubicacion, String creador) {
        if (!validarFechaHora(fechaHoraInicio) || !validarFechaHora(fechaHoraFin)) {
            JOptionPane.showMessageDialog(null, "El formato de fecha y hora debe ser: YYYY-MM-DD HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (compararFechas(fechaHoraInicio, fechaHoraFin)) {
            JOptionPane.showMessageDialog(null, "La fecha de fin debe ser posterior a la fecha de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Evento evento = new Evento(titulo, descripcion, fechaHoraInicio, fechaHoraFin, ubicacion, creador);
        repositorio.agregar(evento);
    }

    @Override
    public List<Evento> getEventosPendientes() {
        return repositorio.obtenerTodos();
    }

    @Override
    public void aprobarPublicacion(Evento evento) {
        evento.setEstado("Aprobado");
        repositorio.eliminar(evento);
        eventosAprobados.add(evento);
    }

    @Override
    public void rechazarEvento(Evento evento) {
        evento.setEstado("Rechazado");
        repositorio.eliminar(evento);
        eventosRechazados.add(evento);
    }

    @Override
    public List<Evento> getEventosAprobados() {
        return eventosAprobados;
    }

    @Override
    public List<Evento> getEventosRechazados() {
        return eventosRechazados;
    }

    public List<Evento> getTodosLosEventos() {
        List<Evento> todosLosEventos = new ArrayList<>(getEventosPendientes());
        todosLosEventos.addAll(getEventosAprobados());
        todosLosEventos.addAll(getEventosRechazados());
        return todosLosEventos;
    }

    private boolean validarFechaHora(String fechaHora) {
        String regex = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fechaHora);
        return matcher.matches();
    }

    private boolean compararFechas(String fechaHoraInicio, String fechaHoraFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime inicio = LocalDateTime.parse(fechaHoraInicio, formatter);
        LocalDateTime fin = LocalDateTime.parse(fechaHoraFin, formatter);
        return fin.isBefore(inicio);
    }
}
