package test;

import static org.junit.Assert.assertNotEquals;

import org.junit.*;
import main.Model.gestionDeUsuario.*;
public class UsuarioTest {
    @Test
    public void pruebaUsuario(){
        Usuario userExpected = new Usuario("User1", "user@gmail.com", "profesor");
        String nombrePrueba = "Patata";
        String email = "user@gmail.com";
        String rol = "estudiante";

        Usuario userTest = new Usuario(nombrePrueba, email, rol);

        assertNotEquals(userExpected.getRol(), userTest.getRol());


        
    }
}
