package main.View.gestionRegistroLogin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// Ventana de Inicio de Sesión
public class LoginFrame extends JFrame {
    public LoginFrame() {
        // Configuración de la ventana
        setTitle("Inicio de Sesión (3D, Blanco y Negro)");
        setSize(350, 250);
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
        // Aplica color de fondo y letra a los campos
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

        // Aplica estilo 3D a los botones usando la clase utilitaria
        ButtonUtils.styleButton3D(backButton);
        ButtonUtils.styleButton3D(registerButton);

        // Evento para ir a la ventana de Registro
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        // Agrega botones al panel
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);

        // Agrega componentes al panel principal
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agrega el panel principal a la ventana
        add(mainPanel);

        setVisible(true);
    }
}
