package main.Model.gestionDeEventos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.Model.gestionDeUsuario.Usuario;

public class RepositorioEventosArchivo implements RepositorioEventos {
    private static final String ARCHIVO_EVENTOS = "src/main/Data/Evento.txt";
    private List<Evento> eventos = new ArrayList<>();

    @Override
    public void guardarEvento(Evento evento, Usuario creador) {
        eventos.add(evento); // Guardar en la lista global de eventos
        creador.agregarEvento(evento); // Guardar en la lista de eventos del usuario
        guardarEnArchivo(evento, creador); // Guardar en el archivo
    }

    @Override
    public List<Evento> getTodosLosEventos() {
        return eventos;
    }

    @Override
    public void actualizarEvento(Evento evento) {
        // No es necesario actualizar en memoria, ya que los objetos se modifican directamente
    }

    private void guardarEnArchivo(Evento evento, Usuario creador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_EVENTOS, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String fechaInicioStr = evento.getFechaHoraInicioOriginal().format(formatter);
            String fechaFinStr = evento.getFechaHoraFinOriginal().format(formatter);
    
            String eventoStr = String.format(
                    "%s,%s,%s,%s,%s,%s,%s\n",
                    evento.getTitulo(),
                    fechaInicioStr,
                    fechaFinStr,
                    evento.getUbicacion(),
                    evento.getDescripcion(),
                    creador.getNombre(),
                    evento.getEstado()
            );
            writer.write(eventoStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}