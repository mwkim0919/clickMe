package com.game.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	
	private BufferedImage image;

	public BufferedImage loadImage(String path) throws IOException {
		// TODO Auto-generated method stub
		image = ImageIO.read(getClass().getResource(path));
		return image;
	}

}
