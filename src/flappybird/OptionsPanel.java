package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel {

	private Font titleFont, menuFont, optionsFont;
	private Image menuBack;
	private Image buttonBack;
	private Image buttonBackSelected;
	private int menuPos = 0;

	private ArrayList<String> listaMusica;
	private ArrayList<String> listaPersonajes;
	private ArrayList<String> listaIdiomas;
	private ArrayList<String> listaMusicaEsp;
	private ArrayList<String> listaIdiomasEsp;

	private int posListaMusica;
	private int posListaPersonajes;
	private int posListaIdiomas;
	
	private Properties idioma;
	private Properties opciones;

	public OptionsPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);
		optionsFont = new Font("8BIT WONDER", Font.BOLD, 24);
		idioma = FlappyBird.idioma;

		inicializarOpciones();

		try {
			menuBack = ImageIO.read(new File("MenuImage.jpg"));
			buttonBack = ImageIO.read(new File("BotonMenuRetro.png"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void inicializarOpciones() {
		listaMusica = new ArrayList<String>();
		listaPersonajes = new ArrayList<String>();
		listaIdiomas = new ArrayList<String>();
		listaMusicaEsp = new ArrayList<String>();
		listaIdiomasEsp = new ArrayList<String>();
		
		listaMusicaEsp.add("Si");
		listaMusicaEsp.add("No");

		listaMusica.add(idioma.getProperty("si"));
		listaMusica.add(idioma.getProperty("no"));
		
		listaPersonajes.add("Flappy");
		listaPersonajes.add("Banana");
		listaPersonajes.add("Mario");
		listaPersonajes.add("Pacman");

		listaIdiomasEsp.add("Espanol");
		listaIdiomasEsp.add("Ingles");
		
		listaIdiomas.add(idioma.getProperty("espanol"));
		listaIdiomas.add(idioma.getProperty("ingles"));

		loadOptions();
	}

	private void loadOptions() {
		opciones = new Properties();

		try {
			opciones.load(new FileInputStream(FlappyBird.OPTIONS_FILE));

			posListaMusica = listaMusicaEsp.indexOf(opciones.getProperty("musica"));
			posListaPersonajes = listaPersonajes.indexOf(opciones.getProperty("personaje"));
			posListaIdiomas = listaIdiomasEsp.indexOf(opciones.getProperty("idioma"));
		} catch (IOException e) {
			posListaMusica = 0;
			posListaPersonajes = 0;
			posListaIdiomas = 0;
		} finally {
			if (posListaMusica < 0) {
				posListaMusica = 0;
			}
			if (posListaPersonajes < 0) {
				posListaPersonajes = 0;
			}
			if (posListaIdiomas < 0) {
				posListaIdiomas = 0;
			}
		}

	}

	private void saveOptions() {
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(FlappyBird.OPTIONS_FILE)));
			out.println("musica=" + listaMusicaEsp.get(posListaMusica));
			out.println("personaje=" + listaPersonajes.get(posListaPersonajes));
			out.println("idioma=" + listaIdiomasEsp.get(posListaIdiomas));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
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
		if (menuPos < 3) {
			menuPos++;
			this.repaint();
			return true;
		}
		return false;
	}

	public boolean moveLeft() {
		if (menuPos < 3) {
			switch (menuPos) {
			case 0:
				try {
					listaMusica.get(posListaMusica - 1);
					posListaMusica--;
				} catch (IndexOutOfBoundsException e) {
					posListaMusica = listaMusica.size() - 1;
				}
				break;
			case 1:
				try {
					listaPersonajes.get(posListaPersonajes - 1);
					posListaPersonajes--;
				} catch (IndexOutOfBoundsException e) {
					posListaPersonajes = listaPersonajes.size() - 1;
				}
				break;
			case 2:
				try {
					listaIdiomas.get(posListaIdiomas - 1);
					posListaIdiomas--;
				} catch (IndexOutOfBoundsException e) {
					posListaIdiomas = listaIdiomas.size() - 1;
				}
				break;
			default:
				break;
			}
			this.repaint();
			return true;
		}
		return false;
	}

	public boolean moveRight() {
		if (menuPos < 3) {
			switch (menuPos) {
			case 0:
				try {
					listaMusica.get(posListaMusica + 1);
					posListaMusica++;
				} catch (IndexOutOfBoundsException e) {
					posListaMusica = 0;
				}
				break;
			case 1:
				try {
					listaPersonajes.get(posListaPersonajes + 1);
					posListaPersonajes++;
				} catch (IndexOutOfBoundsException e) {
					posListaPersonajes = 0;
				}
				break;
			case 2:
				try {
					listaIdiomas.get(posListaIdiomas + 1);
					posListaIdiomas++;
				} catch (IndexOutOfBoundsException e) {
					posListaIdiomas = 0;
				}
				break;
			default:
				break;
			}
			this.repaint();
			return true;
		}
		return false;
	}

	public boolean canExit() {
		if (menuPos == 3) {
			saveOptions();
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

		// Opci�n m�sica
		paintMusicOption(g, pos0);

		// Opci�n personaje
		paintCharacterOption(g, pos1);

		// Opci�n idioma
		paintLanguageOption(g, pos2);

		// Bot�n guardar
		paintSaveButton(g, pos3);
	}

	private void paintMain(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(0, 0, 0, 170));
		g.drawString("Opciones", 175, 50);
		g.setFont(optionsFont);
	}

	private void paintMusicOption(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(240, 10, 15, 170));
		} else {
			g.setColor(new Color(0, 0, 0, 170));
		}
		g.drawString(idioma.getProperty("musica"), 60, 120);
		g.drawString(listaMusica.get(posListaMusica), 360, 120);
	}

	private void paintCharacterOption(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(240, 10, 15, 170));
		} else {
			g.setColor(new Color(0, 0, 0, 170));
		}
		g.drawString(idioma.getProperty("personaje"), 60, 200);
		g.drawString(listaPersonajes.get(posListaPersonajes), 360, 200);
	}

	private void paintLanguageOption(Graphics g, boolean selected) {
		if (selected) {
			g.setColor(new Color(240, 10, 15, 170));
		} else {
			g.setColor(new Color(0, 0, 0, 170));
		}
		g.drawString(idioma.getProperty("idioma"), 60, 280);
		g.drawString(listaIdiomas.get(posListaIdiomas), 360, 280);
	}

	private void paintSaveButton(Graphics g, boolean selected) {
		g.setColor(new Color(255, 255, 255));
		g.setFont(menuFont);
		Image boton = null;
		if (selected) {
			boton = buttonBackSelected;
		} else {
			boton = buttonBack;
		}
		g.drawImage(boton, 150, 475, 300, 50, null);
		g.drawString(idioma.getProperty("guardar"), 230, 510);
	}
}
