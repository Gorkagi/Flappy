package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel {

	private Font titleFont, menuFont;
	private Image buttonBack;
	private Image gameOver;
	private Image buttonBackSelected;
	private int menuPos = 0;
	private int score = 0;

	private Properties idioma;

	public GameOverPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);
		idioma = FlappyBird.idioma;

		try {
			gameOver = ImageIO.read(new File("flappyrip.png"));
			buttonBack = ImageIO.read(new File("BotonMenuRetro.png"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean moveUp() {
		if (menuPos > 0) {
			menuPos--;
			this.repaint();
			return true;
		}
		return false;
	}

	public boolean moveDown() {
		if (menuPos < 2) {
			menuPos++;
			this.repaint();
			return true;
		}
		return false;
	}

	public int getMenuPos() {
		return this.menuPos;
	}

	@Override
	public void paintComponent(Graphics g) {
		boolean pos0 = false;
		boolean pos1 = false;
		boolean pos2 = false;

		paintMenu(g);

		switch (menuPos) {
		case 0:
			pos0 = true;
			break;
		case 1:
			pos1 = true;
			break;
		case 2:
			pos2 = true;
			break;
		default:
			break;
		}

		// Reintentar
		paintRetryButton(g, pos0);

		// Volver al menu
		paintMenuButton(g, pos1);

		// Salir
		paintExitButton(g, pos2);

	}

	private void paintRetryButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 340, 300, 50, null);
		} else {
			g.drawImage(buttonBack, 150, 340, 300, 50, null);
		}
		g.drawString(idioma.getProperty("reintentar"), 200, 375);
	}

	private void paintMenuButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 410, 300, 50, null);
		} else {
			g.drawImage(buttonBack, 150, 410, 300, 50, null);
		}
		g.drawString(idioma.getProperty("menu"), 255, 445);
	}

	private void paintExitButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 480, 300, 50, null);
		} else {
			g.drawImage(buttonBack, 150, 480, 300, 50, null);
		}
		g.drawString(idioma.getProperty("salir"), 255, 515);
	}

	private void paintMenu(Graphics g) {
		g.fillRect(0, 0, 600, 600);
		g.setFont(titleFont);
		g.drawImage(gameOver, 188, 0, 225, 234, null);
		g.setColor(new Color(255, 25, 30));
		g.drawString("Game", 125, 275);
		g.drawString("Over", 325, 275);
		g.setColor(new Color(255, 255, 255, 200));
		g.drawString(idioma.getProperty("puntuacion"), 45, 320);
		g.drawString(String.valueOf(score), 445, 320);
		g.setColor(new Color(255, 255, 255));
		g.setFont(menuFont);
	}

	public void setScore(int score) {
		this.score = score;
	}
}
