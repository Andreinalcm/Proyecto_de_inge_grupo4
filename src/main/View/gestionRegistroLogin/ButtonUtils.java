package main.View.gestionRegistroLogin;
// LoginRegisterApp.java
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

// =================== Clase utilitaria para botones ===================
public class ButtonUtils {
    public static void styleButton3D(JButton button) {
        button.setUI(new Button3DUI());
    }
}
