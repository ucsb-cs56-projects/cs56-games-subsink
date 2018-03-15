package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by andy on 3/12/18.
 */
public class GameOverScreen extends Screen{

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new GridLayout(5,1,0,10));

        addAButton("Try Again", pane);
        addAButton("Exit", pane);
    }

    public static void main(String[] args){
        GameOverScreen sc = new GameOverScreen();
        sc.run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Try Again":
                startGame();
                break;
            case "Exit":
                System.exit(1);
                break;
        }

    }
}
