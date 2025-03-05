package main.View.gestionInicioSesion;

import main.Controller.gestionInicioSesion.LoginControlador;
import main.View.gestionDeRegistro.RegistroVista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginVista extends JFrame {

    private JTextField usuarioField;
    private JPasswordField claveField;
    private JButton loginButton, registroButton;
    private JLabel usuarioLabel, claveLabel, tituloLabel;
    private LoginControlador controlador;

    public LoginVista() {
        setTitle("Login");
        setSize(500, 400);
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

        tituloLabel = new JLabel("VirtualCiencias");
        tituloLabel.setForeground(Color.WHITE);

        //tamaño del titulo
        Font tituloFont = new Font("Lucida Handwriting", Font.BOLD, 20);
        tituloLabel.setFont(tituloFont);

        add(tituloLabel);

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

        claveField = new JPasswordField();
        add(claveField);

        loginButton = new RoundedButton("Iniciar sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        add(loginButton);

        registroButton = new RoundedButton("Registrar");
        registroButton.setFont(new Font("Arial", Font.BOLD, 14));
        registroButton.setBackground(Color.DARK_GRAY);
        registroButton.setForeground(Color.WHITE);
        add(registroButton);

        controlador = new LoginControlador(this);
        loginButton.addActionListener(controlador);
        registroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroVista registroVista = new RegistroVista();
                registroVista.setVisible(true);
            }
        });

        setVisible(true);
        centrarComponentes();
    }

    /* 
    private void centrarComponentes() {
        int width = getWidth();
        int height = getHeight();

        int fieldWidth = 200;
        int fieldHeight = 30;
        int buttonWidth = 200;
        int buttonHeight = 45;

        int centerX = (width - fieldWidth) / 2;
        int centerY = height / 4;

        usuarioLabel.setBounds(centerX, centerY - 30, fieldWidth, fieldHeight);
        usuarioField.setBounds(centerX, centerY, fieldWidth, fieldHeight);
        claveLabel.setBounds(centerX, centerY + 40, fieldWidth, fieldHeight);
        claveField.setBounds(centerX, centerY + 70, fieldWidth, fieldHeight);
        loginButton.setBounds(centerX, centerY + 120, buttonWidth, buttonHeight);
        registroButton.setBounds(centerX, centerY + 170, buttonWidth, buttonHeight);
    }*/

    private void centrarComponentes() {
        int width = getWidth();
        int height = getHeight();
    
        int fieldWidth = 200;
        int fieldHeight = 30;
        int buttonWidth = 200;
        int buttonHeight = 45;
    
        int centerX = (width - fieldWidth) / 2;
        int centerY = height / 4;
    
        // Ajusta el centerY para tituloLabel para que esté más arriba
        int tituloCenterY = centerY - 60; // Ajusta este valor según sea necesario
    
        tituloLabel.setBounds(centerX, tituloCenterY, fieldWidth, fieldHeight);
        usuarioLabel.setBounds(centerX, centerY - 30, fieldWidth, fieldHeight);
        usuarioField.setBounds(centerX, centerY, fieldWidth, fieldHeight);
        claveLabel.setBounds(centerX, centerY + 40, fieldWidth, fieldHeight);
        claveField.setBounds(centerX, centerY + 70, fieldWidth, fieldHeight);
        loginButton.setBounds(centerX, centerY + 120, buttonWidth, buttonHeight);
        registroButton.setBounds(centerX, centerY + 170, buttonWidth, buttonHeight);
    }

    public String getUsuario() {
        return usuarioField.getText();
    }

    public String getClave() {
        return new String(claveField.getPassword());
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}