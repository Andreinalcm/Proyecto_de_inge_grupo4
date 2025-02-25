package main.View.gestionDePublicaciones;

import main.Controller.gestorPublicaciones.GestorPublicaciones;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class VentanaCrearPublicacion extends JFrame {

    JPanel panelPrincipal, panelCampos, panelDeBotones, panelTitulo;
    JButton BotonPublicar, BotonCancelar, BotonMisPublicaciones;
    private GestorPublicaciones gestor;
    private VentanaPublicaciones ventana;

    //Constructor
    public VentanaCrearPublicacion(GestorPublicaciones gestor, VentanaPublicaciones ventana) {

        //asignar variables por parametro
        this.gestor = gestor;
        this.ventana = ventana;

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
        //tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
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
        JLabel creadorLabel = new JLabel("<html><center><span style='font-size: 20px;'>Creador</html>");
        //creadorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        creadorLabel.setForeground(Color.WHITE);
        creadorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelCampos.add(creadorLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField creadorField = new JTextField();
        creadorField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelCampos.add(creadorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0; // Peso vertical para la quinta fila
        JLabel descripcionLabel = new JLabel("<html><center><span style='font-size: 20px;'>Descripción</html>");
        //descripcionLabel.setFont(new Font("Arial", Font.BOLD, 20));
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
        //panelCampos.add(new JScrollPane(descripcionField), gbc);

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
                // Actualizar la lista de publicaciones en VentanaCrearPublicacion
                ventana.actualizarPublicacionsArea(ventana.getPublicacionesPanel());

                //Hacer visible ventana de publicaciones
                ventana.setVisible(true);
                
                //cerrar la ventana actual
                //dispose();
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
                creadorField.setText("");

                // Actualizar la lista de publicaciones en VentanaCrearPublicacion
                ventana.actualizarPublicacionsArea(ventana.getPublicacionesPanel());

                //Hacer visible ventana de publicaciones
                ventana.setVisible(true);
                
                //cerrar la ventana actual
                dispose();
            }
        });

        // ActionListener para el botón "Publicar"
        BotonPublicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Obtener información
                String titulo = tituloField.getText().trim();
                String fechaDePublicacion = fechaDePublicacionField.getText().trim();
                String categoria = categoriaField.getText().trim();
                String creador = creadorField.getText().trim();
                String descripcion = descripcionField.getText().trim();
                
                if (!titulo.isEmpty() && !fechaDePublicacion.isEmpty() && !categoria.isEmpty() && !creador.isEmpty() && !descripcion.isEmpty()) {
                    gestor.crearPublicacion(titulo, fechaDePublicacion, categoria, creador, descripcion);
                    // Actualizar la lista de publicaciones en VentanaCrearPublicacion
                    ventana.actualizarPublicacionsArea(ventana.getPublicacionesPanel());
                    // Guardar en archivo
                    guardarEnArchivo(titulo, fechaDePublicacion, categoria, creador, descripcion);
                    //Hacer visible ventana de publicaciones
                    ventana.setVisible(true);
                    //cerrar la ventana actual
                    dispose();

                    // Limpiar los campos
                    tituloField.setText("");
                    fechaDePublicacionField.setText("");
                    categoriaField.setText("");
                    descripcionField.setText("");
                    creadorField.setText("");
                    
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

    //Guardar en txt
    private void guardarEnArchivo(String titulo, String fechaDePublicacion, String categoria, String descripcion, String creador) {
        // Limpiar el título para usarlo como nombre de archivo
        String nombreArchivo = titulo.replaceAll("[^a-zA-Z0-9\\.\\-]", "_") + ".txt";
        String rutaArchivo = "./main/Data/" + nombreArchivo;
        System.out.println("Ruta del archivo: " + rutaArchivo);
        System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));
        try (FileWriter writer = new FileWriter(rutaArchivo, true)) { // true para agregar al final del archivo
            writer.write(titulo + "\n" + fechaDePublicacion + "\n" + categoria + "\n" + creador + "\n" + descripcion + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
}
