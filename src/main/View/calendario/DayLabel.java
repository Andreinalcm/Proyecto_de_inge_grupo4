package main.View.calendario;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JLabel;

public class DayLabel extends JLabel {

    public DayLabel(String text, Color background, Color foreground, boolean btn) {
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Helvetica", Font.PLAIN, 20)); // Puedes hacer que la fuente sea un parámetro
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        if (btn) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
