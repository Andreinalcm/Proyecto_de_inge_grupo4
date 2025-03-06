package test;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import main.Model.gestionDeUsuario.*;
import main.Model.gestionDeEventos.*;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Controller.gestionDeEventos.*;
public class UsuarioTest {
    @Test
    public void pruebaUsuario(){
        Usuario userExpected = new Usuario("User1", "Jason","user@gmail.com", "profesor");
  
        RepositorioEventos repo = new RepositorioEventosArchivo();
        GestorDeEventos gestor = GestorDeEventos.getInstancia(repo);
        gestor.crearEvento("prueba", "Esto es una prueba", "2026-02-02 02:02", "2026-03-02 02:02", "Ubicacion prueba", userExpected, "Pendiente");
        
        userExpected.agregarEvento(gestor.getTodosLosEventos().get(0));

        assertEquals(userExpected.getEventos().get(0).getTitulo(), gestor.getTodosLosEventos().get(0).getTitulo());

    }
}
