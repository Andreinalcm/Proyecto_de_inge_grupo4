package main.View.gestionDePublicaciones;

import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VentanaCrearPublicacion extends JFrame {

    JPanel panelPrincipal, panelCampos, panelDeBotones, panelTitulo;
    JButton BotonPublicar, BotonCancelar, BotonMisPublicaciones;
    private GestorPublicaciones gestorPublicaciones;
    private Usuario usuario;

    //Constructor
    public VentanaCrearPublicacion(GestorPublicaciones gestorPublicaciones, Usuario usuario) {

        //asignar variables por parametro
        this.gestorPublicaciones = gestorPublicaciones;
        this.usuario = usuario;
        //this.ventana = ventana;

        //Configurar ventana principal
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Panel contenedor
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.BLACK);

        //Panel de titulo
        panelTitulo = new JPanel();
        panelTitulo.setBackground(Color.BLACK);

        // Etiqueta de título
        JLabel mainLabel = new JLabel("Crear Publicación", SwingConstants.CENTER);
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 25));
        mainLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelTitulo.add(mainLabel);

        // Panel con campos de texto
        panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 1.0; // Peso vertical para la primera fila

        JLabel tituloLabel = new JLabel("<html><center><span style='font-size: 20px;'>Titulo</html>");
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelCampos.add(tituloLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField tituloField = new JTextField();
        tituloField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelCampos.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0; // Peso vertical para la segunda fila
        JLabel fechaLabel = new JLabel("<html><center><span style='font-size: 20px;'>Fecha</span></center><br>AAAA-MM-DD HH:MM</html>");
        fechaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fechaLabel.setForeground(Color.WHITE);
        fechaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelCampos.add(fechaLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField fechaDePublicacionField = new JTextField();
        fechaDePublicacionField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelCampos.add(fechaDePublicacionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0; // Peso vertical para la tercera fila
        JLabel categoriaLabel = new JLabel("<html><center><span style='font-size: 20px;'>Categoria</html>");
        //categoriaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        categoriaLabel.setForeground(Color.WHITE);
        categoriaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelCampos.add(categoriaLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField categoriaField = new JTextField();
        categoriaField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelCampos.add(categoriaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0; // Peso vertical para la cuarta fila

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0; // Peso vertical para la quinta fila
        JLabel descripcionLabel = new JLabel("<html><center><span style='font-size: 20px;'>Descripción</html>");
        descripcionLabel.setForeground(Color.WHITE);
        descripcionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelCampos.add(descripcionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        TextArea descripcionField = new TextArea();
        descripcionField.setBackground(Color.LIGHT_GRAY);
        descripcionField.setForeground(Color.BLACK);
        panelCampos.add(descripcionField, gbc);

        //Panel con los botones
        panelDeBotones = new JPanel();
        panelDeBotones.setLayout(new BoxLayout(panelDeBotones, BoxLayout.Y_AXIS));
        panelDeBotones.setBackground(Color.BLACK);

        BotonPublicar = new JButton("Hacer Publicacion");
        BotonCancelar = new JButton("Anular Publicacion");
        BotonMisPublicaciones = new JButton("Mis Publicaciones");

        // Aplicar el estilo 3D a los botones
        ButtonUtils.styleButton3D(BotonPublicar);
        ButtonUtils.styleButton3D(BotonCancelar);
        ButtonUtils.styleButton3D(BotonMisPublicaciones);

        BotonPublicar.setBorder(new EmptyBorder(10, 20, 10, 20));
        BotonCancelar.setBorder(new EmptyBorder(10, 20, 10, 20));
        BotonMisPublicaciones.setBorder(new EmptyBorder(10, 20, 10, 20));

        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(BotonPublicar);
        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(BotonCancelar);
        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(BotonMisPublicaciones);
        panelDeBotones.add(Box.createRigidArea(new Dimension(0, 200)));
        
        //Eventos de los botones
        // ActionListener para el botón "Mis Publicaciones"
        BotonMisPublicaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new MisPublicaciones(gestorPublicaciones, usuario));
            }
        });

        // ActionListener para el botón "Anular"
        BotonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Limpiar los campos
                tituloField.setText("");
                fechaDePublicacionField.setText("");
                categoriaField.setText("");
                descripcionField.setText("");

                dispose();
            }
        });

        // ActionListener para el botón "Publicar"
        BotonPublicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener información
                String titulo = tituloField.getText().trim();
                String fechaDePublicacion = fechaDePublicacionField.getText().trim();
                String categoria = categoriaField.getText().trim();
                String creador = usuario.getUsuario();
                String descripcion = descripcionField.getText().trim();
                String estado = "Pendiente";

                if (!titulo.isEmpty() && !fechaDePublicacion.isEmpty() && !categoria.isEmpty() && !creador.isEmpty() && !descripcion.isEmpty()) {
                    // Validar la fecha y hora
                    if (validarFecha(fechaDePublicacion)) {
                        gestorPublicaciones.crearPublicacion(titulo, fechaDePublicacion, categoria, creador, descripcion);

                        Publicacion publicacionActualizada = new Publicacion(titulo, fechaDePublicacion, categoria, descripcion, creador, estado);
                        // Guardar en archivo
                        guardarOActualizarArchivo(publicacionActualizada, true);
                        // Cerrar la ventana actual
                        dispose();

                        // Limpiar los campos
                        tituloField.setText("");
                        fechaDePublicacionField.setText("");
                        categoriaField.setText("");
                        descripcionField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Fecha/hora inválida. Asegúrese de que el formato sea AAAA-MM-DD HH:MM y que la fecha y hora sean válidas (año >= 2025, hora 00-23, minutos 00-59).", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Agregar paneles al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCampos, BorderLayout.WEST);
        panelPrincipal.add(panelDeBotones, BorderLayout.EAST);
        
        //Añadir el panel principal al Jframe
        add(panelPrincipal);

        //Estado de la visibilidad
        setVisible(true);

    }

    // Método para validar la fecha y hora
    private boolean validarFecha(String fecha) {
        try {
            String[] partes = fecha.split(" ");
            if (partes.length != 2) {
                return false;
            }

            // Validar la fecha
            String[] fechaPartes = partes[0].split("-");
            if (fechaPartes.length != 3) {
                return false;
            }
            int anio = Integer.parseInt(fechaPartes[0]);
            int mes = Integer.parseInt(fechaPartes[1]);
            int dia = Integer.parseInt(fechaPartes[2]);

            if (anio < 2025 || mes < 1 || mes > 12 || dia < 1) {
                return false;
            }

            int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)) {
                diasPorMes[2] = 29; // Año bisiesto
            }

            if (dia > diasPorMes[mes]) {
                return false;
            }

            // Validar la hora
            String[] horaPartes = partes[1].split(":");
            if (horaPartes.length != 2) {
                return false;
            }
            int hora = Integer.parseInt(horaPartes[0]);
            int minutos = Integer.parseInt(horaPartes[1]);

            return hora >= 0 && hora <= 23 && minutos >= 0 && minutos <= 59;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    //Guardar en txt
    private void guardarOActualizarArchivo(Publicacion publicacion, boolean agregar) {
        String nombreUsuario = usuario.getUsuario();
        String nombreArchivo = publicacion.getTitulo() + ".txt";
    
        // Ruta a la carpeta del usuario
        String rutaCarpetaUsuario = "./main/Data/Publicaciones/" + nombreUsuario;
        String rutaArchivoUsuario = rutaCarpetaUsuario + "/" + nombreArchivo;
    
        // Ruta a la carpeta principal "Publicaciones"
        String rutaArchivoPublicaciones = "./main/Data/Publicaciones/" + nombreArchivo;
    
        // Crear la carpeta del usuario si no existe
        File carpetaUsuario = new File(rutaCarpetaUsuario);
        if (!carpetaUsuario.exists()) {
            carpetaUsuario.mkdirs();
            System.out.println("Carpeta de usuario creada: " + rutaCarpetaUsuario);
        }
    
        // Guardar el archivo en la carpeta del usuario
        guardarArchivo(publicacion, rutaArchivoUsuario, agregar);
    
        // Guardar el archivo en la carpeta principal "Publicaciones"
        guardarArchivo(publicacion, rutaArchivoPublicaciones, agregar);
    }
    
    private void guardarArchivo(Publicacion publicacion, String rutaArchivo, boolean agregar) {
        try (FileWriter writer = new FileWriter(rutaArchivo, agregar)) {
            writer.write(publicacion.getTitulo() + "\n" +
                         publicacion.getfechaDePublicacion() + "\n" +
                         publicacion.getCategoria() + "\n" +
                         publicacion.getCreador() + "\n" +
                         publicacion.getDescripcion() + "\n" +
                         publicacion.getEstado());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar/actualizar el archivo en: " + rutaArchivo, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    


    
