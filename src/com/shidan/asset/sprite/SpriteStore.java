package com.shidan.asset.sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.shidan.core.exception.SpriteCreationFailedException;
import com.shidan.display.MainWindow;

/**
 * 
 * SpriteStore to grab a new sprite. Returns one from memory if already loaded,
 * a new one otherwise. TODO Handle spritemaps
 *
 * Random SpriteMap generator:
 * http://img.uninhabitant.com/spritegen.html
 * 
 * 
 * @author Dániel Jávorszky
 * 
 */

public class SpriteStore {

	private static HashMap<String, Sprite> spriteStore = new HashMap<String, Sprite>();
	
	public static Sprite fetch(String name) {

		String url;
		Sprite sprite;
		
		if (spriteStore.containsKey(name)) {
			
			return spriteStore.get(name);
		} else {
			
			try {
				url = buildUrl(name);
			
				sprite = new Sprite();
				sprite.buildSprite(url);
				spriteStore.put(name, sprite);
			
				return sprite;
			} catch(SpriteCreationFailedException scfe) {
				if (MainWindow.getDebug()) {
					scfe.printStackTrace();
				}
			
				return null;
			}
		}
	}
	
	private static String buildUrl(String name) {
		
		return "img/" + name + ".png";
	}
	
	public static ArrayList<Sprite> getSpriteMapRow(String name, int heightStart, int height, int width, int iteration) {
		
		ArrayList<Sprite> spriteRow = new ArrayList<Sprite>();
		
		Sprite spriteMap = SpriteStore.fetch(name);
		
		for (int i = 0; i < iteration; i++) {
			BufferedImage bi = spriteMap.getImage().getSubimage(width * i, heightStart, width, height);
			Sprite temp = new Sprite();
			temp.buildSprite(bi, width, height, name);
			spriteRow.add(temp);
		}
		
		return spriteRow;
	}
	
	public static ArrayList<Sprite> getSpriteMapRow(String name, int heightStart, int height, int width) {
	
		Sprite s = SpriteStore.fetch(name);
		int iteration = s.getWidth() / width;
		
		return getSpriteMapRow(name, heightStart, height, width, iteration);
	}
	
}