package main.Controller.gestionInicioSesion;

import main.View.gestionInicioSesion.LoginVista;
import main.View.gestionDeUsuario.Dashboard;
import main.Model.gestionDeUsuario.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginControlador implements ActionListener {

    private LoginVista vista;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String usuario = vista.getUsuario();
        String clave = vista.getClave();

        try (BufferedReader reader = new BufferedReader(new FileReader("main/Data/usuarios.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(usuario) && partes[1].equals(clave)) {
                    // Crea una instancia de Usuario con los datos del archivo
                    Usuario usuarioActual = new Usuario(partes[0], partes[2], partes[3], partes[4]);

                    //Crea instancia de controlador de eventos y una ventana
                    


                    // Abre el Dashboard y cierra la ventana de login
                    Dashboard dashboard = new Dashboard(usuarioActual);
                    dashboard.setVisible(true);
                    vista.dispose(); // Cierra la ventana de login

                    return;
                }
            }
            vista.mostrarMensaje("Usuario o clave incorrectos.");
        } catch (IOException ex) {
            vista.mostrarMensaje("Error al leer el archivo de usuarios.");
        }
    }
}