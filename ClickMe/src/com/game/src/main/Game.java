package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -8178784217268374099L;

	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 2;
	public static int SCORE = 0;
	public int time = 15;

	public final String TITLE = "SPACE GAME";

	private Thread thread;

	private boolean running = false;

	private Menu menu;
	private HighScoreMenu h_menu;
	private Enemy enemy;
	private Random r = new Random();
	private Timer timer;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage background;

	public static enum STATE {
		MENU,
		GAME,
		GAMEOVER,
	};

	public static STATE State = STATE.MENU;

	private class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			time--;
		}
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}

	private void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			background = loader.loadImage("/background.gif");
		} catch (IOException e) {
			e.printStackTrace();
		}

		enemy = new Enemy((double)r.nextInt(WIDTH*SCALE-50), (double)r.nextInt(HEIGHT*SCALE-50), this);
		menu = new Menu();
		h_menu = new HighScoreMenu(this);
		timer = new Timer(1000, new TimerListener());

		this.addMouseListener(new MouseInput(this, enemy));
	}

	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running ) {
			// this would be the game loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println(updates + " Ticks, FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	private void tick() {
		enemy.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Font fnt_score = new Font("arial", Font.BOLD, 20);
		//////////////////

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0, 0, null);

		if (State == STATE.GAME) {
			timer.start();
			enemy.render(g);
			// for score rendering
			g.setFont(fnt_score);
			g.setColor(Color.BLACK);
			g.drawString("Score: " + Integer.toString(SCORE), 5, 20);
			// for timer rendering
			g.drawString("Time Left: " + Integer.toString(time), WIDTH*SCALE-170, 20);
			
			if (time == 0) {
				timer.stop();
				State = STATE.GAMEOVER;
			}
		} else if (State == STATE.MENU) {
			menu.render(g);
		} else if (State == STATE.GAMEOVER) {
			h_menu.render(g);
		}

		//////////////////

		g.dispose();
		bs.show();
	}

}
