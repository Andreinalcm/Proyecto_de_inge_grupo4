package test;
import main.Model.gestionDeEventos.*;
import main.Model.gestionDeUsuario.Usuario;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.*;

public class RepositorioEventosTest {

    @Test
    public void testGuardarEvento() {
        RepositorioEventosArchivo repositorio = new RepositorioEventosArchivo();
        LocalDateTime inicio = LocalDateTime.of(2023, 10, 1, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2023, 10, 1, 12, 0);
        Evento evento = new Evento("Conferencia", inicio, fin, "Auditorio", "Conferencia sobre IA", "admin", "Pendiente");
        Usuario usuario = new Usuario("Galaxia", "Jesús", "jesus@gmail.com", "Profesor");
       

        repositorio.guardarEvento(evento, usuario);
        List<Evento> eventos = repositorio.getTodosLosEventos();

        assertEquals(1, eventos.size());
        assertEquals("Conferencia", eventos.get(0).getTitulo());
    }

    @Test
    public void testActualizarEvento() {
        RepositorioEventosArchivo repositorio = new RepositorioEventosArchivo();
        LocalDateTime inicio = LocalDateTime.of(2023, 10, 1, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2023, 10, 1, 12, 0);
        Evento evento = new Evento("Conferencia", inicio, fin, "Auditorio", "Conferencia sobre IA", "admin", "Pendiente");
        Usuario userExpected = new Usuario("User1", "Jason", "user@gmail.com", "profesor");
        repositorio.guardarEvento(evento, userExpected);
        List<Evento> eventos = repositorio.getTodosLosEventos();
        assertEquals("Pendiente", eventos.get(0).getEstado());
    }
}
