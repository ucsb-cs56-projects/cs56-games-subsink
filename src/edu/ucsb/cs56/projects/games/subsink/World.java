package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;

/**
 * The World is the main gameplay class that contains global game state and the game's entities.
 */
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

	private boolean paused = false;

	/**
	 * Construct a new world with the given dimensions
	 *
	 * @param width		The width of the world in pixels
	 * @param height	The height of the world in pixels
	 */
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

		player = new Ship(50, waterHeight);
		spawn(player);
		timer = new Timer(msPerFrame, this);
	}

	/**
	 * Run the main game loop.
	 * Spawns a new player and starts a timer to run the update code once per frame.
	 */
	public void run() {
		timer.start();
	}

	/**
	 * The once-per-frame callback, as per the <code>implements ActionListener</code>.
	 * Updates the game state and redraws everything.
	 *
	 * @param e	I don't care about this funny little man.
	 */
	public void actionPerformed(ActionEvent e) {
		// safety measure - this stuff is not thread safe
		if (locked) {
			System.err.println("[!] two frames at once?");
			return;
		}
		locked = true;
		update();
		repaint();
		Toolkit.getDefaultToolkit().sync(); // this REALLY forces the repaint
		locked = false;
	}

	/**
	 * Update the gameplay state for a single frame.
	 * Manages all the entities, the difficulty, and the spawning of new subs.
	 */
	public void update() {
		double frameTime = (double)msPerFrame / 1000;
		if (paused){
			return;
		}

		// perform entity interaction
		for (Entity e1 : entities) {
			for (Entity e2 : entities) {
				if (e1.isDestroyed() || e2.isDestroyed()) {
					continue;
				}
				if (e1 != e2) {
					e1.interact(e2);
				}
			}
		}

		// cull dead enemies, allow finalization (technically spawning could happen here)
		cull();

		// perform entity updates, allow spawning
		for (Entity e1 : (ArrayList<Entity>)entities.clone()) {
				e1.update(this, frameTime);

		}

		// increase difficulty at quadratic intervals
		if (score > maxSubs * maxSubs) {
			maxSubs++;
		}

		// spawn new subs if necessary
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
	}


	/**
	 * Mark the end of the game.
	 * Prints out the final score and exits the application.
	 */
	public void gameOver() {
		//save high schore
		Leaderboard highScores = new Leaderboard();
		highScores.saveScore(score);
		java.util.List<String> gameOverMenuItems = Arrays.asList("Try Again", "Exit");
		Screen gameOverScreen  = new Screen(gameOverMenuItems);

	}

	/**
	 * Draw all the graphics, including all the entities.
	 */
	public void paint(Graphics g) {

		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		// draw background
		g2d.setColor(Color.CYAN);
		g2d.fillRect(0, 0, width, waterHeight);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, waterHeight, width, height - waterHeight);

		if (paused){
			g2d.setColor(Color.BLACK);
			g2d.drawString("Game Paused", width/2 - 40, height/2);
			return;
		}

		// draw health
		for (int health = player.getHealth(), cx = width - 20; health > 0; health--, cx -= 20) {
			g2d.drawImage(ImageLoader.get("img/height_charge.png"), cx, 10, null);
		}

		// draw charge stock
		for (int charge = chargeStock, cx = 10; charge > 0; charge--, cx += 20) {
			g2d.drawImage(ImageLoader.get("img/depth_charge.png"), cx, 10, null);
		}

		// draw score
		g2d.setColor(Color.BLACK);
		g2d.drawString("Score: " + new Integer(score).toString(), width/2 - 30, 15);

		// draw entities
		for (Entity e : entities) {
			e.paint(g2d);
		}
		g.dispose();
	}

	/**
	 * Count the current number of live subs.
	 *
	 * @return The current number of live subs
	 */
	public int countSubs() {
		int out = 0;
		for (Entity e : entities) {
			if (e instanceof Sub) {
				out++;
			}
		}
		return out;
	}

	/**
	 * Cull the entity list, running the finalization handler and discarding any entities which have been destroyed.
	 */
	public void cull() {
		Iterator<Entity> i = entities.iterator();
		while (i.hasNext()) {
			Entity e = i.next();
			if (e.isDestroyed()) {
				e.finalize(this);
				i.remove();
			}
		}
	}

	// getters and simple updaters
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getWaterHeight() { return waterHeight; }
	public void giveScore(int count) { score += count; }
	public void giveStock(int count) { chargeStock += count; }
	public void spawn(Entity e) { entities.add(e); }

	/**
	 * This class handles the player's keypresses and dispatches them to the appropriate handlers.
	 */
	class MyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.accelerate(true);
					break;
				case KeyEvent.VK_RIGHT:
					player.accelerate(false);
					break;
				case KeyEvent.VK_Z:
					if (chargeStock > 0) {
						player.spawnCharge(true);
						chargeStock--;
					}
					break;
				case KeyEvent.VK_X:
					if (chargeStock > 0) {
						player.spawnCharge(false);
						chargeStock--;
					}
					break;
				case KeyEvent.VK_P:
					paused = !paused;

					break;
			}
		}
	}
}
