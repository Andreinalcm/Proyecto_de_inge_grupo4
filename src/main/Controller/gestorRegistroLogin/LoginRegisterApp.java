package main.Controller.gestorRegistroLogin;

// LoginRegisterApp.java
import javax.swing.*;
import javax.swing.border.*;

import main.Model.gestionDeUsuario.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// =================== Clase Principal ===================
public class LoginRegisterApp {
    public static final String CARPETA_DATOS = "Data";
    public static final String ARCHIVO_USUARIOS = CARPETA_DATOS + File.separator + "usuarios.txt";

    // Método para asegurarse de que la carpeta existe
    public static void verificarCarpetaDatos() {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // Crea la carpeta si no existe
        }
    }

    // Método para leer usuarios desde el archivo y retornarlos en un Map
    // Clave: usuario, Valor: String[]{nombre, correo, clave}
    public static Map<String, String[]> leerUsuarios() {
        verificarCarpetaDatos(); // Asegurar que la carpeta exista
        Map<String, String[]> usuarios = new HashMap<>();
        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al crear archivo de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    usuarios.put(partes[0], new String[]{partes[1], partes[2], partes[3]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return usuarios;
    }

    // Método para buscar un usuario por usuario y contraseña
    public static Usuario buscarUsuario(String usuario, String clave) {
        Map<String, String[]> usuarios = leerUsuarios();
        if (usuarios.containsKey(usuario)) {
            String[] datos = usuarios.get(usuario);
            String claveRegistrada = datos[2];
            if (claveRegistrada.equals(clave)) {
                return new Usuario(usuario, datos[0], datos[1], datos[3]); // Corregido
            }
        }
        return null; // Si no se encuentra el usuario o la clave es incorrecta
    }

    // Método para guardar un usuario en el archivo
    public static void guardarUsuario(String usuario, String nombre, String correo, String clave, String rol) {
        verificarCarpetaDatos(); // Asegurar que la carpeta exista

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            // Escribe en el formato: usuario,nombre,correo,clave
            bw.write(usuario + "," + nombre + "," + correo + "," + clave+ "," + rol);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}