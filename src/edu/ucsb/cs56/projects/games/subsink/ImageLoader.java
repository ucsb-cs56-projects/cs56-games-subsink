package edu.ucsb.cs56.projects.games.subsink;

import java.util.HashMap;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.net.URL;

public class ImageLoader {
	public static HashMap<String, Image> cache = new HashMap<String, Image>();

	public static Image get(String name) {
		name = "assets/" + name;
		if (cache.containsKey(name)) {
			return cache.get(name);
		}

		URL url = ImageLoader.class.getResource(name);
		if (url == null) {
			url = ImageLoader.class.getResource("assets/broken_image.png");
			if (url == null) {
				System.err.println("Critical error! Asset loading is BROKEN");
				System.exit(1);
			}
		}
		try {
			Image img = ImageIO.read(url);
			ImageLoader.cache.put(name, img);
			return img;
		} catch (Exception e) {
			System.err.println("Critical error! Broken image " + name);
			System.exit(1);
			return null;
		}
	}
}
