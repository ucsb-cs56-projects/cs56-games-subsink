package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

public class Sub extends Entity {
	private double spawnCountdown = 0;
	private double spawnFrequency;

	public Sub(double x, double y, double speed, double spawnFrequency) {
		super(x, y, 60, 10);
		if (x > 0) {
			speedX = -speed;
		} else {
			speedX = speed;
		}
		this.spawnFrequency = spawnFrequency;
		this.spawnCountdown = spawnFrequency;
	}

	public void interact(Entity other) {
		if (! (other instanceof DepthCharge)) return;
		DepthCharge d = (DepthCharge)other;
		if (this.intersects(d)) {
			d.explode();
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

		spawnCountdown -= time;
		if (spawnCountdown <= 0) {
			spawnCountdown = Math.random() * 0.5 + spawnFrequency;
			world.spawn(new HeightCharge(x + 30, y - 8));
		}

		super.update(world, time);
	}

	public void paint(Graphics2D g) {
		g.drawImage(ImageLoader.get("img/sub.png"), (int)x, (int)y, null);
	}
}
