package test;
import main.Model.gestionPublicacion.Publicacion;
import main.View.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;
public class PublicacionTest {
@Test
    public void test(){
        Publicacion pu = new Publicacion("Adios", "" , "Ayuda", "c Murióóóoooooooo", "WIna");
        Publicacion publicacionEsperada = new Publicacion("EDO", "2025-03-04 14:18", "Matemática", "EDOs, ayuda", "Yo") ;
    

        assertEquals(publicacionEsperada.getfechaDePublicacion(), pu.getfechaDePublicacion());
    

    }

  
}
