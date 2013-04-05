package com.shidan.asset;

import java.awt.Color;

import com.shidan.asset.sprite.Sprite;
import static org.lwjgl.opengl.GL11.*;


/**
 * Abstract class Asset. Should be the superclass of all other assets.
 * 
 * @author Dániel Jávorszky
 *
 */

public abstract class Asset {
	
	 protected float x;
	 protected float y;
	 protected float width;
	 protected float height;
	 
	 protected float speed;


	public void drawAsset(Sprite sprite, float x, float y, float width, float height) {
		
		glEnable(GL_TEXTURE_2D);

		glPushMatrix();
		glColor3f(1f, 1f, 1f);
		glBindTexture(GL_TEXTURE_2D, sprite.getTextureId());
		glBegin(GL_QUADS);
			glTexCoord2f(0, 1);
			glVertex2f(x, y);
	
			glTexCoord2f(1, 1);
			glVertex2f(x + width, y);
	
			glTexCoord2f(1, 0);
			glVertex2f(x + width, y + height);
	
			glTexCoord2f(0, 0);
			glVertex2f(x, y + height);
		glEnd();
		glPopMatrix();

	}
	
	public void drawColorAsset(int x, int y, int width, int height, Color color) {
		 glPushMatrix();
         glColor3f(color.getRed(), color.getGreen(), color.getBlue());
         glBegin(GL_QUADS);
             glVertex2f(x,y);
             glVertex2f(x+width,y);
             glVertex2f(x+width,y+height);
             glVertex2f(x,y+height);
         glEnd();
         glPopMatrix();
	}

	public void drawColorAsset(int x, int y, int width, int height, float red, float green, float blue) {
		Color color = new Color(red, green, blue);
		drawColorAsset(x, y, width, height, color);
	}
	
}
