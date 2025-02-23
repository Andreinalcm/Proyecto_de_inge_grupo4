package main;

//Paquetes importados
//import main.Entidad.*;
import main.Controller.GestorPublicaciones.GestorPublicaciones;
import main.View.GestionDePublicaciones.VentanaPublicaciones;
import main.View.gestionRegistroLogin.*;
import main.View.GestionDePublicaciones.VentanaCrearPublicacion;
import main.model.Publicacion;

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

        //SwingUtilities.invokeLater(() -> new FormularioDeInicioDeSesion());
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        

    }
}
