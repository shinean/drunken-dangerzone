package com.shidan.asset.draw;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import org.lwjgl.util.vector.Vector2f;

/**
 * Primitive drawing class
 *
 * @author Sándor Juhász
 */
public class Primitives {
    /**
     * Draws a line
     * @param x1 - x of the first point
     * @param y1 - y of the first point
     * @param x2 - x of the second point
     * @param y2   y of the second point
     */
    public static void line(double x1, double y1, double x2, double y2, String vertColor1, String vertColor2) {
        String[] v1 = vertColor1.split(",");
        String[] v2 = vertColor2.split(",");
        glBegin(GL_LINE_STRIP);
            glColor3d(castStringToDouble(v1[0]),castStringToDouble(v1[1]),castStringToDouble(v1[2]));
            glVertex2d(x1,y1);
            glColor3d(castStringToDouble(v2[0]),castStringToDouble(v2[1]),castStringToDouble(v2[2]));
            glVertex2d(x2,y2);
        glEnd();
    }

    public static double castStringToDouble(String s) {
        return new Double(s).doubleValue();
    }

    public static double triangleArea(double width, double height) {
        return width * height / 2;
    }

    public static void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        glBegin(GL_TRIANGLES);
            glVertex2d(x1,y1);
            glVertex2d(x2,y2);
            glVertex2d(x3,y3);
        glEnd();
    }

    public static void quad(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        glBegin(GL_QUADS);
            glVertex2d(x1,y1);
            glVertex2d(x2,y2);
            glVertex2d(x3,y3);
            glVertex2d(x4,y4);
        glEnd();
    }

    public static void circle(double cx, double cy, double r, int num_segments) {
        glBegin(GL_LINE_LOOP);
        for(int i = 0; i < num_segments; i++){
            double theta = 2.0f * Math.PI * i / num_segments;
            double x = r * Math.cos(theta);
            double y = r * Math.sin(theta);
            glVertex2d(x + cx, y + cy);

        }
        glEnd();
    }

}
