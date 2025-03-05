package main.Model.gestionDeEventos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.Model.gestionDeUsuario.Usuario;

public class RepositorioEventosArchivo implements RepositorioEventos {
    private static final String ARCHIVO_EVENTOS = "main/Data/Evento.txt";
    private List<Evento> eventos;

    public RepositorioEventosArchivo() {
        eventos = new ArrayList<>();
        cargarEventosDesdeArchivo(); // Cargar eventos al inicializar el repositorio
    }

    @Override
    public void guardarEvento(Evento evento, Usuario creador) {
        eventos.add(evento); // Guardar en la lista global de eventos
        creador.agregarEvento(evento); // Guardar en la lista de eventos del usuario
        guardarEnArchivo(); // Guardar todos los eventos en el archivo
    }

    @Override
    public void eliminarEvento(Evento evento) {
        eventos.remove(evento); // Eliminar el evento de la lista
        guardarEnArchivo(); // Guardar los cambios en el archivo
    }

    @Override
    public List<Evento> getTodosLosEventos() {
        return eventos;
    }

    private void cargarEventosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_EVENTOS))) {
            String linea;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 7) { // Asegúrate de que hay 7 campos
                    String titulo = partes[0].trim();
                    String fechaInicioStr = partes[1].trim();
                    String fechaFinStr = partes[2].trim();
                    String ubicacion = partes[3].trim();
                    String descripcion = partes[4].trim();
                    String creador = partes[5].trim();
                    String estado = partes[6].trim();

                    // Convertir las fechas de String a LocalDateTime
                    LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr, formatter);
                    LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);

                    // Crear el evento y agregarlo a la lista
                    eventos.add(new Evento(titulo, fechaInicio, fechaFin, ubicacion, descripcion, creador, estado));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_EVENTOS))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Evento evento : eventos) {
                String fechaInicioStr = evento.getFechaHoraInicioOriginal().format(formatter);
                String fechaFinStr = evento.getFechaHoraFinOriginal().format(formatter);

                String eventoStr = String.format(
                        "%s,%s,%s,%s,%s,%s,%s\n",
                        evento.getTitulo(),
                        fechaInicioStr,
                        fechaFinStr,
                        evento.getUbicacion(),
                        evento.getDescripcion(),
                        evento.getCreador(),
                        evento.getEstado());
                writer.write(eventoStr);
            }
            System.out.println("Archivo actualizado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}