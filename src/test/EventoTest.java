package test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import main.Model.gestionDeEventos.Evento;
import org.junit.Test;
import static org.junit.Assert.*;

// Particiones de equivalencia: Entradas validas y no validas

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

public class EventoTest {

    // Datos de prueba
    private final String titulo = "Conferencia de Java";
    private final LocalDateTime fechaHoraInicio = LocalDateTime.of(2025, 10, 15, 10, 0);
    private final LocalDateTime fechaHoraFin = LocalDateTime.of(2025, 10, 15, 12, 0);
    private final String descripcion = "Una conferencia sobre Java y JUnit.";
    private final String ubicacion = "Auditorio Tobias Lassert";
    private final String creador = "Juan Pérez";
    private final String estado = "Pendiente";

    // Instanciar de Evento
    private final Evento evento = new Evento(titulo, fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador, estado);

    // Prueba para verificar el título del evento
    @Test
    public void testGetTitulo() {
        //déjame hacer un test 
        assertEquals(titulo, evento.getTitulo(), titulo);
        //yo recomiendo usar assertEquals(evento.getTitulo(), titulo)
        //Esas de dos suelen ser mejores, porque el string del inicio es solo como comentario
        assertEquals(titulo, evento.getTitulo(), "El título del evento no coincide.");
        
    }

    // Prueba para verificar la descripción del evento
    @Test
    public void testGetDescripcion() {
        assertEquals(descripcion, evento.getDescripcion(), "La descripción del evento no coincide.");
    }

    // Prueba para verificar la fecha y hora de inicio formateada
    @Test
    public void testGetFechaHoraInicio() {
        String fechaHoraInicioFormateada = "2025-10-15 10:00";
        assertEquals(fechaHoraInicioFormateada, evento.getFechaHoraInicio(), "La fecha y hora de inicio no coinciden.");
    }

    // Prueba para verificar la fecha y hora de fin formateada
    @Test
    public void testGetFechaHoraFin() {
        String fechaHoraFinFormateada = "2025-10-15 12:00";
        assertEquals(fechaHoraFinFormateada, evento.getFechaHoraFin(), "La fecha y hora de fin no coinciden.");
    }

    // Prueba para verificar la ubicación del evento
    @Test
    public void testGetUbicacion() {
        assertEquals(ubicacion, evento.getUbicacion(), "La ubicación del evento no coincide.");
    }

    // Prueba para verificar el creador del evento
    @Test
    public void testGetCreador() {
        assertEquals(creador, evento.getCreador(), "El creador del evento no coincide.");
    }

    // Prueba para verificar el estado del evento
    @Test
    public void testGetEstado() {
        assertEquals(estado, evento.getEstado(), "El estado del evento no coincide.");
    }


   /*  
    // Prueba para verificar la fecha y hora de inicio original (sin formatear)
    @Test
    public void testGetFechaHoraInicioOriginal() {
        assertEquals("La fecha y hora de inicio original no coinciden.", evento.getFechaHoraInicioOriginal(), fechaHoraInicio);
    }

    // Prueba para verificar la fecha y hora de fin original (sin formatear)
    @Test
    public void testGetFechaHoraFinOriginal() {
        assertEquals("La fecha y hora de fin original no coinciden.", evento.getFechaHoraFinOriginal(), fechaHoraFin);
    }

    */
    }