package main.View.gestionDePublicaciones;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionPublicacion.Publicacion;

public class VentanaVerPublicacion extends JFrame{
    private JFrame frame; 
    private JPanel panelpubli;
    private GestorPublicaciones gestor;
    private Usuario usuario;
    private Publicacion publicacion;

    public VentanaVerPublicacion(GestorPublicaciones jestor, Usuario user, Publicacion post){
        this.gestor = jestor;
        this.usuario = user;
        this.publicacion = post;
       frame = new JFrame(publicacion.getTitulo());
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
       frame.setLayout(new BorderLayout());
       frame.getContentPane().setBackground(Color.BLACK);
       frame.add(panelpubli);
        agregarInfo();
        agregarTitulo();
        frame.setVisible(true);
    }
    private void agregarTitulo() {
        JLabel titulo = new JLabel(publicacion.getTitulo());
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE); // Texto en blanco
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Margen superior de 50 y inferior de 20
        frame.add(titulo, BorderLayout.NORTH);
    }
    private void agregarInfo(){
        JLabel fechaPublicacion = new JLabel("Fecha de publicación: "+ publicacion.getfechaDePublicacion());
        JLabel creadorPublicacion = new JLabel("Creado por: " +  publicacion.getCreador());
        JTextArea descripcion = new JTextArea(publicacion.getDescripcion());
        JButton aPublicaciones = new JButton("Volver");
        JButton comentar = new JButton("Comentar");
     


        fechaPublicacion.setHorizontalAlignment(SwingConstants.CENTER);
        fechaPublicacion.setForeground(Color.WHITE);
        fechaPublicacion.setBounds(70, 70, 75, 75);
        creadorPublicacion.setHorizontalAlignment(SwingConstants.CENTER);
        creadorPublicacion.setForeground(Color.WHITE);
        creadorPublicacion.setBounds(150, 60, 100, 75);
        descripcion.setBounds(150, 90, 200, 200);
        descripcion.setBackground(Color.BLACK);
        descripcion.setForeground(Color.WHITE);
        comentar.setHorizontalAlignment(SwingConstants.CENTER);
        comentar.setLayout(getLayout());
        comentar.setBounds(20, 20, 50, 50);
        ajustarTamanoBotones(panelpubli);
        panelpubli.add(creadorPublicacion);
        panelpubli.add(fechaPublicacion, BorderLayout.NORTH);
        panelpubli.add(descripcion);
        panelpubli.add(comentar);
        ajustarTamanoBotones(panelpubli);
    }
    public void setVisible(boolean b) {
      frame.setVisible(b);
    }


  private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                // Dibuja un fondo redondeado
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Radio de 30
                super.paintComponent(g2d);
                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                // Ajusta el tamaño preferido para que el contenido no sobresalga del borde
                // redondeado
                Dimension size = super.getPreferredSize();
                size.width = Math.max(size.width, 275); // Ancho mínimo de 350 píxeles
                size.height = 40; // Altura fija para todos los botones
                return size;
            }
        };

        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(51, 51, 51));
        boton.setFocusPainted(false);
        boton.setOpaque(false); // Hace que el botón sea transparente
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding reducido

        return boton; // Retorna el JButton directamente
    }

    // Método para asegurar que todos los botones tengan el mismo tamaño
    private void ajustarTamanoBotones(JPanel panelBotones) {
        int maxWidth = 0;
        int maxHeight = 0;

        // Encuentra el ancho y alto máximo de los botones
        for (Component comp : panelBotones.getComponents()) {
            if (comp instanceof JButton) {
                Dimension size = comp.getPreferredSize();
                if (size.width > maxWidth) {
                    maxWidth = size.width;
                }
                if (size.height > maxHeight) {
                    maxHeight = size.height;
                }
            }
        }

        // Establece el mismo tamaño para todos los botones
        for (Component comp : panelBotones.getComponents()) {
            if (comp instanceof JButton) {
                Dimension size = comp.getPreferredSize();
                size.width = maxWidth;
                size.height = maxHeight;
                comp.setPreferredSize(size);
                comp.setMaximumSize(size); // Asegura que no se expandan más allá del tamaño máximo
            }
        }
    }

    // Clase interna para crear bordes redondeados
    private static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 0, 0);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }


}
   
