package main.Model.gestionDeUsuario;

import java.util.ArrayList;
import java.util.List;
import main.Model.gestionDeEventos.Evento;

public class Usuario {
    private final String usuario;
    private final String nombre;
    private final String correo;
    private final String rol;
    private final List<Evento> eventos;

    public Usuario(String usuario, String nombre, String email, String rol) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.correo = email;
        this.rol = rol;
        this.eventos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return correo;
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