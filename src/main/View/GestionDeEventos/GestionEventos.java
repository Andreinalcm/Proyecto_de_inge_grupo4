package main.View.GestionDeEventos;
import java.util.List;
import main.Model.GestionDeEventos.Evento;

public interface GestionEventos {
    void crearEvento(String titulo, String descripcion, String fechaHoraInicio, String fechaHoraFin, String ubicacion, String creador);
    void aprobarPublicacion(Evento evento);
    void rechazarEvento(Evento evento);
    List<Evento> getEventosPendientes();
    List<Evento> getEventosAprobados();
    List<Evento> getEventosRechazados();
}
