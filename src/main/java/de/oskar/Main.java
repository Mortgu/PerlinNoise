package de.oskar;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Window window = new Window();

        window.getFrame().add(new JButton("Button"));
        window.getFrame().add(new JButton("Button"));
        window.getFrame().add(new JButton("Button"));
        window.getFrame().add(new JButton("Button"));

        window.getFrame().pack();
        window.getFrame().setVisible(true);
    }
}
