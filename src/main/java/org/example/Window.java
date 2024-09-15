package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    public Window(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

    }

    public void paintImage(BufferedImage bufferedImage) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };

        JButton button;
        panel.add(button = new JButton("dwa"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        add(panel);
    }

    public void showWindow() {
        setVisible(true);
    }
}
