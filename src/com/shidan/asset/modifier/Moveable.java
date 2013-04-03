package com.shidan.asset.modifier;

import com.shidan.asset.sprite.Sprite;

public interface Moveable {

	public void processInput();
	
	public void drawAsset(Sprite sprite, float x, float y, float width, float height);
	
	
}
