package com.shidan.asset.objects;

import static org.lwjgl.opengl.GL11.*;

import com.shidan.asset.Asset;
import com.shidan.asset.sprite.Sprite;

/**
 * 
 * @author Dániel Jávorszky
 *
 */

public class GenericObject extends Asset {

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
	
}
