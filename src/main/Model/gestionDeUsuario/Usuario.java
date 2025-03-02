package main.Model.gestionDeUsuario;

import java.util.ArrayList;
import java.util.List;
import main.Model.gestionDeEventos.Evento;

public class Usuario {
    private final String nombre;
    private final String email;
    private final String rol;
    private final List<Evento> eventos;

    public Usuario(String nombre, String email, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.eventos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void agregarEvento(Evento evento) {
        eventos.add(evento);
    }
}