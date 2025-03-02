package main.Controller.gestorRegistroLogin;

import javax.swing.*;
import javax.swing.border.*;

import main.Model.gestionDeUsuario.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LoginRegisterApp {

    // Ruta del archivo donde se guardan los usuarios
    private static final String RUTA_ARCHIVO_USUARIOS = "main/Data/usuarios.txt";

    // Método para guardar un usuario
    public static void guardarUsuario(String usuario, String nombre, String correo, String clave) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_USUARIOS, true))) {
            writer.write(usuario + "," + nombre + "," + correo + "," + clave);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    // Método para leer los usuarios
    public static Map<String, String[]> leerUsuarios() {
        Map<String, String[]> usuarios = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO_USUARIOS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 4) {
                    usuarios.put(datos[0], new String[]{datos[1], datos[2], datos[3]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
 
    // Método para buscar un usuario por nombre de usuario y contraseña
    public static Usuario buscarUsuario(String usuario, String clave) {
        Map<String, String[]> usuarios = leerUsuarios();
        if (usuarios.containsKey(usuario)) {
            String[] datos = usuarios.get(usuario);
            String claveRegistrada = datos[2];
            if (claveRegistrada.equals(clave)) {
                return new Usuario(datos[0], datos[1], "Profesor"); // Cambia "Profesor" por el rol correcto si es necesario
            }
        }
        return null; // Si no se encuentra el usuario o la clave es incorrecta
    }

}


