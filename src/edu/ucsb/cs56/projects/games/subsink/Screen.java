package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;



public abstract class Screen implements ActionListener{
    JFrame frame;
    World w;

    public abstract void addComponentsToPane(Container pane);

    public void startGame() {
        {
            w = new World(frame.getWidth(), frame.getHeight());
            frame.add(w);
            frame.setContentPane(w);
            frame.setVisible(true);
            frame.transferFocus();
            w.run();
        }
    }

    public void addAButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        container.add(button);
    }

    Screen() {
        frame = new JFrame();
    }

    public void run(){
        frame = new JFrame("Subsink");
        frame.setSize(620, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}