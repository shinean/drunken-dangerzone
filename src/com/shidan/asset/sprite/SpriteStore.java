package com.shidan.asset.sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.shidan.core.exception.NoSpriteFoundException;
import com.shidan.core.exception.SpriteCreationFailedException;
import com.shidan.display.MainWindow;

/**
 * 
 * SpriteStore to grab a new sprite. Returns one from memory if already loaded,
 * a new one otherwise. TODO Handle spritemaps
 * 
 * @author Dániel Jávorszky
 * 
 */

public class SpriteStore {

	private static HashMap<String, Sprite> urlSpriteCache = new HashMap<String, Sprite>();
	private static HashMap<Integer, Sprite> idSpriteCache = new HashMap<Integer, Sprite>();

	public static Sprite get(String url) {

		if (urlSpriteCache.containsKey(url)) {

			return urlSpriteCache.get(url);
		} else {

			try {
				Sprite sprite = new Sprite();

				sprite.setImage(url);

				urlSpriteCache.put(url, sprite);
				idSpriteCache.put(sprite.getSpriteId(), sprite);

				return sprite;

			} catch (SpriteCreationFailedException scfe) {
				if (MainWindow.getDebug()) {
					scfe.printStackTrace();
				}

				return null;
			}

		}

	}

	/**
	 * Get the sprite from sprite ID
	 * 
	 * @param spriteId
	 * @return
	 */

	public static Sprite get(int spriteId) {

		if (idSpriteCache.containsKey(spriteId)) {

			return idSpriteCache.get(spriteId);
		} else {

			try {
				String url = findUrlById(spriteId);

				return SpriteStore.get(url);
			} catch (NoSpriteFoundException nsfe) {
				if (MainWindow.getDebug()) {
					nsfe.printStackTrace();
				}

				return null;
			}

		}

	}

	/**
	 * Find out the ID of the sprite by a provided URL
	 * 
	 * @param url
	 * @return
	 * @throws NoSpriteFoundException
	 */

	public static int findIdByUrl(String url) throws NoSpriteFoundException {

		Sprite sprite = urlSpriteCache.get(url);

		if (sprite != null) {

			return sprite.getSpriteId();
		} else {

			throw new NoSpriteFoundException();
		}

	}

	/**
	 * Find out the URL of a sprite by a provided ID
	 * 
	 * @param spriteId
	 * @return
	 * @throws NoSpriteFoundException
	 */

	public static String findUrlById(int spriteId)
			throws NoSpriteFoundException {

		Sprite sprite = idSpriteCache.get(spriteId);

		if (sprite != null) {

			return sprite.getUrl();
		} else {

			throw new NoSpriteFoundException();
		}
	}

	/**
	 * Gets a part of a row from a spritemap
	 * 
	 * @param height
	 *            Height of the row
	 * @param width
	 *            Width of the individual sprites
	 * @param row
	 *            Which row. It is important to mention that 1: Rows are zero
	 *            based, and 2: if height = 10, row 2 will start at 2x10 =20px
	 *            and goes down until 30px. Use this if all the rows are exactly
	 *            the same height!
	 * @param column
	 *            How many columns you want from the row. If column is 0 or
	 *            less, it will iterate until end of image.getWidth();
	 * @return ArrayList of Sprites
	 */

	public static ArrayList<Sprite> getSpriteMapRow(Sprite sprite, int height,
			int width, int row, int column) {

		BufferedImage image = sprite.getImage();

		ArrayList<Sprite> array = new ArrayList<Sprite>();

		if (column < 1) {

			for (int i = 0; i < image.getWidth(); i += width) {
				Sprite s = new Sprite();
				try {
					s.setImage(
							image.getSubimage(i, row * height, width, height),
							width, height, null);
				} catch (SpriteCreationFailedException scfe) {
					scfe.printStackTrace();
				}

				array.add(s);
			}

			return array;

		} else {
			
			for (int i = 0; i < column; i++) {
				Sprite s = new Sprite();
				try {
					s.setImage(
							image.getSubimage(i, row * height, width, height),
							width, height, "have to think");
				} catch (SpriteCreationFailedException scfe) {
					scfe.printStackTrace();
				}

				array.add(s);
			}

			return array;
		}

	}

	/**
	 * Gets a row from a spritemap. Goes until end of row.
	 * 
	 * @param height
	 *            Height of the row
	 * @param width
	 *            Width of an individual sprite in the spritemap
	 * @param row
	 *            Which row. It is important to mention that 1: Rows are zero
	 *            based, and 2: if height = 10, row 2 will start at 2x10 =20px
	 *            and goes down until 30px. Use this if all the rows are exactly
	 *            the same height!
	 * @return
	 */

	public static ArrayList<Sprite> getSpriteMapRow(Sprite sprite, int height,
			int width, int row) {

		return getSpriteMapRow(sprite, height, width, row, 0);

	}

	/**
	 * Gets a (part of a) row from the spritemap from a specific Y location to
	 * height 'height'.
	 * 
	 * @param start
	 *            Y coordinate on the image
	 * @param height
	 *            Height of the individual sprites
	 * @param width
	 *            Widths of the individual sprites
	 * @param column
	 *            How many columns you want from the row. If column is 0 or
	 *            less, it will iterate until end of image.getWidth();
	 * @return
	 */

	public static ArrayList<Sprite> getSpecificSpriteMapRow(Sprite sprite,
			int start, int height, int width, int column) {

		// TODO todo. 'nuff said

		return null;
	}

	/**
	 * Gets a row from the spritemap from a specific Y location to height
	 * 'height'.
	 * 
	 * @param start
	 *            Y coordinate on the image
	 * @param height
	 *            Height of the individual sprites
	 * @param width
	 *            Widths of the individual sprites
	 * @param column
	 *            How many columns you want from the row. If column is 0 or
	 *            less, it will iterate until end of image.getWidth();
	 * @return
	 */

	public static ArrayList<Sprite> getSpecificSpriteMapRow(Sprite sprite,
			int start, int height, int width) {

		return getSpecificSpriteMapRow(sprite, start, height, width, 0);
	}

}
