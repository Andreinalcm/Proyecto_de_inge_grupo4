package main.Controller.gestionRegistro;

import main.View.gestionDeRegistro.RegistroVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegistroControlador implements ActionListener {

    private RegistroVista vista;

    public RegistroControlador(RegistroVista vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = vista.getNombre();
        String correo = vista.getEmail();
        String usuario = vista.getUsuario();
        String clave = vista.getClave();
        String rol = vista.getRol();

        if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() || clave.isEmpty() || rol.isEmpty()) {
            vista.mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("main/Data/usuarios.txt", true))) {
            writer.write(usuario + "," + clave + "," + nombre + "," + correo + "," + rol);
            writer.newLine();
        } catch (IOException ex) {
            vista.mostrarMensaje("Error al guardar el usuario.");
            return;
        }

        File carpetaUsuario = new File("main/Data/" + usuario);
        if (!carpetaUsuario.exists()) {
            carpetaUsuario.mkdirs();
        }

        vista.mostrarMensaje("Usuario registrado con éxito.");
        vista.cerrarVentana(); // Cerrar la ventana después del mensaje
    }
}