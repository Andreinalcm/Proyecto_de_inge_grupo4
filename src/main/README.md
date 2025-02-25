# Modelo de implementación

## Lista de librerias usadas

### import java.awt.Component;
### import java.time.LocalDateTime;
### import java.time.format.DateTimeFormatter;
### import java.time.format.DateTimeParseException;
### import java.util.List;
### import java.util.ArrayList;
### import javax.swing.JOptionPane;
### import java.util.regex.Matcher;
### import java.util.regex.Pattern;
### import javax.swing.JOptionPane;
### import java.io.BufferedWriter;
### import java.io.FileWriter;
### import java.io.IOException;


## Instrucciones para correr la aplicación

1. Ejecutar el archivo Main.java.
2. Al Iniciar sesión con alguno de los usuarios guardados en el archivo Usuario.txt.
3. Si se desea registrar un usuario nuevo, presionar el botón de Registrar de la vista Inicio de sesión.
4. Al iniciar sesión, se muestra el Dashbard con las opciones que desea realizar.
5. Si seleciona Publicar evento, se muestra un fomulario con los datos que debe llenar sobre el evento.
6. Si se selecciona Mis eventos, se muestra una lista con los eventos que ha publicado ese usuario.
7. Si se selecciona Revisar calendario, se muestra el calendario.
8. Si se selecciona Hacer una Publicación, se muestra un fomulario con los datos que debe llenar sobre la publicación.
9. Si se selecciona Cerrar sesión, se muestra la vista del Inicio de sesión donde se debe llenar los datos de un usuario existente en el archivo Usuario.txt o deberá registrase un nuevo usuario