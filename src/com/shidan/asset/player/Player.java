package com.shidan.asset.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;


/**
 * User controller class
 *
 * @author Sándor Juhász
 */
public class Player {

    private float x;
    private float y;
    private float width;
    private float height;
    private float coneWidth;
    private float coneHeight;
    private float rotation = 0;
    private float speed;



    public Player(float x,float y,float width,float height) {
        this.x = x;
        this.y = y;
        this.speed = 0.1f;
        this.width = width;
        this.height = height;
        this.coneWidth = 200f;
        this.coneHeight = 50f;
        this.rotation = 0.1f;
    }

    /**
     * Draws a player quad (TODO: replace with an image)
     */
    public void drawPlayer() {
        try {
            GL11.glPushMatrix();
            GL11.glColor3f(0.5f, 0.5f, 1.0f);

            GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(x,y);
                GL11.glVertex2f(x+width,y);
                GL11.glVertex2f(x+width,y+height);
                GL11.glVertex2f(x,y+height);
            GL11.glEnd();
            GL11.glPopMatrix();
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
            float actX = x + (width / 2);
            float actY = y + (height / 2);
            Vector2f playerVector = new Vector2f(actX,actY);
            Vector2f mouseVector = new Vector2f(getMouseX(),getMouseY());
            GL11.glLoadIdentity();
            GL11.glPushMatrix();

            GL11.glBegin(GL11.GL_LINE_STRIP);
                GL11.glVertex2f(actX,actY);
                GL11.glVertex2f(getMouseX(), getMouseY());

            GL11.glEnd();
            GL11.glPopMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Player controls  (TODO: watch for window sides)
     */
    public void playerInput() {
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
