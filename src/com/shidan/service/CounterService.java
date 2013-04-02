package com.shidan.service;


/**
 * Counter service that creates simple unique ids.
 * 
 * TODO: This only works per session. If we save IDs for later we'll have trouble.
 * 
 * @author jdaniel
 *
 */

public class CounterService {

	private static int spriteCounter = 0;
	private static int spriteIncrementer = 2;
	
	public static int incrementSprite() {
		
		spriteCounter += spriteIncrementer;
		
		return CounterService.spriteCounter;
	}
	
}
