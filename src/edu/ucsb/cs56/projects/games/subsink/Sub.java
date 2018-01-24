package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

public class Sub extends Entity {
	private double timeSinceSpawn = 0;
	private double spawnFrequency;

	public Sub(double x, double y, double speed, double spawnFrequency) {
		super(x, y, 60, 10);
		if (x > 0) {
			speedX = -speed;
		} else {
			speedX = speed;
		}
		this.spawnFrequency = spawnFrequency;
	}

	public void interact(DepthCharge other) {
		if (this.intersects(other)) {
			other.explode();
			this.damage();
		}
	}

	public void damage() {
		destroy();
	}

	public void update(World world, double time) {
		if (speedX < 0 && x < 0) {
			speedX = -speedX;
		} else if (speedX > 0 && x + width > world.getWidth()) {
			speedX = -speedX;
		}

		timeSinceSpawn += time;
		if (timeSinceSpawn > spawnFrequency) {
			timeSinceSpawn -= spawnFrequency;
			world.spawn(new HeightCharge(x, y));
		}

		super.update(world, time);
	}

	public void paint(Graphics2D g) {
		g.drawImage(ImageLoader.get("img/sub.png"), (int)x, (int)y, null);
	}
}
