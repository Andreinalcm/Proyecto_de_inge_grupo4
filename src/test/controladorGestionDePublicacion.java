package test;
import main.Controller.*;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionPublicacion.Publicacion;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.*;
public class controladorGestionDePublicacion {
    @Test
     public void prueba(){
        //Publicacion a evaluar
        Publicacion publi = new Publicacion("Adios", "" , "Ayuda", "c Murióóóoooooooo", "aaa");
        GestorPublicaciones gestor = GestorPublicaciones.getInstancia(publi);
        //publicacion esperada 
       gestor.crearPublicacion("Esperada", "2025-02-27 15:17", "prueba", "probando", "El tester");
       // Aprobar las publicaciones, para evaluarlas
       gestor.aprobarPublicacion(gestor.getPublicacionesPendientes().get(0));
       gestor.aprobarPublicacion(publi);
       //Verificar si la publicación publi está aprobada
       assertEquals(publi.getEstado(), gestor.getPublicacionesAprobados().get(0).getEstado());
       //Rechazar la publicación y crear una nueva para evaluarlas
       gestor.rechazarPublicacion(gestor.getTodosLosPublicaciones().get(1));
        
       gestor.crearPublicacion("Esperada", "2025-02-27 15:17", "prueba", "probando", "El tester");
       gestor.rechazarPublicacion(gestor.getTodosLosPublicaciones().get(2));
       //Verificar si las publicaciones están rechazadas, ambas lo están, por lo que debería pasar la prueba.
       assertEquals(gestor.getTodosLosPublicaciones().get(1).getEstado(), gestor.getTodosLosPublicaciones().get(2).getEstado());

       /*Se crea una nueva publicación y se pregunta si, está aprobada.
        Como no se aprueba previamente, debería dar error*/

        gestor.crearPublicacion("Esperada", "2025-02-27 15:17", "prueba", "probando", "El tester");
        assertEquals(gestor.getPublicacionesAprobados().get(1).getEstado(), gestor.getTodosLosPublicaciones().get(0).getEstado());
    
    }
}
