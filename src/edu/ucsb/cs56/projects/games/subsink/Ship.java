package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

public class Ship extends Entity {
	private int health = 3;

	public Ship(double x, double y) {
		super(x, y, 80, 15);
	}

	public void update(World world, double time) {
		if (speedX < 0 && x < 0) {
			speedX = 0;
			x = 0;
		} else if (speedX > 0 && x + width > world.getWidth()) {
			speedX = 0;
			x = world.getWidth() - width;
		}
		super.update(world, time);
	}

	public void accelerateLeft() {
		if (speedX > -3) {
			speedX -= 0.3;
		}
	}

	public void accelerateRight() {
		if (speedX > 3) {
			speedX += 0.3;
		}
	}

	public void damage() {
		health--;
		if (health == 0) {
			destroy();
		}
	}

	public void interact(HeightCharge other) {
		if (this.intersects(other)) {
			other.explode();
			this.damage();
		}
	}

	public void paint(Graphics2D g) {
		g.drawImage(ImageLoader.get("img/ship.png"), (int)x, (int)y, null);
	}
}
