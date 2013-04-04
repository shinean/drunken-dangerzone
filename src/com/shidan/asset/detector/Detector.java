package com.shidan.asset.detector;

import org.lwjgl.util.vector.Vector2f;

/**
 * Collision detection class
 * @author Sándor Juhász
 */
public class Detector {
    /**
     * Collision detection inside an area (like screen)
     * @param sourceX - x coordinate of the test object
     * @param sourceY - y coordinate of the test object
     * @param sourceWidth - object width
     * @param sourceHeight - object height
     * @param targetMinWidth - area minimum width
     * @param targetMinHeight - area minimum height
     * @param targetMaxWidth - area maximum width
     * @param targetMaxHeight - area maximum height
     * @return a Vector2f class within the new x and y coordinate
     */
    public static Vector2f detectAreaCollision(float sourceX, float sourceY,
                                               float sourceWidth, float sourceHeight,
                                               float targetMinWidth, float targetMinHeight,
                                               float targetMaxWidth, float targetMaxHeight ) {
        if (sourceX < targetMinWidth) {
            sourceX = targetMinWidth;
        }
        if (sourceX > targetMaxWidth - sourceWidth) {
            sourceX = targetMaxWidth - sourceWidth;
        }
        if (sourceY < targetMinHeight) {
            sourceY = targetMinHeight;
        }
        if (sourceY > targetMaxHeight - sourceHeight) {
            sourceY = targetMaxHeight - sourceHeight;
        }
        return new Vector2f(sourceX,sourceY);
    }

    /**
     * Collision detection with two objects
     * @param sourceX - x coordinate of the test object
     * @param sourceY - y coordinate of the test object
     * @param sourceWidth - Test object width
     * @param sourceHeight - test object height
     * @param p1 - Target object's 1st position vector (upper left)
     * @param p3 - Target object's 2nd position vector (lower right)
     * @return - egyelőre csak egy színt buzizok
     */
    public static float detectObjectCollision(float sourceX, float sourceY,
                                                 float sourceWidth, float sourceHeight,
                                                 Vector2f p1,Vector2f p3) {
        float c;
        if (sourceX + sourceWidth >= p1.x && sourceX <= p3.x && sourceY + sourceHeight >= p1.y && sourceY <= p3.y) {
            //sourceX = p1.x - sourceWidth;
            //sourceY = p1.y - sourceHeight;
            c = 1;
        } else {
            c = 0;
        }
        return c;
    }
}
