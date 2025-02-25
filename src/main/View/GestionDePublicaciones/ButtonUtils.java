package main.View.gestionDePublicaciones;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class ButtonUtils {
    public static void styleButton3D(JButton button) {
        button.setUI(new Button3DUI());
    }
}