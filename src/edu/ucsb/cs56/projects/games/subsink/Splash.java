package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.io.IOException;

/**
 * Created by andy on 2/27/18.
 */
public class Splash extends Entity {
    Splash(double x, double y) {
        super(x, y, 15, 5);
    }

    @Override
    public void update(World world, double time) throws IOException {
        super.update(world, time);
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawImage(ImageLoader.get("img/splash.png"), (int) x, (int) y, null);

    }
}
