package main.View.gestionDeUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarPerfilUIOLD extends JFrame {
    private JPanel panelLateral, panelContenido;
    private JButton btnCuenta, btnSeguridad, btnMisEventos, btnCalendario, btnGrupos, btnCerrarSesion;
    private JLabel lblTitulo;
    
    public EditarPerfilUIOLD() {
        setTitle("Usuario | Editar Perfil");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        panelLateral = new JPanel();
        panelLateral.setLayout(new GridLayout(6, 1, 10, 10));
        panelLateral.setBackground(Color.BLACK);
        
        btnCuenta = new JButton("Cuenta");
        btnSeguridad = new JButton("Seguridad");
        btnMisEventos = new JButton("Mis eventos");
        btnCalendario = new JButton("Calendario");
        btnGrupos = new JButton("Grupos de Extensión");
        btnCerrarSesion = new JButton("Cerrar sesión");
        
        JButton[] botones = {btnCuenta, btnSeguridad, btnMisEventos, btnCalendario, btnGrupos, btnCerrarSesion};
        for (JButton btn : botones) {
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            panelLateral.add(btn);
        }
        
        add(panelLateral, BorderLayout.WEST);
        
        panelContenido = new JPanel();
        panelContenido.setBackground(Color.BLACK);
        panelContenido.setLayout(new BorderLayout());
        
        lblTitulo = new JLabel("Editar Perfil", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);
        
        add(panelContenido, BorderLayout.CENTER);
        
        btnCuenta.addActionListener(e -> mostrarCuenta());
        btnSeguridad.addActionListener(e -> mostrarSeguridad());

        setVisible(true);
    }
    
    private void mostrarCuenta() {
        panelContenido.removeAll();
        lblTitulo.setText("Cuenta");
        panelContenido.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 10, 10));
        panelInfo.setBackground(Color.BLACK);
        
        JLabel lblUsuario = new JLabel("Nombre de Usuario: usuario123", SwingConstants.CENTER);
        JLabel lblCorreo = new JLabel("Correo Electrónico: usuario@example.com", SwingConstants.CENTER);
        
        lblUsuario.setForeground(Color.WHITE);
        lblCorreo.setForeground(Color.WHITE);
        
        panelInfo.add(lblUsuario);
        panelInfo.add(lblCorreo);
        
        panelContenido.add(panelInfo, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void mostrarSeguridad() {
        panelContenido.removeAll();
        lblTitulo.setText("Seguridad");
        panelContenido.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelOpciones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelOpciones.setBackground(Color.BLACK);
        
        JButton btnCambiarContrasena = new JButton("Cambiar Contraseña");
        JButton btnCambiarCorreo = new JButton("Cambiar Correo");
        JButton btnCambiarUsuario = new JButton("Cambiar Nombre de Usuario");
        
        JButton[] botonesSeguridad = {btnCambiarContrasena, btnCambiarCorreo, btnCambiarUsuario};
        for (JButton btn : botonesSeguridad) {
            btn.setBackground(Color.DARK_GRAY);
            btn.setForeground(Color.WHITE);
            panelOpciones.add(btn);
        }
        
        panelContenido.add(panelOpciones, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
