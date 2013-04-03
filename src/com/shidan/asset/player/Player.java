package com.shidan.asset.player;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;

import com.shidan.asset.Asset;
import com.shidan.asset.modifier.Moveable;
import com.shidan.asset.sprite.Sprite;


/**
 * User controller class
 *
 * @author Sándor Juhász
 * @author Dániel Jávorszky
 */
public class Player extends Asset implements Moveable {

    private float x;
    private float y;
    private float width;
    private float height;
    private double coneDistance;
    private double coneChoke;
    private float speed;
    
    private Sprite sprite;


    public Player(Sprite sprite, float x,float y,float width,float height) {
        this.sprite = sprite;    	
    	this.x = x;
        this.y = y;
        this.speed = 0.1f;
        this.width = width;
        this.height = height;
        this.coneDistance = 200d;
        this.coneChoke = 100d;

    }

    /**
     * Draws a player quad (TODO: replace with an image)
     */
    public void drawAsset(Sprite sprite, float x, float y, float width, float height) {
        try {

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
    		
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getMouseX() {
        return Mouse.getX();
    }

    private int getMouseY() {
        return Mouse.getY();
    }

    public void drawViewCone() {
        try {
            GL11.glColor3f(0.5f,0.0f,0.0f);
            double actX = x + (width / 2);
            double actY = y + (height / 2);

            GL11.glLoadIdentity();
            GL11.glPushMatrix();

            int mouseX = getMouseX();
            int mouseY = getMouseY();
            double x = mouseX - actX;
            double y = mouseY - actY;

            double distance = Math.sqrt(x*x+y*y);

            double x1 = (x / distance) * coneDistance + actX;
            double y1 = (y / distance) * coneDistance + actY;



            GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex2d(actX,actY);
                GL11.glVertex2d(x1,y1);
            GL11.glEnd();

            double angle = Math.sin(y1 / coneDistance);

            double x2 = (x / distance) * (coneChoke / 2);
            double y2 = (y / distance) * (coneChoke / 2);

            double perpX1 = y2 + x1;
            double perpY1 = -x2 + y1;

            double perpX2 = -y2 + x1;
            double perpY2 = x2 + y1;

            GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex2d(x1,y1);
                GL11.glVertex2d(perpX1,perpY1);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex2d(x1,y1);
                GL11.glVertex2d(perpX2,perpY2);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex2d(actX,actY);
                GL11.glVertex2d(perpX1,perpY1);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex2d(actX,actY);
                GL11.glVertex2d(perpX2,perpY2);
            GL11.glEnd();

            GL11.glPopMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawAsset() {
    	
    	drawAsset(sprite, x, y, width, height);
  
    }
    
    
    
    /**
     * Player controls  (TODO: watch for window sides)
     */
    public void processInput() {
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                this.x -= speed;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                this.x += speed;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                this.y += speed;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                this.y -= speed;
            }
    }


}
