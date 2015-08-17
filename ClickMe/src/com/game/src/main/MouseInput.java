package com.game.src.main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class MouseInput implements MouseListener {

	Game game;
	Enemy enemy;
	Random r = new Random();

	public MouseInput(Game game, Enemy enemy) {
		this.game = game;
		this.enemy = enemy;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int mx = arg0.getX();
		int my = arg0.getY();
		/**
		 	FOR MENU 
			public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
			public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
			
			FOR GAMEOVER
			public Rectangle retryButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
			public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
		 */

		if (game.State == game.State.MENU) {
			// PLAY BUTTON
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 150 && my <= 200) {
					// PRESSED PLAY BUTTON
					Game.State = Game.STATE.GAME;
				}
			}

			// QUIT BUTTON
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 250 && my <= 300) {
					// PRESSED QUIT BUTTON
					System.exit(1);
				}
			}
		} else if (game.State == game.State.GAME) {
			if (mx >= enemy.getX() - enemy.BOUND && mx <= enemy.getX() + enemy.BOUND) {
				if (my >= enemy.getY() - enemy.BOUND && my <= enemy.getY() + enemy.BOUND) {
//					System.out.println("CLICKED");
					game.SCORE++;
					enemy.setX((double)r.nextInt(Game.WIDTH*Game.SCALE-50));
					enemy.setY((double)r.nextInt(Game.HEIGHT*Game.SCALE-50));
				}
			}
		} else if (game.State == game.State.GAMEOVER) {
			// PLAY BUTTON
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 250 && my <= 300) {
					// PRESSED PLAY BUTTON
					game.time = 15;
					game.SCORE = 0;
					Game.State = Game.STATE.GAME;
				}
			}

			// QUIT BUTTON
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 350 && my <= 400) {
					// PRESSED QUIT BUTTON
					System.exit(1);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
