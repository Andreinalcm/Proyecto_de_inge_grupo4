package main.View.gestionDePublicaciones;

import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;
import main.View.gestionDePublicaciones.EditarPublicacion;
import main.View.gestionDePublicaciones.MisPublicaciones;
import main.View.gestionDeUsuario.Dashboard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class EditarPublicacion extends JFrame {

    JPanel panelPrincipal, panelCampos, panelDeBotones, panelTitulo;
    JButton BotonPublicar, BotonCancelar, BotonMisPublicaciones, dashboardBtn;
    private Publicacion publicacion;
    JTextField tituloField, fechaDePublicacionField, categoriaField, creadorField;
    TextArea descripcionField;
    private Usuario usuario;
    private GestorPublicaciones gestor;

    //Constructor
    public EditarPublicacion(Publicacion publicacion, GestorPublicaciones gestor, Usuario usuario) {

        this.publicacion = publicacion;
        this.gestor = gestor;
        this.usuario = usuario;

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
        JLabel mainLabel = new JLabel("Editar Publicación", SwingConstants.CENTER);
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
        tituloField = new JTextField();
        tituloField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        tituloField.setEditable(false); // Deshabilitar la edición
        tituloField.setBackground(Color.LIGHT_GRAY);
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
        fechaDePublicacionField = new JTextField();
        fechaDePublicacionField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        fechaDePublicacionField.setEditable(false); // Deshabilitar la edición
        fechaDePublicacionField.setBackground(Color.LIGHT_GRAY);
        panelCampos.add(fechaDePublicacionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0; // Peso vertical para la tercera fila
        JLabel categoriaLabel = new JLabel("<html><center><span style='font-size: 20px;'>Categoria</html>");
        categoriaLabel.setForeground(Color.WHITE);
        categoriaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelCampos.add(categoriaLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        categoriaField = new JTextField();
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
        descripcionField = new TextArea();
        descripcionField.setBackground(Color.WHITE);
        descripcionField.setForeground(Color.BLACK);
        panelCampos.add(descripcionField, gbc);

        //Panel con los botones
        panelDeBotones = new JPanel();
        panelDeBotones.setLayout(new BoxLayout(panelDeBotones, BoxLayout.Y_AXIS));
        panelDeBotones.setBackground(Color.BLACK);

        BotonPublicar = new JButton("Guardar Cambios");
        BotonCancelar = new JButton("Cancelar");
        BotonMisPublicaciones = new JButton("Mis Publicaciones");
        dashboardBtn = new JButton("Dashboard");

        // Aplicar el estilo 3D a los botones
        ButtonUtils.styleButton3D(BotonPublicar);
        ButtonUtils.styleButton3D(BotonCancelar);
        ButtonUtils.styleButton3D(BotonMisPublicaciones);
        ButtonUtils.styleButton3D(dashboardBtn);

        BotonPublicar.setBorder(new EmptyBorder(10, 20, 10, 20));
        BotonCancelar.setBorder(new EmptyBorder(10, 20, 10, 20));
        BotonMisPublicaciones.setBorder(new EmptyBorder(10, 20, 10, 20));
        dashboardBtn.setBorder(new EmptyBorder(10, 20, 10, 20));

        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(BotonPublicar);
        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(BotonCancelar);
        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(BotonMisPublicaciones);
        panelDeBotones.add(Box.createVerticalGlue());
        panelDeBotones.add(dashboardBtn);
        panelDeBotones.add(Box.createRigidArea(new Dimension(0, 200)));

        //Eventos de los botones
        // ActionListener para el botón "Cancelar"
        BotonCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //cerrar la ventana actual
                dispose();
            }
        });

        // ActionListener para el botón "Guardar Cambios"
        BotonPublicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener información de los campos de texto
                String titulo = tituloField.getText().trim(); // Obtener el texto, pero no se usará si está deshabilitado
                String fechaDePublicacion = fechaDePublicacionField.getText().trim(); // Obtener el texto, pero no se usará si está deshabilitado
                String categoria = categoriaField.getText().trim();
                String descripcion = descripcionField.getText().trim();

                if (!categoria.isEmpty() && !descripcion.isEmpty()) {
                    // Obtener el creador y el estado original del archivo
                    ArrayList<String> datosOriginales = obtenerDatosOriginales();
                    String creadorOriginal = datosOriginales.get(0);
                    String estadoOriginal = datosOriginales.get(1);

                    // Crear una nueva instancia de Publicacion con los datos actualizados
                    Publicacion publicacionActualizada = new Publicacion(publicacion.getTitulo(), publicacion.getfechaDePublicacion(), categoria, descripcion, creadorOriginal, estadoOriginal); // Usar los valores originales

                    // Actualizar todos los archivos correspondientes
                    actualizarTodosLosArchivos(publicacionActualizada);

                    // Cerrar la ventana actual
                    dispose();

                    // Limpiar los campos
                    categoriaField.setText("");
                    descripcionField.setText("");

                } else {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener para el botón "Mis Publicaciones"
        BotonMisPublicaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Abrir Mis Publicaciones
                SwingUtilities.invokeLater(() -> new MisPublicaciones(gestor, usuario));
                //cerrar la ventana actual
                dispose();
            }
        });

        //ActionListener para el botón dashboardBtn
        dashboardBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new Dashboard(usuario));
            dispose();
        });

        // Llenar los campos con los datos de la publicación
        llenarCampos();

        // Agregar paneles al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCampos, BorderLayout.WEST);
        panelPrincipal.add(panelDeBotones, BorderLayout.EAST);

        //Añadir el panel principal al Jframe
        add(panelPrincipal);

        //Estado de la visibilidad
        setVisible(true);

    }

    private void llenarCampos() {
        ArrayList<String> datosOriginales = obtenerDatosOriginales();
        tituloField.setText(publicacion.getTitulo());
        fechaDePublicacionField.setText(publicacion.getfechaDePublicacion());
        categoriaField.setText(publicacion.getCategoria());
        descripcionField.setText(publicacion.getDescripcion());
        // Eliminar la última línea del TextArea
        String descripcionSinEstado = descripcionField.getText().replace("\n" + datosOriginales.get(1), "");
        descripcionField.setText(descripcionSinEstado);
    }

    private ArrayList<String> obtenerDatosOriginales() {
        ArrayList<String> datos = new ArrayList<>();
        String nombreArchivo = publicacion.getTitulo() + ".txt";
        String rutaArchivo = "./main/Data/Publicaciones/" + nombreArchivo;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            for (int i = 0; i < 3; i++) {
                br.readLine(); // Saltar las primeras 3 líneas
            }
            datos.add(br.readLine()); // Leer la cuarta línea (creador)
            String linea;
            String ultimaLinea = "";
            while ((linea = br.readLine()) != null) {
                ultimaLinea = linea; // Guardar la última línea leída
            }
            datos.add(ultimaLinea); // Guardar la última línea (estado)
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al leer el creador del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            datos.add(""); // Retornar una cadena vacía en caso de error
            datos.add("");
        }
        return datos;
    }

    private void actualizarTodosLosArchivos(Publicacion publicacion) {
        String nombreArchivo = publicacion.getTitulo() + ".txt";
        File directorioPublicaciones = new File("./main/Data/Publicaciones/");

        // Actualizar el archivo en la carpeta principal "Publicaciones"
        actualizarArchivo(publicacion, directorioPublicaciones.getAbsolutePath() + "/" + nombreArchivo, false);

        // Actualizar los archivos en las subcarpetas
        File[] subdirectorios = directorioPublicaciones.listFiles(File::isDirectory);
        if (subdirectorios != null) {
            for (File subdirectorio : subdirectorios) {
                String rutaArchivoSubdirectorio = subdirectorio.getAbsolutePath() + "/" + nombreArchivo;
                File archivoSubdirectorio = new File(rutaArchivoSubdirectorio);
                if (archivoSubdirectorio.exists()) {
                    actualizarArchivo(publicacion, rutaArchivoSubdirectorio, false);
                }
            }
        }
    }

    private void actualizarArchivo(Publicacion publicacion, String rutaArchivo, boolean agregar) {
        try (FileWriter writer = new FileWriter(rutaArchivo, agregar)) {
            writer.write(publicacion.getTitulo() + "\n" +
                    publicacion.getfechaDePublicacion() + "\n" +
                    publicacion.getCategoria() + "\n" +
                    publicacion.getCreador() + "\n" +
                    publicacion.getDescripcion() + "\n" +
                    publicacion.getEstado() + "\n"); // Agregar el estado al final
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar/actualizar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}