package com.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy {
	
	private double x, y;
	public static final int BOUND = 45;
	
	Random r = new Random();
	private int speedX , speedY;
	
	private BufferedImage img;
	
	Game game;
	
	
	public Enemy(double x, double y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
		
		try {
			img = ImageIO.read(getClass().getResource("/zergling.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
//		System.out.println("X = " + x + " Y = " + y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
}
