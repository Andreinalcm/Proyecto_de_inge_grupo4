package main.Controller.gestionDeEventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeUsuario.Usuario;

public class GestorDeEventos {

    private static GestorDeEventos instancia;
    private final RepositorioEventos repositorio;

    private GestorDeEventos(RepositorioEventos repositorio) {
        this.repositorio = repositorio;
    }

    public static GestorDeEventos getInstancia(RepositorioEventos repositorio) {
        if (instancia == null) {
            instancia = new GestorDeEventos(repositorio);
        }
        return instancia;
    }

    public boolean crearEvento(String titulo, String descripcion, String fechaHoraInicioStr, String fechaHoraFinStr, String ubicacion, Usuario creador, String estado) {
        try {
            // Validar campos vacíos
            if (titulo.isEmpty() || descripcion.isEmpty() || fechaHoraInicioStr.isEmpty() || fechaHoraFinStr.isEmpty() || ubicacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            // Validar longitud del título y ubicación (máximo 100 palabras)
            if (contarPalabras(titulo) > 100) {
                JOptionPane.showMessageDialog(null, "El título no puede tener más de 100 palabras.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            if (contarPalabras(ubicacion) > 100) {
                JOptionPane.showMessageDialog(null, "La ubicación no puede tener más de 100 palabras.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            // Validar longitud de la descripción (máximo 300 palabras)
            if (contarPalabras(descripcion) > 300) {
                JOptionPane.showMessageDialog(null, "La descripción no puede tener más de 300 palabras.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            // Convertir fechas a LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaHoraInicio = LocalDateTime.parse(fechaHoraInicioStr, formatter);
            LocalDateTime fechaHoraFin = LocalDateTime.parse(fechaHoraFinStr, formatter);
    
            // Validar fechas
            if (fechaHoraFin.isBefore(fechaHoraInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha de fin debe ser posterior a la fecha de inicio.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            if (fechaHoraFin.isEqual(fechaHoraInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser igual a la fecha de inicio.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Validar que las fechas no sean anteriores a la fecha actual
            LocalDateTime ahora = LocalDateTime.now();
            if (fechaHoraInicio.isBefore(ahora)) {
                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser anterior a la fecha actual.", "Advertencia", JOptionPane.ERROR_MESSAGE);
                return false;
            }
    
            // Si todas las validaciones son exitosas, crear el evento
            Evento evento = new Evento(titulo, fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador.getNombre(), estado);
            repositorio.guardarEvento(evento, creador); // Guardar en el repositorio
            return true; // Evento creado exitosamente
    
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha y hora incorrecto (YYYY-MM-DD HH:mm).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el evento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    // Método para contar palabras en un texto
    private int contarPalabras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return 0;
        }
        String[] palabras = texto.split("\\s+"); // Dividir por espacios
        return palabras.length;
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