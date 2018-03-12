package edu.ucsb.cs56.projects.games.subsink;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * The top level class; the java entry point and the game's main window.
 */
public class Main extends JFrame {
	private World w;

	public static void main(String[] args) {
		List<String> startMenuItems = Arrays.asList("Start Game", "High Scores", "Exit");
		Screen screen = new Screen(startMenuItems);
	}
}
