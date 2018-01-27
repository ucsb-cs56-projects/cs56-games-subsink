package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;

/**
 * A HeightCharge is the weapon the submarine fires.
 * It will rise to the surface, damaging the player ship if it touches.
 */
public class HeightCharge extends Entity {
	/**
	 * Construct a new height charge at the specified position.
	 *
	 * @param x	The initial horizontal position in pixels
	 * @param y	The initial vertical position in pixels
	 */
	public HeightCharge(double x, double y) {
		super(x, y, 10, 10);
		speedX = 0;
		speedY = -15;
	}

	/**
	 * Blow up the charge.
	 */
	public void explode() {
		destroy();
	}

	/**
	 * Update the charge each frame. Will disappear if it reaches the water surface.
	 *
	 * @param world	The encompassing world
	 * @param time	The time since the last update in seconds
	 */
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
