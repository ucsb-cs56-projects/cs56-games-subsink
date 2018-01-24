package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

public class HeightCharge extends Entity {
	public HeightCharge(double x, double y) {
		super(x, y, 10, 10);
		speedX = 0;
		speedY = -0.5;
	}

	public void explode() {
		destroy();
	}

	public void update(World world, double time) {
		if (y < world.getWaterHeight()) {
			destroy();
		} else {
			super.update(world, time);
		}
	}

	public void paint(Graphics2D g) {
		g.drawImage(ImageLoader.get("img/height_charge.png"), (int)x, (int)y, null);
	}
}
