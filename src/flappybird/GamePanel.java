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
import java.util.concurrent.SynchronousQueue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class GamePanel extends JPanel {

	private Bird bird;
	private ArrayList<Rectangle> rects;
	private FlappyBird fb;
	private Font scoreFont, pauseFont;
	public static final Color bg = new Color(0, 158, 158);
	public static final int PIPE_W = 50, PIPE_H = 20;
	private Image pipeHead, pipeLength, background1, background2, background3;
	static MusicPlayer player, player1, player2;
	static boolean killPlayer1, killPlayer2, killPlayer;

	public final static String GAME = "audio/Game.mp3";
	public final static String GAME1 = "audio/GameX1.mp3";
	public final static String GAME2 = "audio/GameX2.mp3";
	
	public GamePanel(FlappyBird fb, Bird bird, ArrayList<Rectangle> rects) {
		this.fb = fb;
		this.bird = bird;
		this.rects = rects;
		scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
		pauseFont = new Font("Arial", Font.BOLD, 48);
		this.initPlayers();
		
		try {
			//pipeHead = ImageIO.read(new File("tree.png"));
			pipeLength = ImageIO.read(new File("pipe_part.png"));
			background1 = ImageIO.read(new File("GameBack.png"));
			background2 = ImageIO.read(new File("GameBack2.png"));
			background3 = ImageIO.read(new File("GameBack3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initPlayers() {
		player = new MusicPlayer(GAME);
		player1 = new MusicPlayer(GAME1);
		player2 = new MusicPlayer(GAME2);
		killPlayer = false;
		killPlayer1 = false;
		killPlayer2 = false;
	}

	@Override
	public void paintComponent(Graphics g) {
		
		if(fb.getScore() < 150) {
			g.drawImage(background1, 0, 0, 600, 600, null);
			fb.setSpeedFPS(30);
			fb.frequency = 3;
			
			if (!player.isAlive() && killPlayer == false) {
				player.play();
			}
			
			
		}else if(fb.getScore() > 150 && fb.getScore() < 250) {
			g.drawImage(background2, 0, 0, 600, 600, null);
			fb.setSpeedFPS(15);
			fb.frequency = 2;
			
			
			if (player.isAlive() ) {
				player.close();
			}
			
			if (!player1.isAlive() && killPlayer1 == false) {
				player1.play();
			}
			

		}else {
			g.drawImage(background3, 0, 0, 600, 600, null);
			fb.setSpeedFPS(10);
			fb.frequency = 1;

			
			if (player1.isAlive()) {
				player1.close();
			}
			
			if (!player2.isAlive() && killPlayer2 == false) {
				player2.play();
			}
			
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
			//g2d.drawImage(pipeHead, -PIPE_W / 2, -PIPE_H / 2, GamePanel.PIPE_W, GamePanel.PIPE_H, null);
			g2d.drawImage(pipeLength, -PIPE_W / 2, PIPE_H / 2, GamePanel.PIPE_W, r.height, null);
			g2d.setTransform(old);
		}
		g.setFont(scoreFont);
		g.setColor(Color.BLACK);
		g.drawString("Score: " + fb.getScore(), 10, 20);

		if (fb.paused()) {
			g.setFont(pauseFont);
			g.setColor(new Color(0, 0, 0, 170));
			g.drawString("PAUSED", FlappyBird.WIDTH / 2 - 100, FlappyBird.HEIGHT / 2 - 100);
			g.drawString("PRESS SPACE TO BEGIN", FlappyBird.WIDTH / 2 - 300, FlappyBird.HEIGHT / 2 + 50);
		}
	}
}
