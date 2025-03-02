package main.View.gestionRegistroLogin;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Controller.gestorRegistroLogin.LoginRegisterApp;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeEventos.RepositorioEventosArchivo;
import main.Model.gestionDeUsuario.Usuario;

// LoginRegisterApp.java
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import main.View.gestionDeUsuario.Dashboard;
import main.Model.gestionDeUsuario.Usuario;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Controller.gestionDeEventos.GestorDeEventos;

// =================== Ventana de Inicio de Sesión ===================
public class LoginFrame extends JFrame {
    public LoginFrame() {
        // Configuración de la ventana
        setTitle("Inicio de sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);  // Fondo negro

        // Etiqueta de título
        JLabel titleLabel = new JLabel("Inicio de sesión", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Panel central con campos
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY),  // Borde 3D
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.WHITE);
        JTextField userField = new JTextField();
        userField.setBackground(Color.LIGHT_GRAY);
        userField.setForeground(Color.BLACK);
        userField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JLabel passLabel = new JLabel("Clave:");
        passLabel.setForeground(Color.WHITE);
        JPasswordField passField = new JPasswordField();
        passField.setBackground(Color.LIGHT_GRAY);
        passField.setForeground(Color.BLACK);
        passField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        centerPanel.add(userLabel);
        centerPanel.add(userField);
        centerPanel.add(passLabel);
        centerPanel.add(passField);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JButton backButton = new JButton("Regresar");
        JButton registerButton = new JButton("Registrar");

        ButtonUtils.styleButton3D(backButton);
        ButtonUtils.styleButton3D(registerButton);

        // Evento para iniciar sesión
        JButton loginButton = new JButton("Iniciar sesión");
        ButtonUtils.styleButton3D(loginButton);
        loginButton.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String clave = new String(passField.getPassword()).trim();

            if (usuario.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese usuario y clave.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioActual = LoginRegisterApp.buscarUsuario(usuario, clave);
            if (usuarioActual != null) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.\nBienvenido " + usuarioActual.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Crear el Dashboard con el usuario actual
                RepositorioEventos repositorio2 = new RepositorioEventosArchivo();
                GestorDeEventos controller = GestorDeEventos.getInstancia(repositorio2); // Usar getInstancia
                Dashboard dashboard = new Dashboard(controller, usuarioActual);
                dashboard.setVisible(true);
                dispose(); // Cerrar la ventana de inicio de sesión
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento para ir a la ventana de Registro
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}