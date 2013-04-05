package com.shidan.asset.objects;

import static org.lwjgl.opengl.GL11.*;

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
     * @param y2 - y of the second point
     * @param vertColor1 - rgb string ("0,1,0")
     * @param vertColor2 - rgb string
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

    /**
     * String to double
     * @param s - String
     * @return - double
     */
    public static double castStringToDouble(String s) {
        return new Double(s).doubleValue();
    }

    /**
     * Returns a triangle's area
     * @param width - sweeping line width (fingom nincs, hogy mondják az átfogót angolul)
     * @param height - triangle's height
     * @return - area
     */
    public static double triangleArea(double width, double height) {
        return width * height / 2;
    }

    /**
     * Draws a triangle
     * @param x1 - x1 coordinate
     * @param y1 - y1 coordinate
     * @param x2 - x2 coordinate
     * @param y2 - y2 coordinate
     * @param x3 - x3 coordinate
     * @param y3 - y3 coordinate
     */
    public static void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        glBegin(GL_TRIANGLES);
            glVertex2d(x1,y1);
            glVertex2d(x2,y2);
            glVertex2d(x3,y3);
        glEnd();
    }

    /**
     * Draws a quad
     * @param x1 - x1 coordinate
     * @param y1 - y1 coordinate
     * @param x2 - x2 coordinate
     * @param y2 - y2 coordinate
     * @param x3 - x3 coordinate
     * @param y3 - y3 coordinate
     * @param x4 - x4 coordinate
     * @param y4 - y4 coordinate
     */
    public static void quad(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        glBegin(GL_QUADS);
            glVertex2d(x1,y1);
            glVertex2d(x2,y2);
            glVertex2d(x3,y3);
            glVertex2d(x4,y4);
        glEnd();
    }
    
    
    public static void texturedQuad(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        glBegin(GL_QUADS);
            glVertex2d(x1,y1);
            glVertex2d(x2,y2);
            glVertex2d(x3,y3);
            glVertex2d(x4,y4);
        glEnd();
    }
    

    /**
     * Draws a circle
     * @param cx - middle x
     * @param cy - middle y
     * @param r  - radius
     * @param num_segments - precision
     */
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
