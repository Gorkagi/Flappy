package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CongratsPanel extends JPanel {

	private Font titleFont, textFont, optionsFont;
	private Image menuBack;
	private Image arrowUp;
	private Image arrowDown;
	private Image arrowUpSelected;
	private Image arrowDownSelected;
	private int menuPos = 0;

	private Properties idioma;

	public CongratsPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		textFont = new Font("8BIT WONDER", Font.BOLD, 20);
		optionsFont = new Font("8BIT WONDER", Font.BOLD, 24);
		idioma = FlappyBird.idioma;

		try {
			menuBack = ImageIO.read(new File("Congrats.jpg"));
			arrowUp = ImageIO.read(new File("ArrowUp.png"));
			arrowDown = ImageIO.read(new File("ArrowDown.png"));
			arrowUpSelected = ImageIO.read(new File("ArrowUpSelected.png"));
			arrowDownSelected = ImageIO.read(new File("ArrowDownSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		paintMain(g);

		boolean pos0 = false;
		boolean pos1 = false;
		boolean pos2 = false;
		boolean pos3 = false;

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
		case 3:
			pos3 = true;
			break;
		default:
			break;
		}

		paintCongratsText(g);

		paintScore(g);

		paintWord1(g, pos0);

		paintWord2(g, pos1);

		paintWord3(g, pos2);

		paintSave(g, pos3);

		// Opción música
		// paintMusicOption(g, pos0);

		// Opción personaje
		// paintCharacterOption(g, pos1);

		// Opción idioma
		// paintLanguageOption(g, pos2);

		// Botón guardar
		// paintSaveButton(g, pos3);
	}

	private void paintMain(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(255, 255, 255));
		g.drawString(idioma.getProperty("enhorabuena"), 100, 50);
	}
	
	private void paintScore(Graphics g) {
		g.drawString(idioma.getProperty("puntuacion"), 100, 175);
		g.drawString(String.valueOf(180), 400, 175);
	}

	private void paintCongratsText(Graphics g) {
		g.setFont(textFont);
		g.setColor(new Color(255, 255, 255, 225));
		g.drawString(idioma.getProperty("entrarTop5"), 75, 125);
		g.drawString(idioma.getProperty("introduceNombre"), 100, 275);

	}

	private void paintWord1(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(0, 100, 200));
			g.drawImage(arrowUpSelected, 74, 343, 25, 25, null);
			g.drawImage(arrowDownSelected, 74, 412, 25, 25, null);
		} else {
			g.setColor(new Color(255, 255, 255));
			g.drawImage(arrowUp, 74, 343, 25, 25, null);
			g.drawImage(arrowDown, 74, 412, 25, 25, null);
		}

		g.setFont(optionsFont);
		g.drawString("A", 75, 400);
	}

	private void paintWord2(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(0, 100, 200));
			g.drawImage(arrowUpSelected, 149, 343, 25, 25, null);
			g.drawImage(arrowDownSelected, 149, 412, 25, 25, null);
		} else {
			g.setColor(new Color(255, 255, 255));
			g.drawImage(arrowUp, 149, 343, 25, 25, null);
			g.drawImage(arrowDown, 149, 412, 25, 25, null);
		}

		g.drawString("A", 150, 400);
	}

	private void paintWord3(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(0, 100, 200));
			g.drawImage(arrowUpSelected, 224, 343, 25, 25, null);
			g.drawImage(arrowDownSelected, 224, 412, 25, 25, null);
		} else {
			g.setColor(new Color(255, 255, 255));
			g.drawImage(arrowUp, 224, 343, 25, 25, null);
			g.drawImage(arrowDown, 224, 412, 25, 25, null);
		}

		g.drawString("A", 225, 400);
	}

	private void paintSave(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(0, 100, 200));
			g.drawImage(arrowUpSelected, 398, 343, 25, 25, null);
			g.drawImage(arrowDownSelected, 398, 412, 25, 25, null);
		} else {
			g.setColor(new Color(255, 255, 255));
			g.drawImage(arrowUp, 398, 343, 25, 25, null);
			g.drawImage(arrowDown, 398, 412, 25, 25, null);
		}

		g.drawString(idioma.getProperty("guardar"), 325, 400);
	}

}
