import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase para aplicar estilo 3D a los botones.
 */
class Button3DUI extends javax.swing.plaf.basic.BasicButtonUI {
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JButton button = (JButton) c;
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.DARK_GRAY),
                new EmptyBorder(5, 15, 5, 15)
        ));
        button.setFocusPainted(false);
    }
}
