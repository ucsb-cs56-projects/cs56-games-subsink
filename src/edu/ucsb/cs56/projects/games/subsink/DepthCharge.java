package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

/**
 * A DepthCharge is the entity representing the weapons the player drops.
 * It will sink downwards and destroy any submarine it touches.
 */
public class DepthCharge extends Entity {
	/**
	 * Construct a new DepthCharge at the given position
	 *
	 * @param x	The initial x position
	 * @param y	The initial y position
	 */
	public DepthCharge(double x, double y) {
		super(x, y, 10, 15);
		speedX = 0;
		speedY = 20;
	}

	/**
	 * Blow up the charge.
	 */
	public void explode() {
		destroy();
	}

	/**
	 * Update the charge with respect to the world.
	 * Will clean up the charge if it goes off the bottom of the screen.
	 *
	 * @param world	The parent World object
	 * @param time	The time since the last update in seconds
	 */
	public void update(World world, double time) {
		if (y > world.getHeight()) {
			destroy();
		} else {
			super.update(world, time);
		}
	}

	/**
	 * Register the disappearance of the charge.
	 * Will add one to the player's charge stock.
	 *
	 * @param world	The parent World object
	 */
	public void finalize(World world) {
		world.giveStock(1);
	}

	public void paint(Graphics2D g) {
		g.drawImage(ImageLoader.get("img/depth_charge.png"), (int)x, (int)y, null);
	}
}
