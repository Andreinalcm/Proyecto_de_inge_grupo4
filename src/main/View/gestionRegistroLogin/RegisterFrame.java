package main.View.gestionRegistroLogin;

import main.Controller.gestorRegistroLogin.LoginRegisterApp;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        // Configuración de la ventana
        setTitle("Registro de Usuario");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Etiqueta de título
        JLabel titleLabel = new JLabel("Registro de usuario", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Panel central con campos
        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 filas, 2 columnas, espaciado de 10px
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Margen interno

        // Crear y agregar etiquetas y campos de texto
        addLabelAndField(centerPanel, "Usuario:");
        JTextField userField = addTextField(centerPanel);

        addLabelAndField(centerPanel, "Nombre:");
        JTextField nameField = addTextField(centerPanel);

        addLabelAndField(centerPanel, "Correo:");
        JTextField emailField = addTextField(centerPanel);

        addLabelAndField(centerPanel, "Rol:");
        JTextField rolField = addTextField(centerPanel);

        addLabelAndField(centerPanel, "Clave:");
        JPasswordField passField = addPasswordField(centerPanel);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JButton registerButton = new JButton("Registrar");
        ButtonUtils.styleButton3D(registerButton);

        // Evento para registrar al usuario
        registerButton.addActionListener(e -> {
            String usuario = userField.getText().trim();
            String nombre = nameField.getText().trim();
            String correo = emailField.getText().trim();
            String clave = new String(passField.getPassword()).trim();
            String rol = rolField.getText().trim();

            if (usuario.isEmpty() || nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || rol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, String[]> usuarios = LoginRegisterApp.leerUsuarios();
            if (usuarios.containsKey(usuario)) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                LoginRegisterApp.guardarUsuario(usuario, nombre, correo, clave, rol);
                JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new LoginFrame();
            }
        });

        buttonPanel.add(registerButton);

        // Agregar componentes al panel principal
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Método para agregar una etiqueta y un campo de texto
    private void addLabelAndField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        panel.add(label);
    }

    // Método para agregar un JTextField
    private JTextField addTextField(JPanel panel) {
        JTextField textField = new JTextField();
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panel.add(textField);
        return textField;
    }

    // Método para agregar un JPasswordField
    private JPasswordField addPasswordField(JPanel panel) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.LIGHT_GRAY);
        passwordField.setForeground(Color.BLACK);
        passwordField.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panel.add(passwordField);
        return passwordField;
    }
}