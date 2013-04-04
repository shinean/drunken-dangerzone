package com.shidan.asset;

import java.awt.Color;
import org.lwjgl.opengl.GL11;

import com.shidan.asset.draw.Primitives;
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
		 GL11.glPushMatrix();
         GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
         GL11.glBegin(GL11.GL_QUADS);
             GL11.glVertex2f(x,y);
             GL11.glVertex2f(x+width,y);
             GL11.glVertex2f(x+width,y+height);
             GL11.glVertex2f(x,y+height);
         GL11.glEnd();
         GL11.glPopMatrix();
	}

	public void drawColorAsset(int x, int y, int width, int height, float red, float green, float blue) {
		Color color = new Color(red, green, blue);
		drawColorAsset(x, y, width, height, color);
	}
	
}
