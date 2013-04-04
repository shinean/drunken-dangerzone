package com.shidan.asset.modifier;

import com.shidan.asset.sprite.Sprite;

/**
 * Interface that is needed for objects that move in the world
 * 
 * @author Dániel Jávorszky
 *
 */

public interface Moveable {

	public void processInput(int delta);
	
	public void drawAsset(Sprite sprite, float x, float y, float width, float height);
	
}
