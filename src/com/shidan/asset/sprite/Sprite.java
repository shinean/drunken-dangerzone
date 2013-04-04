package com.shidan.asset.sprite;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import com.shidan.core.exception.SpriteCreationFailedException;
import com.shidan.display.MainWindow;
import com.shidan.service.CounterService;

/**
 * 
 * Sprite class for the win.
 * 
 * @author Dániel Jávorszky
 * 
 */

public class Sprite {

	// TODO: Implement the a way to tell what type of image this is (png, jpg,
	// etc...)

	private BufferedImage image;
	private int width;
	private int height;
	private int spriteId;
	private String url;
	private int textureId;
	private int type;
	
	public boolean buildSprite(String url) throws SpriteCreationFailedException {

		try {
			File file = new File(url);
			this.image = ImageIO.read(file);

			this.width = image.getWidth();
			this.height = image.getHeight();
			this.url = url;
			this.spriteId = CounterService.incrementSprite();
			this.textureId = getTextureId(image);
			this.type = SpriteConstants.LOADED;
			
		} catch (IOException e) {
			if (MainWindow.getDebug()) {
				e.printStackTrace();
			}

			throw new SpriteCreationFailedException();
		}

		return true;
	}

	public boolean buildSprite(BufferedImage image, int width, int height,
			String name) {
		
		this.image = image;
		this.width = width;
		this.height = height;
				
		this.spriteId = CounterService.incrementSprite();
		this.textureId = getTextureId(image);
		this.type = SpriteConstants.GENERATED;
		
		this.url = "blob";
		
		return true;	
	}

	private int getTextureId(BufferedImage image) {

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * SpriteConstants.BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
															// Only for RGBA
			}
		}

		buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:

		int textureID = glGenTextures(); // Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); // Bind texture ID

		// Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		// Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(),
				image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// Return the texture ID so we can bind it later again
		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSpriteId() {
		return spriteId;
	}

	public String getUrl() {
		return url;
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public int getTextureId() {
		return this.textureId;
	}
	
	public int getType() {
		return this.type;
	}

}
