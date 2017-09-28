/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class Bird {
	public float x, y, vx, vy;
	public static final int RAD = 25;
	private Image img;
	
	private final String BIRD_IMG = "bird.png";
	private final String MARIO_IMG = "mario.png";
	private final String PACMAN_IMG = "pacman.png";
	private final String BANANE_IMG = "banane.png";

	public Bird() {
		x = FlappyBird.WIDTH / 2;
		y = FlappyBird.HEIGHT / 2;
		try {
			switch (FlappyBird.getOpciones().getProperty("personaje")) {
			case "Flappy":
				img = ImageIO.read(new File(BIRD_IMG));
				break;
			case "Banana":
				img = ImageIO.read(new File(BANANE_IMG));
				break;
			case "Mario":
				img = ImageIO.read(new File(MARIO_IMG));
				break;
			case "Pacman":
				img = ImageIO.read(new File(PACMAN_IMG));
				break;
			default:
				img = ImageIO.read(new File(BIRD_IMG));
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setVx(int vx) {
		this.vx = vx;
	}

	public void physics() {
		x += vx;
		y += vy;
		vy += 0.5f;
	}

	public void update(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(img, Math.round(x - RAD), Math.round(y - RAD), 2 * RAD, 2 * RAD, null);
	}

	public void jump() {
		vy = -8;
	}

	public void reset() {
		x = 640 / 2;
		y = 640 / 2;
		vx = vy = 0;
	}
}
