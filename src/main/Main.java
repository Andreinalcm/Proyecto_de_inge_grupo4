package main;

//Paquetes importados
//import main.View.gestionRegistroLogin.LoginFrame;
import main.View.gestionInicioSesion.LoginVista;


//librerias
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        //Crear instancia de LOGIN OLD
        //SwingUtilities.invokeLater(() -> new LoginFrame());

        //instancia de login gemini
        SwingUtilities.invokeLater(() -> new LoginVista());
        
    }

}
