package com.shidan.asset.player;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL20.*; if we want to make shaders

import com.shidan.asset.detector.Detector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


import com.shidan.asset.Asset;
import com.shidan.asset.modifier.Moveable;
import com.shidan.asset.objects.Primitives;
import com.shidan.asset.sprite.Sprite;
import com.shidan.core.Props;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;


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
    private int delta;

    private double orientationX = 0;
    private double orientationY = 0;

    public Player(Sprite sprite, float x, float y, float width, float height) {
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


            float ax = x + (width / 2);
            float ay = y + (height / 2);
            double d = Math.sqrt(Math.pow(orientationX - ax,2) + Math.pow(orientationY - ay,2));
            HashMap<String,Object> info = Primitives.circleRadius(ax, ay, d, 60, 0);
            ArrayList<Vector2f> v = (ArrayList<Vector2f>)info.get("vertices");
            double heightLength = Primitives.getDistance(orientationX, orientationY, v.get(0).getX(), v.get(0).getY());

            float angle = (float)(Math.sin(d/heightLength)) - 360;
            glTranslatef(ax,ay,1);
            glRotatef(angle*delta,0,0,1);
            glTranslatef(-ax, -ay, 0);
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
            glDisable(GL_TEXTURE_2D);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the flashlight's cone
     */
    public void drawViewCone() {
        try {


            glColor3f(0f, 0.0f, 0.0f);
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
            glPushMatrix();
            // Player to cursor line
            Primitives.line(ax, ay, x1, y1, "1,1,1", "1,1,1");
            // Perpeticular line 1
            Primitives.line(x1,  y1, bx, by,"1,1,1", "1,1,1");
            // Perpeticular line 2
            Primitives.line(x1,  y1, cx, cy,"1,1,1", "1,1,1");
            // Side line 1
            Primitives.line(ax,  ay, bx, by,"1,1,1", "1,1,1");
            // Side line 2
            Primitives.line(ax,  ay, cx, cy,"1,1,1", "1,1,1");
            glPopMatrix();

            glPushMatrix();
            // Orientation indicator
            glColor3f(0f,1.0f,0.0f);
            Primitives.circle(x1,y1,5,10);
            glPopMatrix();
            testCollidableObject();
            orientationX = x1;
            orientationY = y1;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws a player sprite
     */
    public void drawAsset() {
        drawAsset(sprite, x, y, width, height);
    }

    /**
     * Player controls
     */
    public void processInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.x -= speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.x += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.y += speed * delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.y -= speed * delta;
        }

        Vector2f collide = Detector.detectAreaCollision(this.x, this.y, this.width, 
        		this.height, 0, 0, Props.DISPLAY_WIDTH, Props.DISPLAY_HEIGHT);
        this.x = collide.x;
        this.y = collide.y;
        testColor = Detector.detectObjectCollision(
                this.x,
                this.y,
                this.width,
                this.height,
                new Vector2f(100,300),
                new Vector2f(200,400));
    }

    /**
     * Returns the flashlight's area
     * @return double
     */
    public double getViewConeArea() {
        return Primitives.triangleArea(coneChoke,coneDistance);
    }

    /**
     * Returns the mouse x coordinate
     * @return
     */
    private int getMouseX() {
        return Mouse.getX();
    }

    /**
     * Returns the mouse y coordinate
     * @return
     */
    private int getMouseY() {
        return Mouse.getY();
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }



    /** Only for testing **/
    float testColor;
    public void testCollidableObject() {
        glEnable(GL_COLOR_MATERIAL);
        glColorMaterial(GL_FRONT_AND_BACK,GL_AMBIENT_AND_DIFFUSE);
        glPushMatrix();
        glNormal3f(0, 0, 1.0f);
        // define colour
        glColor3f(1.0f, testColor, 1.0f);
        Primitives.quad(100,300,200,300,200,400,100,400);
        glPopMatrix();
        glDisable(GL_COLOR_MATERIAL);
    }

}
