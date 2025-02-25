package main;

//Paquetes importados
//import main.Entidad.*;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.View.gestionDePublicaciones.VentanaPublicaciones;
import main.View.gestionRegistroLogin.*;
import main.View.calendario.Calendario;
import main.View.gestionDePublicaciones.VentanaCrearPublicacion;
import main.Model.gestionPublicacion.Publicacion;

import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeEventos.RepositorioEventosArchivo;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeUsuario.Dashboard;
import main.View.calendario.*;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


//librerias
import javax.swing.SwingUtilities;

public class Main {

    //Atributos
    //private static GestorPublicaciones gestor;

    public static void main(String[] args) {

        //Crear instancia de LOGIN
        SwingUtilities.invokeLater(() -> new LoginFrame());

        /*

        Usuario usuarioActual = new Usuario("Andreina", "hola@gmail.com", "Profesor");
        RepositorioEventos repositorio2 = new RepositorioEventosArchivo();
        GestorDeEventos controller = GestorDeEventos.getInstancia(repositorio2); // Usar getInstancia

        // Solo mostrar el Dashboard al inicio
        Dashboard dashboard = new Dashboard(controller, usuarioActual);
        dashboard.setVisible(true); 
        */

    }

}
