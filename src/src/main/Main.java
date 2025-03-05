package main;

//Paquetes importados
import main.View.gestionInicioSesion.LoginVista;

//librerias
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        //instancia de login gemini
        SwingUtilities.invokeLater(() -> new LoginVista());
        
    }

}
