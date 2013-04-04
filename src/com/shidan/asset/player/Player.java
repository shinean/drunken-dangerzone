package com.shidan.asset.player;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.shidan.asset.draw.Primitives;
import com.shidan.asset.shader.ShaderLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


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
            glColor3f(0f,0.0f,0.0f);
            // calculate the square's middle point
            double ax = x + (width / 2);
            double ay = y + (height / 2);
            // gets the mouse cursor point
            int mouseX = getMouseX();
            int mouseY = getMouseY();
            // needed x and y
            double x = mouseX - ax;
            double y = mouseY - ay;
            // mouse and square distance
            double distance = Math.sqrt(x * x + y * y);
            // first line of the viewcone (square -> cursor - cone distance vector)
            double x1 = (x / distance) * coneDistance + ax;
            double y1 = (y / distance) * coneDistance + ay;
            // perpendicular line (half of the cone max length)
            double x2 = (x / distance) * (coneChoke / 2);
            double y2 = (y / distance) * (coneChoke / 2);
            // calculate the first perpendicular line's points
            double bx = y2 + x1;
            double by = -x2 + y1;
            // calculate the second perpendicular line's points
            double cx = -y2 + x1;
            double cy = x2 + y1;
            // draw all this shit
            glLoadIdentity();
            glPushMatrix();
            // Player to cursor line
            Primitives.line(ax,  ay, x1, y1,"0,0,0","0,0,0");
            // Perpeticular line 1
            Primitives.line(x1,  y1, bx, by,"0,1,0","0,0,1");
            // Perpeticular line 2
            Primitives.line(x1,  y1, cx, cy,"0,1,0","0,0,1");
            // Side line 1
            Primitives.line(ax,  ay, bx, by,"0,0,0","0,1,0");
            // Side line 2
            Primitives.line(ax,  ay, cx, cy,"0,0,0","0,1,0");
            glPopMatrix();
            glPushMatrix();
            // Orientation indicator
            glColor3f(0f,1.0f,0.0f);
            Primitives.circle(x1,y1,5,10);
            glPopMatrix();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getViewConeArea() {
        return Primitives.triangleArea(coneChoke,coneDistance);
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
