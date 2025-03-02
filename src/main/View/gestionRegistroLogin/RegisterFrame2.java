package main.View.gestionRegistroLogin;

//import main.Controller.gestorRegistroLogin.LoginRegisterApp;
import main.Controller.gestorRegistroLogin.LoginRegisterApp;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class RegisterFrame2 extends JFrame {
    public RegisterFrame2() {
        // Configuración de la ventana
        setTitle("Registro de Usuario (3D, Blanco y Negro)");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Etiqueta de título
        JLabel titleLabel = new JLabel("Registro de usuario", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Panel central con campos
        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel userLabel = new JLabel("Usuario:");
        JLabel nameLabel = new JLabel("Nombre:");
        JLabel emailLabel = new JLabel("Correo:");
        JLabel passLabel = new JLabel("Clave:");

        userLabel.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        passLabel.setForeground(Color.WHITE);

        JTextField userField = new JTextField();
        userField.setBackground(Color.LIGHT_GRAY);
        userField.setForeground(Color.BLACK);
        userField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JTextField nameField = new JTextField();
        nameField.setBackground(Color.LIGHT_GRAY);
        nameField.setForeground(Color.BLACK);
        nameField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JTextField emailField = new JTextField();
        emailField.setBackground(Color.LIGHT_GRAY);
        emailField.setForeground(Color.BLACK);
        emailField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JPasswordField passField = new JPasswordField();
        passField.setBackground(Color.LIGHT_GRAY);
        passField.setForeground(Color.BLACK);
        passField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        centerPanel.add(userLabel);
        centerPanel.add(userField);
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(emailLabel);
        centerPanel.add(emailField);
        centerPanel.add(passLabel);
        centerPanel.add(passField);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Botones de acción con estilo 3D redondeado
        RoundedButton backButton = new RoundedButton("Regresar");
        RoundedButton registerButton = new RoundedButton("Registrar");

        // Evento para regresar al Login
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        // Evento para registrar al usuario
        registerButton.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String nombre = nameField.getText().trim();
            String correo = emailField.getText().trim();
            String clave = new String(passField.getPassword()).trim();

            if (usuario.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Map<String, String[]> usuarios = LoginRegisterApp.leerUsuarios();
            if (usuarios.containsKey(usuario)) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                LoginRegisterApp.guardarUsuario(usuario, nombre, correo, clave);
                JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginFrame();
            }
        });

        buttonPanel.add(registerButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}