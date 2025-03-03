package main;

//Paquetes importados
import main.View.gestionRegistroLogin.LoginFrame;

//librerias
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        //Crear instancia de LOGIN
        SwingUtilities.invokeLater(() -> new LoginFrame());
        
    }

}
