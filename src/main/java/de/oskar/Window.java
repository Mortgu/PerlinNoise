package de.oskar;

import lombok.Getter;
import javax.swing.*;

@Getter
public class Window extends JFrame {

    public Window() {
        setTitle("Perlin Noise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);



        pack();
        setVisible(true);
    }
}
