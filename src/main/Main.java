package main;

//Paquetes importados
import main.View.gestionRegistroLogin.*;
import main.View.gestionDePublicaciones.MostrarPublicacion;

//librerias
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        //Crear instancia de LOGIN
        SwingUtilities.invokeLater(() -> new LoginFrame());

        //Crear instancia de VentanaPublicaciones
        //SwingUtilities.invokeLater(() -> new MostrarPublicacion());

    }

}
