package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private Font titleFont, menuFont;
	private Image buttonBack;
	private Image menuBack;
	private Image buttonBackSelected;
	private int menuPos = 0;

	public MenuPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);

		try {
			menuBack = ImageIO.read(new File("MenuImage.jpg"));
			buttonBack = ImageIO.read(new File("BotonMenuRetro.png"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateMenuPosition(int position) {
		menuPos = position;
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		boolean pos0 = false;
		boolean pos1 = false;
		boolean pos2 = false;
		boolean pos3 = false;
		boolean pos4 = false;

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
		case 3:
			pos3 = true;
			break;
		case 4:
			pos4 = true;
		default:
			break;
		}

		// Position 0
		paintPlayButton(g, pos0);

		// Position 1
		paintRankingButton(g, pos1);

		// Position 2
		paintInstrucButton(g, pos2);

		// Position 3
		paintOptionsButton(g, pos3);

		// Position 4
		paintExitButton(g, pos4);

	}

	private void paintPlayButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 100, 300, 50, null);
			g.drawString("Jugar", 250, 135);
		} else {
			g.drawImage(buttonBack, 150, 100, 300, 50, null);
			g.drawString("Jugar", 250, 135);
		}
	}

	private void paintRankingButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 170, 300, 50, null);
			g.drawString("Ranking", 240, 205);
		} else {
			g.drawImage(buttonBack, 150, 170, 300, 50, null);
			g.drawString("Ranking", 240, 205);
		}
	}

	private void paintInstrucButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 240, 300, 50, null);
			g.drawString("Instrucciones", 175, 275);
		} else {
			g.drawImage(buttonBack, 150, 240, 300, 50, null);
			g.drawString("Instrucciones", 175, 275);
		}
	}

	private void paintOptionsButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 310, 300, 50, null);
			g.drawString("Opciones", 225, 345);
		} else {
			g.drawImage(buttonBack, 150, 310, 300, 50, null);
			g.drawString("Opciones", 225, 345);
		}
	}

	private void paintExitButton(Graphics g, boolean selected) {
		if (selected) {
			g.drawImage(buttonBackSelected, 150, 380, 300, 50, null);
			g.drawString("Salir", 255, 415);
		} else {
			g.drawImage(buttonBack, 150, 380, 300, 50, null);
			g.drawString("Salir", 255, 415);
		}
	}

	private void paintMenu(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(0, 0, 0, 170));
		g.drawString("Flappy", 200, 50);
		g.setColor(new Color(255, 255, 255));
		g.setFont(menuFont);
	}

}
