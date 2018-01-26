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
	private Ship player;
	private Timer timer;

	private int width;
	private int height;
	private int waterHeight;
	private int score;
	private int maxSubs;
	private double spawnTimer;
	private int msPerFrame;
	private boolean locked = false;
	private int chargeStock = 4;

	public World(int width, int height) {
		this.width = width;
		this.height = height;
		this.waterHeight = 50;

		setDoubleBuffered(true);
		setFocusable(true);

		addKeyListener(new MyListener());
		entities = new ArrayList<Entity>();
		score = 0;
		maxSubs = 2;
		spawnTimer = 5;
		msPerFrame = 30;
	}

	public void run() {
		timer = new Timer(msPerFrame, this);
		timer.start();
		player = new Ship(50, waterHeight);
		spawn(player);
		player.accelerateRight();
	}

	public void actionPerformed(ActionEvent e) {
		if (locked) {
			System.err.println("[!] two frames at once?");
			return;
		}
		locked = true;
		double frameTime = (double)msPerFrame / 1000;

		for (Entity e1 : entities) {
			for (Entity e2 : entities) {
				if (e1 != e2) {
					e1.interact(e2);
				}
			}
		}
		for (Entity e1 : (ArrayList<Entity>)entities.clone()) {
			e1.update(this, frameTime);
		}

		cull(entities);

		if (player.isDestroyed()) {
			gameOver();
		}

		if (score > maxSubs * maxSubs) {
			maxSubs++;
		}

		if (countSubs() < maxSubs) {
			spawnTimer -= frameTime;
			if (spawnTimer <= 0) {
				spawnTimer = 4;
				spawn(new Sub(
					Math.random() < 0.5 ? -60 : width,
					Math.random() * (height - waterHeight - 75) + waterHeight + 25,
					Math.random() * 50 + 10,
					Math.random() * 10 + 10));
			}
		}

		repaint();
		locked = false;
	}

	public void gameOver() {
		System.exit(0);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.CYAN);
		g2d.fillRect(0, 0, width, waterHeight);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, waterHeight, width, height - waterHeight);

		for (int health = player.getHealth(), cx = width - 20; health > 0; health--, cx -= 20) {
			g2d.drawImage(ImageLoader.get("img/height_charge.png"), cx, 10, null);
		}

		for (int charge = chargeStock, cx = 10; charge > 0; charge--, cx += 20) {
			g2d.drawImage(ImageLoader.get("img/depth_charge.png"), cx, 10, null);
		}

		g2d.setColor(Color.BLACK);
		g2d.drawString("Score: " + new Integer(score).toString(), width/2 - 30, 15);

		for (Entity e : entities) {
			e.paint(g2d);
		}
		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}

	public int countSubs() {
		int out = 0;
		for (Entity e : entities) {
			if (e instanceof Sub) {
				out++;
			}
		}
		return out;
	}

	public void spawn(Entity e) {
		entities.add(e);
	}

	public void cull(ArrayList<Entity> list) {
		Iterator<Entity> i = list.iterator();
		while (i.hasNext()) {
			Entity e = i.next();
			if (e.isDestroyed()) {
				i.remove();

				if (e instanceof Sub) {
					score++;
				} else if (e instanceof DepthCharge) {
					chargeStock++;
				}
			}
		}
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getWaterHeight() { return waterHeight; }

	class MyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.accelerateLeft();
					break;
				case KeyEvent.VK_RIGHT:
					player.accelerateRight();
					break;
				case KeyEvent.VK_Z:
					if (chargeStock > 0) {
						player.spawn(true);
						chargeStock--;
					}
					break;
				case KeyEvent.VK_X:
					if (chargeStock > 0) {
						player.spawn(false);
						chargeStock--;
					}
					break;
			}
		}
	}
}
