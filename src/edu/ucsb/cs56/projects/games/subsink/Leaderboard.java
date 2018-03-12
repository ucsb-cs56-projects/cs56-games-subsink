package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


/**
 * Created by andy on 3/11/18.
 */
public class Leaderboard extends JFrame implements ActionListener{
    private ArrayList<Integer> scores;

    Leaderboard(){
        // de-serialize highest scores and put them in scores
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("assets/data/scores");
            ObjectInputStream in = new ObjectInputStream(file);

            // De-serialize
            scores = (ArrayList<Integer>)in.readObject();

            in.close();
            file.close();

        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("Unable to load leaderboard");
            scores = new ArrayList<>();
        }

    }

    public void saveScore(int score){
        //Saving of object in a file
        scores.add(score);
        FileOutputStream file = null;
        try {
            file = new FileOutputStream("assets/data/scores");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(scores);

            out.close();
            file.close();

        }catch (IOException ex){
            System.out.println("Unable to save score");
        }
    }

    public ArrayList<Integer> getHighScores(){
        Collections.sort(scores);
        Collections.reverse(scores);
        return scores;
    }


    public static void main(String[] args) {
        Leaderboard test = new Leaderboard();
        for (int i = 0; i < 10; i++){
            test.saveScore(0);
        }
        test.paintLeaderboard();

    }


    public void paintLeaderboard() {
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
        int height = 140;
        ArrayList<Integer> ar = getHighScores();
        drawString(g2d, "Leaderboard", getWidth()/2 - 42, 80);
        for (int i = 0; i < 10; i++){ //top 10 scores
            String textToDisplay;
            if (ar.get(i) == 0) {
                textToDisplay = Integer.toString(i + 1) + ".\t" + "N/A";
            }else{
                textToDisplay = Integer.toString(i + 1) + ".\t" + ar.get(i).toString();
            }
            drawString(g2d, textToDisplay, getWidth()/2 - 30, height);
            height+= 20;
        }

    }

    public void drawString(Graphics g2d, String text, int x,
                           int y) {
        g2d.drawString(text, x, y);
    }

    public void actionPerformed(ActionEvent event){
        List<String> startMenuItems = Arrays.asList("Start Game", "High Scores", "Exit");
        Screen screen = new Screen(startMenuItems);
        dispose();
    }

}

