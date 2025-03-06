package test;
import java.time.LocalDateTime;

import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeEventos.RepositorioEventosArchivo;
import main.Model.gestionDeUsuario.Usuario;

import org.junit.Test;
import static org.junit.Assert.*;

// Particiones de equivalencia: Entradas validas y no validas

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

public class EventoTest {

    // Datos de prueba
    private final String titulo = "La vida es un viaje lleno de sorpresas, algunas maravillosas y otras desafiantes. A menudo, nos encontramos navegando por aguas turbulentas, enfrentando obstáculos que parecen insuperables. Sin embargo, es en estos momentos de dificultad donde realmente descubrimos nuestra fortaleza interior. La resiliencia, esa capacidad de levantarnos después de cada caída, es lo que nos impulsa a seguir adelante. En el camino, aprendemos valiosas lecciones sobre nosotros mismos y sobre el mundo que nos rodea. Descubrimos que la felicidad no reside en la ausencia de problemas, sino en la capacidad de superarlos. Aprendemos a valorar los pequeños momentos de alegría, a apreciar la belleza que nos rodea y a cultivar relaciones significativas con quienes nos importan. La vida es un regalo precioso, y cada día es una oportunidad para crecer, aprender y amar. No importa cuán oscuro parezca el camino, siempre hay una luz al final del túnel. Así que, sigamos adelante con valentía y esperanza, sabiendo que somos capaces de alcanzar nuestros sueños y construir un futuro lleno de posibilidades.";
    private final LocalDateTime fechaHoraInicio = LocalDateTime.of(2025, 10, 15, 10, 0);
    private final LocalDateTime fechaHoraFin = LocalDateTime.of(2025, 10, 15, 12, 0);
    private final String descripcion = "Una conferencia sobre Java y JUnit.";
    private final String ubicacion = "Auditorio Tobias Lassert";
    private final String creador = "Juan Pérez";
    private final String estado = "Pendiente";
    RepositorioEventos repo = new RepositorioEventosArchivo();
    GestorDeEventos gestor = GestorDeEventos.getInstancia(repo);
    // Instanciar de Evento
    private final Evento evento = new Evento(titulo, fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador, estado);

    // Prueba para verificar el título del evento
    @Test
    public void testGetTitulo() {
        Evento event = new Evento("titulo", fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador, estado);
        Usuario u = new Usuario("aaa", "Anthony", descripcion, creador);
        gestor.crearEvento(titulo, descripcion, "2025-10-15 12:00", "2025-10-16 12:00", ubicacion, u, estado);
        assertEquals(event.getTitulo(), gestor.getTodosLosEventos().get(0).getTitulo());
    }

    // Prueba para verificar la descripción del evento
    @Test
    public void testGetDescripcion() {
        assertEquals(descripcion, evento.getDescripcion(), "La descripción del evento no coincide.");
    }

   
    @Test
    public void testGetFechaHoraInicio() {
        String fechaHoraInicioFormateada = "2025-10-15 10:00";
        assertEquals(fechaHoraInicioFormateada, evento.getFechaHoraInicio(), "La fecha y hora de inicio no coinciden.");
    }


    @Test
    public void testGetFechaHoraFin() {
        String fechaHoraFinFormateada = "2025-10-15 12:00";
        assertEquals(fechaHoraFinFormateada, evento.getFechaHoraFin(), "La fecha y hora de fin no coinciden.");
    }

    @Test
    public void testGetUbicacion() {
        assertEquals(ubicacion, evento.getUbicacion(), "La ubicación del evento no coincide.");
    }


    @Test
    public void testGetCreador() {
        assertEquals(creador, evento.getCreador(), "El creador del evento no coincide.");
    }


    @Test
    public void testGetEstado() {
        assertEquals(estado, evento.getEstado(), "El estado del evento no coincide.");
    }

    @Test
    public void testGetFechaHoraInicioOriginal() {
        assertEquals("La fecha y hora de inicio original no coinciden.", evento.getFechaHoraInicioOriginal(), fechaHoraInicio);
    }

    // Prueba para verificar la fecha y hora de fin original (sin formatear)
    @Test
    public void testGetFechaHoraFinOriginal() {
        assertEquals("La fecha y hora de fin original no coinciden.", evento.getFechaHoraFinOriginal(), fechaHoraFin);
    }
@Test
    public void testFechaCorrecta(){
        Evento event = new Evento(titulo, fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador, estado);
        Usuario u = new Usuario("titulo", "", descripcion, creador);
        gestor.crearEvento("", descripcion, "2025-10-15 12:00", "2025-10-16 12:00", ubicacion, u, estado);
        assertEquals(event.getCreador(), gestor.getTodosLosEventos().get(0).getCreador());
    }
     
    
    }