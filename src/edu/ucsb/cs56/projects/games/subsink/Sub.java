package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.io.IOException;

/**
 * Subs are the enemies in SubSink.
 * They patrol the ocean and launch height charges to attack submarine destroyers.
 */
public class Sub extends Entity {
	private double spawnCountdown = 0;
	private double spawnFrequency;
	public Explosion explosion;
	private boolean isExploded = false;
	double explosionTimer = 0.5;

	/**
	 * Construct a new Sub with the given initial parameters.
	 *
	 * @param x		The initial x position in pixels
	 * @param y		The initial y position in pixels
	 * @param speed		The sub's horizontal speed. This should be a positive number; its sign will be chosen based on the x position such that the sub is moving onto the screen if it is offscreen.
	 * @param spawnFrequency	The average time between charge spawns
	 */
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

	/**
	 * Interact with other entities. If the sub touches a depth charge, it damages the sub and explodes the charge.
	 *
	 * @param other	The other entity with which to interact
	 */
	public void interact(Entity other) {
		if (! (other instanceof DepthCharge)) return;
		DepthCharge d = (DepthCharge)other;
		if (this.intersects(d)) {
			isExploded = true;
			explosionTimer = 0.5;
			d.explode();
			this.damage();
		}
	}


	/**
	 * Deal a point of damage to the sub, destroying it.
	 */
	public void damage() {
		explosion = new Explosion();
		explosion.playExplosionSound();
	}

	/**
	 * Update the sub with respect to the world.
	 * Subs will move back and forth at a constant speed, bouncing off the walls of the game and spawning height charges at a random interval.
	 *
	 * @param world	The encompassing World
	 * @param time	The time since the last update in seconds
	 */
	public void update(World world, double time) {
		if (speedX < 0 && x < 0) {
			speedX = -speedX;
		} else if (speedX > 0 && x + width > world.getWidth()) {
			speedX = -speedX;
		}

		spawnCountdown -= time;
		if (spawnCountdown <= 0) {
			spawnCountdown = Math.random() * 0.5 + spawnFrequency;
//			if(I_think_I_can_hit_ship) {
			world.spawn(new HeightCharge(x + 30, y - 8));
//			}
			//only reset the timer whenever you shoot a charge

		}

		if (isExploded){
			if (explosionTimer < 0){

				world.spawn(new Bubbles(x+15, y - 65));
				destroy();
			}
			explosionTimer -= time;
		}
		else {
			super.update(world, time);
		}
	}

	/**
	 * Register the death of the sub by granting one point to the player's score.
	 */
	public void finalize(World w) {
		w.giveScore(1);
	}



	public void paint(Graphics2D g) {
		double xOffset = (int) (Math.random() * 10) + x - 10;
		double yOffset = (int) (Math.random() * 10) + y - 10;
		if (isExploded) {
			g.drawImage(ImageLoader.get("img/explosion.png"), (int) xOffset, (int) yOffset, null);
		}else if ((speedX < 0)){
			g.drawImage(ImageLoader.get("img/sub.png"), (int) x, (int) y, null);
		}else{
			g.drawImage(ImageLoader.get("img/sub.png"), (int) x + 60, (int) y, -60, 10,  null);
		}
	}
}
