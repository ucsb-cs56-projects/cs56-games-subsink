package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.io.IOException;

/**
 * Created by andy on 2/27/18.
 */
public class Bubbles extends Entity {
    boolean chargeBubble = false;

    private double bubbleSpawnCountdown = 0;
    private double bubbleSpawnFrequency;

    Bubbles(double x, double y , boolean chargeBubble){
        super(x, y, 10, 10);
        speedX = 0;
        speedY = -50;

        this.chargeBubble = true;
    }

    Bubbles(double x, double y){
        super(x, y, 72, 48);
        speedX = 0;
        speedY = -50;
    }

    @Override
    public void update(World world, double time) {
        if (y < world.getWaterHeight()) {
            destroy();

        }
        super.update(world, time);
    }


    @Override
    public void paint(Graphics2D g) {
        if (chargeBubble) {
            g.drawImage(ImageLoader.get("img/chargebubble.png"), (int) x, (int) y, null);
        } else {
            g.drawImage(ImageLoader.get("img/bubbles.png"), (int) x, (int) y, null);
        }
    }
}
