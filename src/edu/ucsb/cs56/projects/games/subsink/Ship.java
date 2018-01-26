package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

public class Ship extends Entity {
	private int health = 3;
	private boolean spawning = false;
	private boolean spawnLeft = false;

	public Ship(double x, double y) {
		super(x, y-12, 80, 15);
	}

	public void update(World world, double time) {
		if (speedX < 0 && x < 0) {
			speedX = 0;
			x = 0;
		} else if (speedX > 0 && x + width > world.getWidth()) {
			speedX = 0;
			x = world.getWidth() - width;
		}

		if (spawning) {
			world.spawn(new DepthCharge(spawnLeft ? x - 5 : x + 75, y + 15));
			spawning = false;
		}

		super.update(world, time);
	}

	public void spawn(boolean spawnLeft) {
		spawning = true;
		this.spawnLeft = spawnLeft;
	}

	public void accelerateLeft() {
		if (speedX > -100) {
			speedX -= 10;
		}
	}

	public void accelerateRight() {
		if (speedX < 100) {
			speedX += 10;
		}
	}

	public void damage() {
		health--;
		if (health == 0) {
			destroy();
		}
	}

	@Override
	public void interact(Entity other) {
		if (! (other instanceof HeightCharge)) return;
		HeightCharge h = (HeightCharge) other;
		if (this.intersects(h)) {
			h.explode();
			this.damage();
		}
	}

	public void paint(Graphics2D g) {
		g.drawImage(ImageLoader.get("img/ship.png"), (int)x, (int)y, null);
	}

	public int getHealth() { return health; }
}
