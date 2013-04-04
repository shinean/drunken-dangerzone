package com.shidan.display;

import java.util.ArrayList;

import com.shidan.asset.player.Player;
import com.shidan.asset.shader.ShaderLoader;
import com.shidan.asset.sprite.Sprite;
import com.shidan.asset.sprite.SpriteStore;
import com.shidan.core.Props;
import com.shidan.core.exception.NoSpriteFoundException;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

/**
 * MainWindow class. Center of all. 
 * 
 * @author Dániel Jávorszky
 * @author Sándor Juhász
 * 
 */

public class MainWindow {

    long lastFrame;
	private int width, height;
	private static boolean debug = false;
	
	
	public MainWindow(int width, int height) {
		
		this.width = width;
		this.height = height;

}

	public void startDisplay() {
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		MainWindow.debug = true;
		

        Sprite playerSprite = SpriteStore.fetch("testSprite");
        
		
		Player p = new Player(playerSprite, 150,150,20,20);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0,Props.DISPLAY_WIDTH,0,Props.DISPLAY_HEIGHT,1,-1);
        glMatrixMode(GL_MODELVIEW);
        
        //Sprite player = SpriteStore.get("/home/jdaniel/Pictures/dafuq.png");
        
        if (p != null);	// needed, because if the sprite could not be read, SpriteStore returns null.

		while (!Display.isCloseRequested()) {
            int delta = getDelta();
			// TODO logic
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            p.processInput(delta);
            p.drawViewCone();
            p.drawAsset();


            Display.update();
            Display.sync(60);
		}
		
		Display.destroy();
			
	}

	public static boolean getDebug() {
		return MainWindow.debug;
	}
	
	public static void setDebug(boolean debug) {
		MainWindow.debug = debug;
	}

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }
	
	public static void main(String[] args) {
		
		MainWindow mw = new MainWindow(Props.DISPLAY_WIDTH,Props.DISPLAY_HEIGHT);
		mw.startDisplay();
		
	}
	
}
