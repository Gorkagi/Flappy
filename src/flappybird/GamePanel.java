/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class GamePanel extends JPanel {

	private Bird bird;
	private ArrayList<Rectangle> rects;
	private Game game;
	private Font scoreFont, pauseFont;
	public static final int PIPE_W = 50, PIPE_H = 20;
	private Image pipeHead, pipeLength, background1, background2, background3;

	public GamePanel(Game game, Bird bird, ArrayList<Rectangle> rects) {
		this.game = game;
		this.bird = bird;
		this.rects = rects;
		scoreFont = new Font("8BIT WONDER", Font.PLAIN, 18);
		pauseFont = new Font("8BIT WONDER", Font.BOLD, 34);

		try {
			// pipeHead = ImageIO.read(new File("tree.png"));
			pipeLength = ImageIO.read(new File("pipe_part.png"));
			background1 = ImageIO.read(new File("GameBack.png"));
			background2 = ImageIO.read(new File("GameBack2.png"));
			background3 = ImageIO.read(new File("GameBack3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		if (game.getScore() < Game.LVL2 - 10) {
			g.drawImage(background1, 0, 0, 600, 600, null);
			game.setFrequency(2);

		} else if (game.getScore() >= Game.LVL2 - 10 && game.getScore() < Game.LVL2 + 10) {
			if (game.getScore() % 5 == 0) {
				g.drawImage(background2, 0, 0, 600, 600, null);
			} else {
				g.drawImage(background1, 0, 0, 600, 600, null);
			}

		} else if (game.getScore() >= Game.LVL2 + 10 && game.getScore() < Game.LVL3 - 10) {
			g.drawImage(background2, 0, 0, 600, 600, null);
			game.setFrequency(4);

		}  else if (game.getScore() >= Game.LVL3 - 10 && game.getScore() < Game.LVL3 + 10) {
			if (game.getScore() % 5 == 0) {
				g.drawImage(background3, 0, 0, 600, 600, null);
			} else {
				g.drawImage(background2, 0, 0, 600, 600, null);
			}

		} else {
			g.drawImage(background3, 0, 0, 600, 600, null);
			game.setFrequency(6);

		}
		bird.update(g);
		g.setColor(Color.RED);
		for (Rectangle r : rects) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.GREEN);
			// g2d.fillRect(r.x, r.y, r.width, r.height);
			AffineTransform old = g2d.getTransform();
			g2d.translate(r.x + PIPE_W / 2, r.y + PIPE_H / 2);
			if (r.y < FlappyBird.HEIGHT / 2) {
				g2d.translate(0, r.height);
				g2d.rotate(Math.PI);
			}
			// g2d.drawImage(pipeHead, -PIPE_W / 2, -PIPE_H / 2, GamePanel.PIPE_W,
			// GamePanel.PIPE_H, null);
			g2d.drawImage(pipeLength, -PIPE_W / 2, PIPE_H / 2, GamePanel.PIPE_W, r.height, null);
			g2d.setTransform(old);
		}
		g.setFont(scoreFont);
		g.setColor(Color.WHITE);
		g.drawString("Score " + game.getScore(), 10, 20);

		if (game.isPaused()) {
			g.setFont(pauseFont);
			g.setColor(new Color(0, 0, 0, 170));
			g.drawString("PAUSED", FlappyBird.WIDTH / 2 - 100, FlappyBird.HEIGHT / 2 - 75);
			g.drawString("PRESS UP TO BEGIN", FlappyBird.WIDTH / 2 - 275, FlappyBird.HEIGHT / 2 + 100);
		}
	}
}
