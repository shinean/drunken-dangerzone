package com.shidan.asset.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.shidan.display.MainWindow;

/**
 * 
 * Sprite class for the win.
 * 
 * @author jdaniel
 *
 */

public class Sprite {

	private BufferedImage image;
	private int width;
	private int height;
	private int type;
	private String url;
	
	public boolean setImage(String url) {
		
		try {
			File file = new File(url);
			image = ImageIO.read(file);
			
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.type = image.getType();
			this.url = url;
			
		
		} catch (IOException e) {
			if (MainWindow.getDebug()) {
				e.printStackTrace();		
			} 
			
			return false;
		}
			
		return true;
	}
	
	public boolean setImage(BufferedImage image, int width, int height, int type, String url) {
			
		File file = new File(url);
		
		if (! file.exists()) {
			
			try {
				file.createNewFile();
				ImageIO.write((RenderedImage) image, "test.png", file);
				
			} catch(IOException e) {
				if (MainWindow.getDebug()) {
					e.printStackTrace();
				}
				return false;
			}
			
			
			this.image = image;
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.type = image.getType();
			this.url = url;

			return true;
			
		} else {
			return setImage(url);
		}
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getType() {
		return type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}

}
