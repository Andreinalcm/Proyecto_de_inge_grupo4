package main.Model.gestionDeEventos;

import java.util.ArrayList;
import java.util.List;
import main.Model.gestionDeUsuario.Usuario;

public class RepositorioEventosMemoria implements RepositorioEventos {
    private final List<Evento> eventos = new ArrayList<>();

    @Override
    public void guardarEvento(Evento evento, Usuario creador) {
        eventos.add(evento);
        creador.agregarEvento(evento);
    }

    @Override
    public List<Evento> getTodosLosEventos() {
        return eventos;
    }

    @Override
    public void guardarEnArchivo() {
        // No es necesario hacer nada, ya que los eventos están en memoria
    }
}