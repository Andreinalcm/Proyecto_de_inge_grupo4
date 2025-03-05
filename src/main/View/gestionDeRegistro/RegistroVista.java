package main.View.gestionDeRegistro;

import main.Controller.gestionRegistro.RegistroControlador;
import javax.swing.*;
import java.awt.*;

public class RegistroVista extends JFrame {

    private JTextField nombreField, emailField, usuarioField, rolField;
    private JPasswordField claveField; // Usamos JPasswordField para la contraseña
    private JButton registrarButton;
    private RegistroControlador controlador;
    private JLabel nombreLabel, emailLabel, usuarioLabel, claveLabel, rolLabel; // Declaración de etiquetas

    public RegistroVista() {
        setTitle("Registro");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        // Centrar la ventana
        setLocationRelativeTo(null);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                centrarComponentes();
            }
        });

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        nombreLabel = new JLabel("Nombre");
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setFont(labelFont);
        add(nombreLabel);

        nombreField = new JTextField();
        add(nombreField);

        emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        emailField = new JTextField();
        add(emailField);

        usuarioLabel = new JLabel("Usuario");
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setFont(labelFont);
        add(usuarioLabel);

        usuarioField = new JTextField();
        add(usuarioField);

        claveLabel = new JLabel("Contraseña");
        claveLabel.setForeground(Color.WHITE);
        claveLabel.setFont(labelFont);
        add(claveLabel);

        claveField = new JPasswordField(); // Usamos JPasswordField para la contraseña
        add(claveField);

        rolLabel = new JLabel("Rol");
        rolLabel.setForeground(Color.WHITE);
        rolLabel.setFont(labelFont);
        add(rolLabel);

        rolField = new JTextField();
        add(rolField);

        registrarButton = new JButton("Registrar");
        registrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        registrarButton.setBackground(Color.DARK_GRAY);
        registrarButton.setForeground(Color.WHITE);
        add(registrarButton);

        controlador = new RegistroControlador(this);
        registrarButton.addActionListener(controlador);

        setVisible(true);
        centrarComponentes();

    }

    private void centrarComponentes() {
        int width = getWidth();
        int height = getHeight();

        int fieldWidth = 200;
        int fieldHeight = 30;
        int buttonWidth = 200;
        int buttonHeight = 45;

        int centerX = (width - fieldWidth) / 2;
        int centerY = height / 6;

        nombreLabel.setBounds(centerX, centerY - 25, fieldWidth, fieldHeight);
        nombreField.setBounds(centerX, centerY, fieldWidth, fieldHeight);
        emailLabel.setBounds(centerX, centerY + 35, fieldWidth, fieldHeight);
        emailField.setBounds(centerX, centerY + 60, fieldWidth, fieldHeight);
        usuarioLabel.setBounds(centerX, centerY + 95, fieldWidth, fieldHeight);
        usuarioField.setBounds(centerX, centerY + 120, fieldWidth, fieldHeight);
        claveLabel.setBounds(centerX, centerY + 155, fieldWidth, fieldHeight);
        claveField.setBounds(centerX, centerY + 180, fieldWidth, fieldHeight);
        rolLabel.setBounds(centerX, centerY + 215, fieldWidth, fieldHeight);
        rolField.setBounds(centerX, centerY + 240, fieldWidth, fieldHeight);
        registrarButton.setBounds(centerX, centerY + 285, buttonWidth, buttonHeight);
    }

    public String getNombre() {
        return nombreField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getUsuario() {
        return usuarioField.getText();
    }

    public String getClave() {
        return new String(claveField.getPassword()); // Correctamente usando JPasswordField
    }

    public String getRol() {
        return rolField.getText();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cerrarVentana() {
        dispose();
    }
}