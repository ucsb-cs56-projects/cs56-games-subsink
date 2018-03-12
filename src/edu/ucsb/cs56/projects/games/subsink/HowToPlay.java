package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by andy on 3/12/18.
 */
public class HowToPlay extends JFrame implements ActionListener {
    public static void main(String[] args) {
        HowToPlay test = new HowToPlay();

    }


    HowToPlay() {
        JButton backButton = new JButton();
        backButton.setSize(60, 10);
        backButton.setText("Back");
        backButton.setVisible(true);
        backButton.addActionListener(this);
        add(backButton);
        setLayout(new FlowLayout());


        setSize(620, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.CYAN);
        setForeground(Color.BLACK);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int y = 140;
        int x = getWidth()/2 - 110;
        g.drawImage(ImageLoader.get("img/keys/p.png"), x, y, null);
        g2d.drawString("Pause game", x + 60, y+20);
        y += 40;
        g.drawImage(ImageLoader.get("img/keys/x.png"), x, y, null);
        g2d.drawString("Drop depth charge from the right side of the ship", x + 60, y+20);
        y += 40;
        g.drawImage(ImageLoader.get("img/keys/z.png"), x, y, null);
        g2d.drawString("Drop depth charge from the left side of the ship", x + 60, y+20);
        y += 40;
        g.drawImage(ImageLoader.get("img/keys/right.png"), x, y, null);
        g2d.drawString("Accelerate", x + 60, y+20);
        y += 40;
        g.drawImage(ImageLoader.get("img/keys/left.png"), x, y, null);
        g2d.drawString("Reverse", x + 60, y+20);

    }

    public void drawString(Graphics g2d, String text, int x,
                           int y) {
        g2d.drawString(text, x, y);
    }

    public void actionPerformed(ActionEvent event){
        java.util.List<String> startMenuItems = Arrays.asList("Start Game", "How To Play" ,"High Scores", "Exit");
        Screen screen = new Screen(startMenuItems);
        dispose();
    }


}
