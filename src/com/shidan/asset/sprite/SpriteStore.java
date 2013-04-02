package com.shidan.asset.sprite;

import java.util.HashMap;
/**
 * 
 *  SpriteStore to grab a new sprite. Returns one from memory if already loaded, a new one otherwise.
 * 
 * @author Dániel Jávorszky
 *
 */

public class SpriteStore {

	private static HashMap<String, Sprite> cacheMap = new HashMap<String, Sprite>();
	
	public static Sprite get(String url) {
		
		if (cacheMap.containsKey(url)) {
			
			return cacheMap.get(url);
		} else {
			Sprite sprite = new Sprite();
			
			if (sprite.setImage(url)) {
				cacheMap.put(url, sprite);
				
				return sprite;
			} else {
				
				return null;
			}
	
		}
		
	}
	
}
