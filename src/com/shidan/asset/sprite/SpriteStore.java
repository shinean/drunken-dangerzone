package com.shidan.asset.sprite;

import java.util.HashMap;

import com.shidan.core.exception.NoSpriteFoundException;
import com.shidan.core.exception.SpriteCreationFailedException;
import com.shidan.display.MainWindow;
/**
 * 
 *  SpriteStore to grab a new sprite. Returns one from memory if already loaded, a new one otherwise.
 * 
 * @author Dániel Jávorszky
 *
 */

public class SpriteStore {

	private static HashMap<String, Sprite> urlSpriteCache = new HashMap<String, Sprite>();
	private static HashMap<Integer, Sprite> idSpriteCache = new HashMap<Integer, Sprite>();
	
	public static Sprite get(String url) {
		
		if (urlSpriteCache.containsKey(url)) {
			
			return urlSpriteCache.get(url);
		} else {
			
			try {
				Sprite sprite = new Sprite();
				
				sprite.setImage(url);
				
				urlSpriteCache.put(url, sprite);
				idSpriteCache.put(sprite.getSpriteId(), sprite);
				
				return sprite;
				
			} catch (SpriteCreationFailedException scfe) {
				if (MainWindow.getDebug()) {
					scfe.printStackTrace();
				}
				
				return null;
			}
			
		}
		
	}
	
	public static Sprite get(int spriteId) {
		
		if (idSpriteCache.containsKey(spriteId)) {
			
			return idSpriteCache.get(spriteId);
		} else {
			
			try {
				String url = findUrlById(spriteId);
				
				return SpriteStore.get(url);
			} catch (NoSpriteFoundException nsfe) {
				if (MainWindow.getDebug()) {
					nsfe.printStackTrace();
				}
			
				return null;
			}
		
		}
		
	}
	
	public static int findIdByUrl(String url) throws NoSpriteFoundException {
		
		Sprite sprite = urlSpriteCache.get(url);
		
		if (sprite != null) {
			
			return sprite.getSpriteId();
		} else {
		
			throw new NoSpriteFoundException();
		}
		
	}
	
	public static String findUrlById(int spriteId) throws NoSpriteFoundException {
		
		Sprite sprite = idSpriteCache.get(spriteId);
		
		if (sprite != null) {
			
			return sprite.getUrl();
		} else {
		
			throw new NoSpriteFoundException();
		}
	}
	
	
	
}
