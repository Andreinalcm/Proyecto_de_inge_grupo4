package test;
import main.Controller.*;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionPublicacion.Publicacion;

import static org.junit.Assert.assertArrayEquals;

import org.junit.*;
public class controladorGestionDePublicacion {
    @Test
     public void prueba(){
        Publicacion publi = new Publicacion("Adios", "" , "Ayuda", "c Murióóóoooooooo", "WIna");
        GestorPublicaciones gestor = GestorPublicaciones.getInstancia(publi);
       
    
       
    }
}
