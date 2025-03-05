package main.View.gestionDeEventos;

import java.awt.*;
import javax.swing.*;

import main.Controller.gestionDeComentarios.GestorComentarios;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Model.gestionDeEventos.Evento;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeUsuario.Usuario;

public class VistaDeEvento {

    private JFrame frame;
    private Evento evento;
    private JFrame ventanaAnterior; // Referencia a la ventana anterior (MisEventos)
    private Usuario usuario; // Usuario actual
    private RepositorioEventos repositorio; // Repositorio de eventos
    private String directorioComentarios = "main/Data/Comentarios/Eventos/";
    private GestorComentarios gestorComentarios;

    public VistaDeEvento(Evento evento, JFrame ventanaAnterior, Usuario usuario, RepositorioEventos repositorio) {
        this.evento = evento;
        this.ventanaAnterior = ventanaAnterior;
        this.usuario = usuario; // Inicializar el usuario actual
        this.repositorio = repositorio; // Inicializar el repositorio
        this.gestorComentarios = GestorComentarios.getInstance();
        frame = new JFrame("Evento: " + evento.getTitulo());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK); // Fondo negro

        // Agregar el título centrado en la parte superior
        agregarTitulo();
        crearPanelDetalles();
        crearPanelBotones();

        // Ocultar la ventana anterior
        ventanaAnterior.setVisible(false);

        // Mostrar la ventana actual
        frame.setVisible(true);

        // Cuando se cierre esta ventana, mostrar la ventana anterior
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ventanaAnterior.setVisible(true);
            }
        });
    }

    private void agregarTitulo() {
        JLabel titulo = new JLabel(evento.getTitulo());
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE); // Texto en blanco
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0)); // Margen superior de 50 y inferior de 20
        frame.add(titulo, BorderLayout.NORTH);
    }

    private void crearPanelDetalles() {
        RoundedPanel panel = new RoundedPanel(30); // Radio de 30 para el borde redondeado
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(51, 51, 51)); // Fondo oscuro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno
    
        // Margen adicional para que no se pegue a los límites de la ventana
        JPanel margenPanel = new JPanel(new BorderLayout());
        margenPanel.setBackground(Color.BLACK); // Fondo negro
        margenPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen externo
        margenPanel.add(panel, BorderLayout.CENTER);
    
        // Crear etiquetas con los detalles del evento
        JLabel publicadoPor = new JLabel("Publicado por: " + evento.getCreador());
        JLabel fechaInicio = new JLabel("Fecha/Hora de inicio: " + evento.getFechaHoraInicio());
        JLabel fechaFin = new JLabel("Fecha/Hora de fin: " + evento.getFechaHoraFin());
        JLabel ubicacion = new JLabel("Ubicación: " + evento.getUbicacion());
        JLabel descripcion = new JLabel("Descripción: " + evento.getDescripcion());
    
        // Estilo de las etiquetas
        Font font = new Font("Arial", Font.PLAIN, 16);
        Color colorTexto = Color.WHITE;
    
        publicadoPor.setFont(font);
        publicadoPor.setForeground(colorTexto);
        descripcion.setFont(font);
        descripcion.setForeground(colorTexto);
        fechaInicio.setFont(font);
        fechaInicio.setForeground(colorTexto);
        fechaFin.setFont(font);
        fechaFin.setForeground(colorTexto);
        ubicacion.setFont(font);
        ubicacion.setForeground(colorTexto);
    
        // Agregar las etiquetas al panel
        panel.add(publicadoPor);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre elementos
        panel.add(descripcion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(fechaInicio);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(fechaFin);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(ubicacion);
    
        // Mostrar el estado solo si el usuario actual es el creador del evento
        if (evento.getCreador().equals(usuario.getNombre())) {
            JLabel estado = new JLabel("Estado: " + evento.getEstado());
            estado.setFont(font);
            estado.setForeground(colorTexto);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(estado);
        }
    
        // Agregar el panel con margen al centro de la ventana
        frame.add(margenPanel, BorderLayout.CENTER);
    }

    // Clase interna para el panel con bordes redondeados
    private class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int cornerRadius) {
            this.cornerRadius = cornerRadius;
            setOpaque(false); // Hacer el panel transparente
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dibujar el fondo redondeado
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Dibujar el borde redondeado
            g2d.setColor(Color.GRAY); // Color del borde
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

            g2d.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(super.getPreferredSize().width, 100); // Ajusta la altura según sea necesario
        }
    }

    private void crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(Color.BLACK); // Fondo negro
    
        JButton comentariosBtn = crearBoton("Comentarios", new Color(51, 51, 51));
        JButton editarBtn = crearBoton("Editar", new Color(51, 51, 51));
        JButton comentarBtn = crearBoton("Comentar", new Color(51, 51, 51));
        JButton regresarBtn = crearBoton("Regresar", new Color(51, 51, 51));
        JButton agregarAlCalendarioBtn = crearBoton("Agregar al calendario", new Color(51, 51, 51));
    
        // ActionListeners para los botones
        comentariosBtn.addActionListener(e -> {
            gestorComentarios.mostrarComentarios(directorioComentarios, evento.getTitulo());
        });
    
        editarBtn.addActionListener(e -> {
            // Cerrar la ventana actual
            frame.dispose();
            
            // Abrir la vista de edición de evento
            EditarEvento editarEvento = new EditarEvento(GestorDeEventos.getInstancia(repositorio), usuario, evento);
            editarEvento.getFrame().setVisible(true);
        });
    
        comentarBtn.addActionListener(e -> {
            String comentario = JOptionPane.showInputDialog("Escribe un comentario:");
            gestorComentarios.guardarComentario(directorioComentarios, evento.getTitulo(), usuario.getUsuario(), comentario);
        });
    
        regresarBtn.addActionListener(e -> {
            frame.dispose(); // Cerrar la ventana actual
            ventanaAnterior.setVisible(true); // Mostrar la ventana anterior (MisEventos)
        });
    
        agregarAlCalendarioBtn.addActionListener(e -> {
            // Lógica para agregar el evento al calendario
            JOptionPane.showMessageDialog(frame, "Evento agregado al calendario.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
    
        panel.add(comentariosBtn);
        panel.add(comentarBtn);
    
        // Solo mostrar el botón "Editar" si el creador del evento es el usuario actual
        if (evento.getCreador().equals(usuario.getNombre())) {
            panel.add(editarBtn);
        }

        // Solo mostrar el botón "Agregar al calendario" si el creador del evento no es el usuario actual
        if (!evento.getCreador().equals(usuario.getNombre())) {
            panel.add(agregarAlCalendarioBtn);
        }

        panel.add(regresarBtn);
    
        frame.add(panel, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Radio de 30
                super.paintComponent(g2d);
                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = Math.max(size.width, 150); // Ancho mínimo
                size.height = 40; // Altura fija
                return size;
            }
        };

        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(Color.WHITE); // Texto en blanco
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setOpaque(false); // Fondo transparente
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding
        return boton;
    }

    public JFrame getFrame() {
        return frame;
    }
}