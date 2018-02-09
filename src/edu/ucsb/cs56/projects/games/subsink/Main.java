package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;

/**
 * The top level class; the java entry point and the game's main window.
 */
public class Main extends JFrame {
	private World w;

	public Main() {
		setTitle("Subsink");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(620, 480);

		w = new World(getWidth(), getHeight());
		add(w);

		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Start the main game loop.
	 */
	public void run() {
		w.run();
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}
}
