package main.View.gestionDeUsuario;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class EditarPerfilUI extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnGuardar, btnRegresar;
    private String usuarioActual;
    private File usuariosFile = new File("main/Data/usuarios.txt");

    public EditarPerfilUI(String usuario) {
        this.usuarioActual = usuario;
        setTitle("Editar Perfil");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JPanel panelCuenta = new JPanel(new GridBagLayout());
        panelCuenta.setBackground(Color.BLACK);

        JLabel lblUsuario = new JLabel("Nombre de Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        JLabel lblNombre = new JLabel();
        lblNombre.setForeground(Color.WHITE);
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setForeground(Color.WHITE);
        txtCorreo = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(Color.WHITE);
        txtPassword = new JPasswordField();

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panelCuenta.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panelCuenta.add(lblNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panelCuenta.add(lblCorreo, gbc);
        gbc.gridx = 1;
        panelCuenta.add(txtCorreo, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        panelCuenta.add(lblPassword, gbc);
        gbc.gridx = 1;
        panelCuenta.add(txtPassword, gbc);

        cargarDatosUsuario(lblNombre);

        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBackground(Color.DARK_GRAY);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        btnGuardar.addActionListener(e -> actualizarDatosUsuario());

        // Botón para regresar al dashboard
        btnRegresar = new JButton("Regresar al Dashboard");
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(Color.DARK_GRAY);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        btnRegresar.addActionListener(e -> {
            // Here you can add logic to return to the dashboard
            JOptionPane.showMessageDialog(this, "Regresando al Dashboard...");
            dispose();  // Close the current window
        });

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelCuenta.add(btnGuardar, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelCuenta.add(btnRegresar, gbc);

        gbc.gridx = 0; gbc.gridy = 0;
        add(panelCuenta, gbc);
    }

    private void cargarDatosUsuario(JLabel lblNombre) {
        try (Scanner scanner = new Scanner(usuariosFile)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(",");
                if (datos[0].equals(usuarioActual)) {
                    lblNombre.setText(datos[2]);
                    txtCorreo.setText(datos[3]);
                    txtPassword.setText(datos[1]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el archivo de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarDatosUsuario() {
        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;
        try (Scanner scanner = new Scanner(usuariosFile)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(",");
                if (datos[0].equals(usuarioActual)) {
                    datos[1] = new String(txtPassword.getPassword());
                    datos[3] = txtCorreo.getText();
                    lineas.add(String.join(",", datos));
                    actualizado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró el archivo de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!actualizado) {
            JOptionPane.showMessageDialog(this, "Error: Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PrintWriter writer = new PrintWriter(usuariosFile)) {
            for (String linea : lineas) {
                writer.println(linea);
            }
            JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los cambios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditarPerfilUI ventana = new EditarPerfilUI("usuarioEjemplo");
            ventana.setVisible(true);
        });
    }
}