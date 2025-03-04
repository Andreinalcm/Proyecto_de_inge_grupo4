package main.View.calendario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calendario extends JPanel {

    private static final long serialVersionUID = 1L;

    private int añoActual;
    private int mesActual;
    private JLabel dateLabel;
    private JPanel daysPanel;
    private DayLabel diaSeleccionado;

    public Calendario(int año, int mes) {
        añoActual = año;
        mesActual = mes;

        setLayout(new BorderLayout(30, 30));
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));
        setBackground(Color.black);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBackground(null);

        dateLabel = new JLabel();
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
        dateLabel.setForeground(Color.decode("#FFFFFF"));
        top.add(dateLabel, BorderLayout.CENTER);

        // Ajustar imagen de la flecha izquierda
        ImageIcon scaledLeftIcon = loadImageIcon("pics/Flecha_izq_B.png", 30, 30);
        JLabel left = new JLabel(scaledLeftIcon);
        left.setCursor(new Cursor(Cursor.HAND_CURSOR));
        left.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                retrocederMes();
            }
        });
        top.add(left, BorderLayout.WEST);

        // Ajustar imagen de la flecha derecha
        ImageIcon scaledRightIcon = loadImageIcon("pics/Flecha_der_B.png", 30, 30);
        JLabel right = new JLabel(scaledRightIcon);
        right.setCursor(new Cursor(Cursor.HAND_CURSOR));
        right.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                avanzarMes();
            }
        });
        top.add(right, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        daysPanel = new JPanel(new GridLayout(7, 7));
        daysPanel.setBackground(null);

        add(daysPanel, BorderLayout.CENTER);

        actualizarCalendario();
    }

    private void actualizarCalendario() {
        daysPanel.removeAll();
        daysPanel.revalidate();
        daysPanel.repaint();

        LocalDate dateValue = LocalDate.of(añoActual, mesActual, 1);
        String formattedDate = dateValue.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")) + " " + añoActual;
        dateLabel.setText(formattedDate);

        Color header = Color.decode("#1FB5DA");

        daysPanel.add(new DayLabel("Lu", header, Color.white, false));
        daysPanel.add(new DayLabel("Ma", header, Color.white, false));
        daysPanel.add(new DayLabel("Mi", header, Color.white, false));
        daysPanel.add(new DayLabel("Ju", header, Color.white, false));
        daysPanel.add(new DayLabel("Vi", header, Color.white, false));
        daysPanel.add(new DayLabel("Sa", header, Color.white, false));
        daysPanel.add(new DayLabel("Do", header, Color.white, false));

        LocalDate firstDay = LocalDate.of(añoActual, mesActual, 1);
        DayOfWeek firstDayOfWeek = firstDay.getDayOfWeek();
        int dayOfWeekValue = firstDayOfWeek.getValue(); // 1 (Lunes) to 7 (Domingo)

        for (int i = 1; i < dayOfWeekValue; i++) {
            daysPanel.add(new DayLabel("", Color.decode("#f0f0f0"), Color.black, false));
        }

        int daysNum = YearMonth.of(añoActual, mesActual).lengthOfMonth();

        for (int i = 1; i <= daysNum; i++) {
            DayLabel dayLabel = new DayLabel(i + "", Color.decode("#f0f0f0"), Color.black, true);
            dayLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    marcarDiaSeleccionado(dayLabel);
                }
            });
            daysPanel.add(dayLabel);
        }

        int totalDays = dayOfWeekValue - 1 + daysNum;
        int remainingDays = 42 - totalDays;

        for (int i = 0; i < remainingDays; i++) {
            daysPanel.add(new DayLabel("", Color.decode("#f0f0f0"), Color.black, false));
        }
    }

    private void marcarDiaSeleccionado(DayLabel nuevoDiaSeleccionado) {
        if (diaSeleccionado != null) {
            diaSeleccionado.setBackground(Color.decode("#f0f0f0"));
        }
        nuevoDiaSeleccionado.setBackground(Color.decode("#ADD8E6")); // Color azul claro para el día seleccionado
        diaSeleccionado = nuevoDiaSeleccionado;
    }

    private void avanzarMes() {
        mesActual++;
        if (mesActual > 12) {
            mesActual = 1;
            añoActual++;
        }
        actualizarCalendario();
    }

    private void retrocederMes() {
        mesActual--;
        if (mesActual < 1) {
            mesActual = 12;
            añoActual--;
        }
        actualizarCalendario();
    }

    // Método para cargar y escalar ImageIcon con manejo de excepciones
    private ImageIcon loadImageIcon(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.dispose();
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + path);
            e.printStackTrace();
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }



}