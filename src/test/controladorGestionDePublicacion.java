package test;
import main.Controller.*;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionPublicacion.Publicacion;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.*;
public class controladorGestionDePublicacion {
    @Test
     public void prueba(){
        Publicacion publi = new Publicacion("Adios", "" , "Ayuda", "c Murióóóoooooooo", "WIna");
        GestorPublicaciones gestor = GestorPublicaciones.getInstancia(publi);
       gestor.crearPublicacion("Esperada", "2025-02-27 15:17", "prueba", "probando", "El tester");
       gestor.aprobarPublicacion(gestor.getPublicacionesPendientes().get(0));
       gestor.aprobarPublicacion(publi);
       assertEquals(publi.getEstado(), gestor.getPublicacionesAprobados().get(0).getEstado());
       gestor.crearPublicacion("Esperada", "2025-02-27 15:17", "prueba", "probando", "El tester");
       assertEquals(gestor.getTodosLosPublicaciones().get(0).getEstado(), gestor.getPublicacionesAprobados().get(1).getEstado());

    
       
    }
}
