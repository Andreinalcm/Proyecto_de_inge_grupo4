package main.Model.gestionDeEventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import main.Model.gestionDeUsuario.Usuario;

public class GestorDeEventos {
    private static GestorDeEventos instancia;
    private RepositorioEventos repositorio;

    private GestorDeEventos(RepositorioEventos repositorio) {
        this.repositorio = repositorio;
    }

    public static GestorDeEventos getInstancia(RepositorioEventos repositorio) {
        if (instancia == null) {
            instancia = new GestorDeEventos(repositorio);
        }
        return instancia;
    }

    public void crearEvento(String titulo, String descripcion, String fechaHoraInicio, String fechaHoraFin, String ubicacion, Usuario creador, String estado) {
        LocalDateTime inicio = LocalDateTime.parse(fechaHoraInicio, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime fin = LocalDateTime.parse(fechaHoraFin, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Evento evento = new Evento(titulo, inicio, fin, ubicacion, descripcion, creador.getNombre(), estado);
        repositorio.guardarEvento(evento, creador);
    }

    public List<Evento> getTodosLosEventos() {
        return repositorio.getTodosLosEventos();
    }

    public void aprobarPublicacion(Evento evento) {
        evento.setEstado("Aprobado");
        repositorio.actualizarEvento(evento);
    }

    public void rechazarEvento(Evento evento) {
        evento.setEstado("Rechazado");
        repositorio.actualizarEvento(evento);
    }
}