package org.example;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    public Canvas() { }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D graphics = (Graphics2D) g;
        graphics.drawLine(120, 50, 360, 50);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawLine(120, 50, 360, 50);
    }
}
