package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andy on 3/12/18.
 */
public class StartScreen extends Screen{

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new GridLayout(5,1,0,10));

        addAButton("Start Game", pane);
        addAButton("High Scores", pane);
        addAButton("How To Play", pane);
        addAButton("Exit", pane);
    }

    public static void main(String[] args){
        StartScreen sc = new StartScreen();
        sc.run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Start Game":
                startGame();
                break;
            case "High Scores":
                Leaderboard leaderboard = new Leaderboard();
                leaderboard.paintLeaderboard();
                break;
            case "How To Play":
                HowToPlay howToPlay = new HowToPlay();
                howToPlay.run();
                break;
            case "Exit":
                System.exit(1);
                break;
        }

    }
}
