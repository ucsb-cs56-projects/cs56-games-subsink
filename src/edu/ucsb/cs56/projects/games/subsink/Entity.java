package edu.ucsb.cs56.projects.games.subsink;

import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;

public abstract class Entity extends Rectangle2D.Double {
	protected double speedX = 0;
	protected double speedY = 0;
	private boolean gone = false;

	public Entity(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public void interact(Entity other) { }

	public void update(World world, double time) {
		x += speedX * time;
		y += speedY * time;
	}

	public void destroy() {
		gone = true;
	}

	public boolean isDestroyed() {
		return gone;
	}

	public abstract void paint(Graphics2D g);
}
