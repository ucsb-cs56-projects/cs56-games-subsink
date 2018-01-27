package edu.ucsb.cs56.projects.games.subsink;

import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

/**
 * Entity is the base class for all gameplay entities.
 * It provides mechanisms for motion, interaction, and drawing.
 * All measurements are performed as doubles even though they are pixel-based in order to simplify interactions and fractional motion.
 */
public abstract class Entity extends Rectangle2D.Double {
	/**
	 * The horizontal speed in pixels per second
	 */
	protected double speedX = 0;
	/**
	 * The vertical speed in pixels per second
	 */
	protected double speedY = 0;
	private boolean gone = false;

	/**
	 * All entities are rectangles, so to initialize one you must provide its position and size.
	 *
	 * @param x		The initial x position in pixels
	 * @param y		The initial y position in pixels
	 * @param width		The initial width in pixels
	 * @param height	The initial height in pixels
	 */
	public Entity(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	/**
	 * Interact with another entity, by default doing nothing.
	 * This will be called with every other entity in the game every single frame.
	 * You should override this function to check if the other entity is a kind of entity you know how to deal with, and if so cast it to its appropriate type.
	 * <p>
	 * For checking if two entities are touching, you may want to use Rectangle.intersects(other).
	 *
	 * @param other	The entity to interact with
	 */
	public void interact(Entity other) { }

	/**
	 * Update the entity's state for a single frame, making any necessary changes to the encompassing world.
	 * The default implementation just updates position given speed, so you should make a super call if you want motion based on speed.
	 *
	 * @param world	The encompassing World
	 * @param time	The time since the last update in seconds
	 */
	public void update(World world, double time) {
		x += speedX * time;
		y += speedY * time;
	}

	/**
	 * Register the disappearance of the entity.
	 * You can override this to change the state of the World on the death of the entity.
	 *
	 * @param world	The encompassing World
	 */
	public void finalize(World world) { }

	/**
	 * Mark this entity as destroyed.
	 * You should not override this method.
	 */
	public void destroy() {
		gone = true;
	}

	/**
	 * Check if the entity has been destroyed.
	 * You should not override this method.
	 */
	public boolean isDestroyed() {
		return gone;
	}

	/**
	 * Produce the entiteis's graphics according to its curent state.
	 *
	 * @param g	The current graphics context
	 */
	public abstract void paint(Graphics2D g);
}
