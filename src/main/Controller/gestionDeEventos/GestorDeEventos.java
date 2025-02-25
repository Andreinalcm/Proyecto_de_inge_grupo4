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

    public void crearEvento(String titulo, String descripcion, String fechaHoraInicioStr, String fechaHoraFinStr, String ubicacion, Usuario creador, String estado) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime fechaHoraInicio = LocalDateTime.parse(fechaHoraInicioStr, formatter);
            LocalDateTime fechaHoraFin = LocalDateTime.parse(fechaHoraFinStr, formatter);
    
            if (fechaHoraFin.isBefore(fechaHoraInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha de fin debe ser posterior a la fecha de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            Evento evento = new Evento(titulo, fechaHoraInicio, fechaHoraFin, ubicacion, descripcion, creador.getNombre(), estado);
            repositorio.guardarEvento(evento, creador); // Guardar en el repositorio
    
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha y hora incorrecto (YYYY-MM-DD HH:mm).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el evento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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