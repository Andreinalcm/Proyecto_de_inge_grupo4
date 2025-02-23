package main.View.gestionRegistroLogin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// Ventana de Registro
class RegisterFrame extends JFrame {
    public RegisterFrame() {
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

        JButton backButton = new JButton("Regresar");
        JButton registerButton = new JButton("Registrar");

        ButtonUtils.styleButton3D(backButton);
        ButtonUtils.styleButton3D(registerButton);

        // Evento para regresar al Login
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        // Agregar componentes al panel principal
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}
