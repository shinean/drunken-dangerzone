package com.djavorszky.display;

import com.djavorszky.asset.player.Player;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class MainWindow {

	
	private int width, height;
	private boolean debug = false;
	
	
	public MainWindow(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		if (debug) {
			System.out.println("MainWindow initialised");
		}
		
	}



	public void startDisplay() {
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}

        Player p = new Player(150,150,20,20);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0,800,0,600,1,-1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

		while (!Display.isCloseRequested()) {
			
			// TODO logic
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


            p.playerInput();
            p.drawViewCone();
            p.drawPlayer();


            Display.update();

		}
		
		Display.destroy();
			
	}

	public boolean getDebug() {
		return this.debug;
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	
	public static void main(String[] args) {
		
		MainWindow mw = new MainWindow(800,600);
		mw.startDisplay();
		
	}
	
}
