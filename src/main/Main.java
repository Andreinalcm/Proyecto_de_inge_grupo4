package main;

//Paquetes importados
//import main.Entidad.*;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.View.gestionDePublicaciones.VentanaPublicaciones;
import main.View.gestionRegistroLogin.*;
import main.View.gestionDePublicaciones.VentanaCrearPublicacion;
import main.Model.gestionPublicacion.Publicacion;

//librerias
import javax.swing.SwingUtilities;

public class Main {

    //Atributos
    private static GestorPublicaciones gestor;

    public static void main(String[] args) {

        Publicacion repositorio = new Publicacion();
        gestor = GestorPublicaciones.getInstancia(repositorio);

        //Crear instancia de la vista de publicaciones
        VentanaPublicaciones ventana = new VentanaPublicaciones(gestor);
        
        //Crear instancia de la vista de crear publicacion
        VentanaCrearPublicacion ventana2 = new VentanaCrearPublicacion(gestor, ventana);
        ventana2.setVisible(true);

        //Crear instancia de LOGIN
        SwingUtilities.invokeLater(() -> new LoginFrame());
        //login.setVisible(true);
        

    }
}
