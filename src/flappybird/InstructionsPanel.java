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

public class InstructionsPanel extends JPanel {

	private Font titleFont, menuFont, instruccionesFont;
	private Image menuBack;
	private Image buttonBackSelected;
	
	private Properties idioma;

	public InstructionsPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);
		instruccionesFont = new Font("8BIT WONDER", Font.PLAIN, 18);
		idioma = FlappyBird.idioma;

		try {
			menuBack = ImageIO.read(new File("MenuImage.jpg"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		paintMain(g);
		paintInstructions(g);
		paintExitButton(g);
	}

	private void paintMain(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(0, 0, 0, 170));
		g.drawString(idioma.getProperty("instrucciones"), 75, 50);
	}

	private void paintInstructions(Graphics g) {
		g.setFont(instruccionesFont);
		g.drawString(idioma.getProperty("instrucciones1"), 50, 120);
		g.drawString(idioma.getProperty("instrucciones2"), 50, 160);
		g.drawString(idioma.getProperty("instrucciones3"), 50, 200);
		g.drawString(idioma.getProperty("instrucciones4"), 50, 240);
		g.drawString(idioma.getProperty("instrucciones5"), 50, 260);
	}

	private void paintExitButton(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.setFont(menuFont);
		g.drawImage(buttonBackSelected, 150, 475, 300, 50, null);
		g.drawString(idioma.getProperty("atras"), 250, 510);
	}
}
