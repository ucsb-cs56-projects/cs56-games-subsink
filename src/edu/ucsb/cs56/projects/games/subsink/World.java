package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class World extends JPanel implements ActionListener {
	private ArrayList<Entity> entities;
	private ArrayList<Sub> subs;
	private Ship player;
	private Timer timer;

	private int width;
	private int height;
	private int waterHeight;

	public World(int width, int height) {
		this.width = width;
		this.height = height;
		this.waterHeight = 50;

		timer = new Timer(16, this);

		spawn(new Ship(50, waterHeight));
	}

	public void actionPerformed(ActionEvent e) {
		for (Entity e1 : entities) {
			for (Entity e2 : entities) {
				if (e1 != e2) {
					e1.interact(e2);
				}
			}
		}
		for (Entity e1 : entities) {
			e1.update(this, 0.016);
		}
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.CYAN);
		g2d.fillRect(0, 0, width, waterHeight);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, waterHeight, width, height - waterHeight);

		// TODO draw score

		for (Entity e : entities) {
			e.paint(g2d);
		}
	}

	public void spawn(Entity e) {
		entities.add(e);
		if (e instanceof Sub) {
			subs.add((Sub)e);
		}
		if (e instanceof Ship) {
			player = (Ship)e;
		}
	}

	public static void cull(ArrayList<Entity> list) {
		Iterator<Entity> i = list.iterator();
		while (i.hasNext()) {
			Entity e = i.next();
			if (e.isDestroyed()) {
				i.remove();
			}
		}
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getWaterHeight() { return waterHeight; }
}
