package com.djavorszky.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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
		
		while (!Display.isCloseRequested()) {
			
			// TODO logic
			
			
			Display.update();
			Display.sync(60);
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
		
		MainWindow mw = new MainWindow(1280,720);
		mw.startDisplay();
		
	}
	
}
