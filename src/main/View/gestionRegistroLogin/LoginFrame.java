package main.View.gestionRegistroLogin;

import javax.swing.*;
import main.Controller.gestionDeEventos.GestorDeEventos;
import main.Controller.gestorRegistroLogin.LoginRegisterApp;
import main.Model.gestionDeEventos.RepositorioEventos;
import main.Model.gestionDeEventos.RepositorioEventosArchivo;
import main.Model.gestionDeUsuario.Usuario;
import main.View.gestionDeUsuario.Dashboard;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JTextField usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createTitledBorder("<html><font color='White'>Usuario</font></html>"));
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE); // Texto en blanco
        add(usernameField, gbc);

        gbc.gridy++;
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createTitledBorder("<html><font color='White'>Contraseña</font></html>"));
        passwordField.setForeground(Color.WHITE);
        passwordField.setBackground(Color.BLACK);
        add(passwordField, gbc);

        gbc.gridy++;
        RoundedButton loginButton = new RoundedButton("Iniciar sesión");
        add(loginButton, gbc);

        gbc.gridy++;
        RoundedButton registerButton = new RoundedButton("Registrar");
        add(registerButton, gbc);

        setVisible(true);

        //Metodos

        loginButton.addActionListener(e -> {
            String usuario = usernameField.getText().trim();
            String clave = new String(passwordField.getPassword()).trim();

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


    }
    
}