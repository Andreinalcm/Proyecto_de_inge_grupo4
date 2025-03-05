package main.View.gestionDeRegistro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        //setBackground(new Color(30, 144, 255)); // Azul predeterminado
        setBackground(Color.GRAY);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //setBackground(new Color(65, 105, 225)); // Azul más oscuro al pasar el mouse
                setBackground(Color.GRAY);
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //setBackground(new Color(30, 144, 255)); // Azul normal
                setBackground(Color.GRAY);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Bordes más redondos
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 2;
        g2.drawString(getText(), x, y);
        g2.dispose();
        super.paintComponent(g);
    }
}
