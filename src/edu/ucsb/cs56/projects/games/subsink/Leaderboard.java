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

class Score implements Comparable<Score>, Serializable {
    String userName;
    int userScore;

    public Score(int score, String name) {
        this.userScore = score;
        this.userName = name;
    }

    @Override
    public int compareTo(Score newScore) {
        return userScore < newScore.userScore ? -1 : userScore > newScore.userScore ? 1 : 0;
    }
}


public class Leaderboard extends JFrame implements ActionListener{
    private List<Score> scores;


    public void loadScores(){
        // de-serialize highest scores and put them in scores
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("assets/data/scores");
            ObjectInputStream in = new ObjectInputStream(file);

            // De-serialize
            scores = (List<Score>)in.readObject();

            in.close();
            file.close();

        } catch(IOException | ClassNotFoundException ex) {
            System.out.println("Unable to load leaderboard");
            scores = new ArrayList<Score>();
        }
    }

    public void saveScore(String userName, int score){
        //Saving of object in a file
        loadScores();
        scores.add(new Score(score, userName));

        FileOutputStream file = null;
        try {
            file = new FileOutputStream("assets/data/scores");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(scores);

            out.close();
            file.close();

        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Unable to save score");
        }
    }

    public List<Score> getHighScores(){
        loadScores();
        Collections.sort(scores);
        Collections.reverse(scores);
        return scores;
    }


    public static void main(String[] args) {

        Leaderboard leaderboard = new Leaderboard();
        List<Score> l = leaderboard.getHighScores();
        for(int i = 0; i < 10; i++) {
            System.out.println(l.get(i).userName);
        }

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
        List<Score> scores = getHighScores();
        drawString(g2d, "Leaderboard", getWidth()/2 - 42, 80);
        for (int i = 0; i < 10; i++){ //top 10 scores
            String textToDisplay;
            if (scores.get(i).userScore == 0) {
                textToDisplay = Integer.toString(i + 1) + ".\t"  + "Name: N/A" + ".\t          " + "Score: N/A";
            }else{
                textToDisplay = Integer.toString(i + 1) + ".\t" + "Name: " + scores.get(i).userName +  ".\t          " + "Score: " + Integer.toString(scores.get(i).userScore);
            }
            drawString(g2d, textToDisplay, getWidth()/2 - 100, height);
            height+= 20;
        }

    }

    public void drawString(Graphics g2d, String text, int x,
                           int y) {
        g2d.drawString(text, x, y);
    }

    public void actionPerformed(ActionEvent event){
        List<String> startMenuItems = Arrays.asList("Start Game", "How To Play", "High Scores", "Exit");
//        Screen screen = new Screen(startMenuItems);
        dispose();
    }

}

