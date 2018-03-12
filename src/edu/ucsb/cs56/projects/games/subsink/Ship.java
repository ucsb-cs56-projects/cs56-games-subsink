package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.io.IOException;

/**
 * Ship is the player entity. It moves back and forth on the surface of the water and can drop charges.
 */
public class Ship extends Entity {
	private int health = 1;
	private boolean spawning = false;
	private boolean spawnLeft = false;
	Explosion explosion;

	double splashTimer = 0;
	boolean isSplashed = false;
	double[] splashLocation = {0,0};

	/**
	 * Construct a new ship at the given initial position
	 *
	 * @param x	The initial horizontal position in pixels
	 * @param y	The initial vertical position in pixels
	 */
	public Ship(double x, double y) {
		super(x, y-26, 166, 31);
	}

	/**
	 * Update the ship with respect to the world.
	 * <p>
	 * If it is about to go off the side of the screen, it will stop.
	 * If it needs to spawn a charge, it will do so.
	 *
	 * @param world	The encompassing World
	 * @param time	The time since the last update in seconds
	 */
	public void update(World world, double time) {
		if (speedX < 0 && x < 0) {
			speedX = 0;
			x = 0;
		} else if (speedX > 0 && x + width > world.getWidth()) {
			speedX = 0;
			x = world.getWidth() - width;
		}


		if (isSplashed){
			Splash s;
			if (spawnLeft){
				s = new Splash(splashLocation[0] - 20, splashLocation[1] + 15);
			}else{
				s = new Splash(splashLocation[0] + 135, splashLocation[1] + 15);
			}
			world.spawn(s);
			if (splashTimer < 0){
				isSplashed = false;
				}
			s.destroy();
			splashTimer -= time;
		}

		if (spawning) {
			world.spawn(new DepthCharge(spawnLeft ? x : x + 155, y + 15));
			spawning = false;

		}

			super.update(world, time);

	}

	/**
	 * Register the death of the player by causing a game over.
	 *
	 * @param world	The encompassing World
	 */
	public void finalize(World world) {

		world.gameOver();
	}

	/**
	 * Spawn a new charge off one of the sides of the ship.
	 *
	 * @param spawnLeft	Whether we are spawning off the left side or not
	 */
	public void spawnCharge(boolean spawnLeft) {
		spawning = true;
		this.spawnLeft = spawnLeft;
		explosion = new Explosion();
		explosion.playDropChargeSound();
		splashTimer = .5;
		isSplashed = true;
		splashLocation[0] = x;
		splashLocation[1] = y;

	}

	/**
	 * Accelerate the ship to the side in units of 10 px/sec, with a maximum of 100 px/sec.
	 *
	 * @param accelerateLeft	Whether we are accelerating to the left or not
	 */
	public void accelerate(boolean accelerateLeft) {
		if (accelerateLeft) {
			if (speedX > -100) {
				speedX -= 10;
			}
		} else {
			if (speedX < 100) {
				speedX += 10;
			}
		}
	}

	/**
	 * Deal one point of damage to the ship. After losing all its health it will sink.
	 */
	public void damage() {
		health--;
		explosion = new Explosion();
		explosion.playExplosionSound();

		if (health == 0) {
			destroy();

		}
	}

	/**
	 * Interact with other entities.
	 * If the ship touches a height charge it will be damaged and the charge will explode.
	 *
	 * @param other	The entity with which to check interaction
	 */
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
