package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel{

	private Font titleFont, menuFont, optionsFont;
	private Image menuBack;
	private Image buttonBack;
	private Image buttonBackSelected;
	private int menuPos = 0;
	
	public OptionsPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);
		optionsFont = new Font("8BIT WONDER", Font.BOLD, 24);
		
		try {
			menuBack = ImageIO.read(new File("MenuImage.jpg"));
			buttonBack = ImageIO.read(new File("BotonMenuRetro.png"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean moveUp() {
		if(menuPos > 0) {
			menuPos--;
			this.repaint();
			return true;
		}
		return false;
	}
	
	public boolean moveDown() {
		if(menuPos < 3) {
			menuPos++;
			this.repaint();
			return true;
		}
		return false;
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

		//Opción música
		paintMusicOption(g, pos0);

		//Opción personaje
		paintCharacterOption(g, pos1);

		//Opción idioma
		paintLanguageOption(g, pos2);

		//Botón guardar
		paintSaveButton(g, pos3);
	}

	private void paintMain(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(0,0,0,170));
		g.drawString("Opciones", 175, 50);
		g.setFont(optionsFont);
	}

	private void paintMusicOption(Graphics g, boolean selected) {
		if(selected) {
			g.setColor(new Color(240,10,15,170));
		} else {
			g.setColor(new Color(0,0,0,170));
		}
		g.drawString("Musica", 60, 120);
		g.drawString("Si", 360, 120);
	}

	private void paintCharacterOption(Graphics g, boolean selected) {
		if(selected) {
			g.setColor(new Color(240,10,15,170));
		} else {
			g.setColor(new Color(0,0,0,170));
		}
		g.drawString("Personaje", 60, 200);
		g.drawString("Flappy", 360, 200);
	}

	private void paintLanguageOption(Graphics g, boolean selected) {
		if(selected) {
			g.setColor(new Color(240,10,15,170));
		} else {
			g.setColor(new Color(0,0,0,170));
		}
		g.drawString("Idioma", 60, 280);
		g.drawString("Espanol", 360, 280);
	}

	private void paintSaveButton(Graphics g, boolean selected) {
		g.setColor(new Color(255,255,255));
		g.setFont(menuFont);
		Image boton = null;
		if(selected) {
			boton = buttonBackSelected;
		} else {
			boton = buttonBack;
		}
		g.drawImage(boton, 150, 475, 300, 50, null);			
		g.drawString("Guardar", 230, 510);
	}
}
