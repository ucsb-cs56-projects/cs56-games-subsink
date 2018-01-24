package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;

public class Main extends JFrame {
	private World w;

	public Main() {
		setTitle("Subsink");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(620, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		w = new World(getWidth(), getHeight());
		add(w);
	}

	public void run() {
		w.run();
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}
}
