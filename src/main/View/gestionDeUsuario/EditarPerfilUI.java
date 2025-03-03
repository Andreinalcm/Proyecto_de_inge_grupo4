package main.View.gestionDeUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
//import java.util.*;
import java.util.List;

public class EditarPerfilUI extends JFrame {
    private JPanel panelLateral, panelContenido;
    private JButton btnCuenta, btnSeguridad, btnMisEventos, btnCalendario, btnGrupos, btnCerrarSesion;
    private JLabel lblTitulo;
    private String rutaUsuarios = "src/main/Data/usuarios.txt";
    private String usuarioActual;
    
    public EditarPerfilUI(String usuario) {
        this.usuarioActual = usuario;
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
    }
    
    private void mostrarCuenta() {
        panelContenido.removeAll();
        lblTitulo.setText("Cuenta");
        panelContenido.add(lblTitulo, BorderLayout.NORTH);
        
        String[] datos = obtenerDatosUsuario();
        if (datos != null) {
            JPanel panelInfo = new JPanel(new GridLayout(2, 1, 10, 10));
            panelInfo.setBackground(Color.BLACK);
            
            JLabel lblUsuario = new JLabel("Nombre de Usuario: " + datos[0], SwingConstants.CENTER);
            JLabel lblCorreo = new JLabel("Correo Electrónico: " + datos[1], SwingConstants.CENTER);
            
            lblUsuario.setForeground(Color.WHITE);
            lblCorreo.setForeground(Color.WHITE);
            
            panelInfo.add(lblUsuario);
            panelInfo.add(lblCorreo);
            
            panelContenido.add(panelInfo, BorderLayout.CENTER);
        }
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
        
        btnCambiarContrasena.addActionListener(e -> actualizarDato(2, "Nueva Contraseña"));
        btnCambiarCorreo.addActionListener(e -> actualizarDato(1, "Nuevo Correo"));
        btnCambiarUsuario.addActionListener(e -> actualizarDato(0, "Nuevo Nombre de Usuario"));
        
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
    
    private String[] obtenerDatosUsuario() {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(rutaUsuarios));
            for (String linea : lineas) {
                String[] partes = linea.split(",");
                if (partes[0].equals(usuarioActual)) {
                    return partes;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void actualizarDato(int indice, String mensaje) {
        String nuevoValor = JOptionPane.showInputDialog(this, mensaje);
        if (nuevoValor != null && !nuevoValor.trim().isEmpty()) {
            try {
                List<String> lineas = Files.readAllLines(Paths.get(rutaUsuarios));
                for (int i = 0; i < lineas.size(); i++) {
                    String[] partes = lineas.get(i).split(",");
                    if (partes[0].equals(usuarioActual)) {
                        partes[indice] = nuevoValor;
                        lineas.set(i, String.join(",", partes));
                        Files.write(Paths.get(rutaUsuarios), lineas);
                        JOptionPane.showMessageDialog(this, "Dato actualizado correctamente.");
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}