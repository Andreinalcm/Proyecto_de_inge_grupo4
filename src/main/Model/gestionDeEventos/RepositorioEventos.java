package main.Model.gestionDeEventos;

import java.util.List;
import main.Model.gestionDeUsuario.Usuario;

public interface RepositorioEventos {
    void guardarEvento(Evento evento, Usuario usuario);
    List<Evento> getTodosLosEventos();
    void guardarEnArchivo();
}