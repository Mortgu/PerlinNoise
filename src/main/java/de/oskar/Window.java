package de.oskar;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class Window extends JFrame {

    private JFrame frame;
    private final int width, height;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;

        this.initWindow();
    }

    public Window() {
        this.width = 50;
        this.height = 50;

        this.initWindow();
    }

    private void initWindow() {
        this.frame = new JFrame();
        this.frame.setSize(width, height);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
    }
}
