package main.Controller.gestorRegistroLogin;

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

}


