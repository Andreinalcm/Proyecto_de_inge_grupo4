package main.View.gestionDeUsuario;

//Librerias
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

//Imports
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeEventos.RepositorioEventosArchivo;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeEventos.FormularioDeEventos;
import main.View.gestionDeEventos.MisEventos;
import main.View.gestionDeEventos.RevisarPublicacionesOEventos;
import main.View.gestionDeEventos.EventosYPublicaciones;
import main.View.gestionDePublicaciones.VentanaCrearPublicacion;
import main.View.calendario.Calendario;
import main.View.calendario.EventoCalen;
import main.Controller.gestorPublicaciones.GestorPublicaciones;
import main.Model.gestionPublicacion.Publicacion;
import main.View.gestionDePublicaciones.MisPublicaciones;
import main.View.gestionInicioSesion.LoginVista;
import main.View.gestionDeNotificaciones.VerNotificaciones;
import main.Controller.gestionDeNotificaciones.GestorNotificaciones;

public class Dashboard extends JFrame {

    private final GestorDeEventos controller;
    RepositorioEventos repositorioEventos;
    private final Usuario usuario;
    private static GestorPublicaciones gestorPublicaciones;
    Publicacion repositorioPublicacion;

    public Dashboard(Usuario usuario) {

        repositorioEventos = new RepositorioEventosArchivo();
        this.controller = GestorDeEventos.getInstancia(repositorioEventos);
        this.usuario = usuario;

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tamaño de la ventana a pantalla completa
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); // Fondo negro

        // Título con margen superior y menos espacio inferior
        JLabel titulo = new JLabel("Dashboard");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE); // Letras blancas
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Margen superior de 50 y inferior de 20
        add(titulo, BorderLayout.NORTH);

        // Panel principal para los botones
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.BLACK); // Fondo negro

        // Panel para los botones principales
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(Color.BLACK); // Fondo negro

        // Botones comunes a todos los usuarios
        JButton publicarEventoBtn = crearBoton("Publicar evento");
        JButton misEventosBtn = crearBoton("Mis eventos");
        JButton eventosPublicacionesBtn = crearBoton("Eventos y publicaciones");
        JButton revisarCalendarioBtn = crearBoton("Revisar calendario");
        JButton verNotificacionesBtn = crearBoton("Ver notificaciones");

        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(publicarEventoBtn); // Añade el JButton
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(misEventosBtn); // Añade el JButton
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(eventosPublicacionesBtn); // Añade el JButton
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(revisarCalendarioBtn); // Añade el JButton
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(verNotificacionesBtn);

        // Botones adicionales según el rol
        if (usuario.getRol().equals("Profesor") || usuario.getRol().equals("Personal de Apoyo")
                || usuario.getRol().equals("Moderador") || usuario.getRol().equals("Administrador")) {
            JButton hacerPublicacionBtn = crearBoton("Hacer una publicación");
            JButton misPublicacionesBtn = crearBoton("Mis publicaciones");
            panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
            panelBotones.add(hacerPublicacionBtn);
            panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
            panelBotones.add(misPublicacionesBtn);

            //Elemenos para publicación
            repositorioPublicacion = new Publicacion();
            gestorPublicaciones = GestorPublicaciones.getInstancia(repositorioPublicacion);

            hacerPublicacionBtn.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> new VentanaCrearPublicacion(gestorPublicaciones, usuario));
                dispose();
            });

            misPublicacionesBtn.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> new MisPublicaciones(gestorPublicaciones, usuario));
                dispose();
            });
        }

        if (usuario.getRol().equals("Moderador") || usuario.getRol().equals("Administrador")) {
            JButton revisarPublicacionesBtn = crearBoton("Revisar publicaciones o eventos");
            panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
            panelBotones.add(revisarPublicacionesBtn);

            revisarPublicacionesBtn.addActionListener(e -> {
                RevisarPublicacionesOEventos revisarPublicaciones = new RevisarPublicacionesOEventos(controller,usuario, gestorPublicaciones);
                revisarPublicaciones.setVisible(true);
                this.setVisible(false); // Oculta el Dashboard
            });
        }

        // Añadir el panel de botones al panel principal
        panelPrincipal.add(panelBotones);

        // Panel para los botones finales ("Editar Perfil" y "Cerrar Sesión")
        JPanel panelBotonesFinales = new JPanel();
        panelBotonesFinales.setLayout(new BoxLayout(panelBotonesFinales, BoxLayout.Y_AXIS));
        panelBotonesFinales.setBackground(Color.BLACK); // Fondo negro

        // Botones finales
        JButton editarPerfilBtn = crearBoton("Editar Perfil");
        JButton cerrarSesionBtn = crearBoton("Cerrar sesión");

        panelBotonesFinales.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio antes de los botones finales
        panelBotonesFinales.add(editarPerfilBtn); // Añade el JButton
        panelBotonesFinales.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotonesFinales.add(cerrarSesionBtn); // Añade el JButton

        // Añadir el panel de botones finales al panel principal
        panelPrincipal.add(panelBotonesFinales);

        // Añadir el panel principal al centro de la ventana
        add(panelPrincipal, BorderLayout.CENTER);

        // Ajustar el tamaño de los botones
        ajustarTamanoBotones(panelBotones);
        ajustarTamanoBotones(panelBotonesFinales);

        // ActionListener para el botón "Publicar evento"
        publicarEventoBtn.addActionListener(e -> {
            FormularioDeEventos formulario = new FormularioDeEventos(controller, usuario);
            formulario.getFrame().setVisible(true);
            this.setVisible(false); // Oculta el Dashboard
        });

        // ActionListener para el botón "Mis eventos"
        misEventosBtn.addActionListener(e -> {
            MisEventos misEventos = new MisEventos(controller, usuario);
            misEventos.setVisible(true);
            this.setVisible(false); // Oculta el Dashboard
        });

        // ActionListener para el botón "Eventos y publicaciones"
        eventosPublicacionesBtn.addActionListener(e -> {
            EventosYPublicaciones eventosYPublicaciones = new EventosYPublicaciones(controller, usuario, gestorPublicaciones);
            eventosYPublicaciones.setVisible(true);
            this.setVisible(false); // Oculta el Dashboard
        });

        // ActionListener para el botón "Revisar calendario"

        revisarCalendarioBtn.addActionListener(e -> {
            setTitle("Calendario");
            setSize(900, 500);
            setLocationRelativeTo(null);
            getContentPane().setBackground(Color.black);
        
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                    new Calendario(2025, 4, controller, usuario),
                    new EventoCalen(controller, usuario));
            splitPane.setResizeWeight(0.5);
        
            getContentPane().removeAll();
            getContentPane().add(splitPane, BorderLayout.CENTER);
        
            setVisible(true);
            revalidate();
            repaint();
            gestorPublicaciones.getRepositorio();
        });

        // ActionListener para el botón "Ver notificaciones"
        verNotificacionesBtn.addActionListener(e -> {
            GestorNotificaciones.getInstance().mostrarNotificaciones(usuario.getNombre());
        });

        cerrarSesionBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new LoginVista());
            dispose();
        });

        // ActionListener para el botón "Editar Perfil"
        editarPerfilBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new EditarPerfilUI(usuario.getUsuario()).setVisible(true);
            setVisible(true);
            }
        });

        setVisible(true);
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
                // Ajusta el tamaño preferido para que el contenido no sobresalga del borde redondeado
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