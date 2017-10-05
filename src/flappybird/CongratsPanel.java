package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import error.FirebaseException;
import error.JacksonUtilityException;
import util.DatabaseUtility;

public class CongratsPanel extends JPanel {

	private Font titleFont, textFont, optionsFont;
	private Image menuBack;
	private Image arrowUp;
	private Image arrowDown;
	private Image arrowUpSelected;
	private Image arrowDownSelected;
	private int menuPos = 0;
	private int saveNotSavePos = 0;
	private int score = 0;
	private Map<String, Object> ranking;

	private char letra0 = 'A';
	private char letra1 = 'A';
	private char letra2 = 'A';
	private ArrayList<String> saveNotSave;

	private Properties idioma;

	public CongratsPanel(int score, Map<String, Object> ranking) {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		textFont = new Font("8BIT WONDER", Font.BOLD, 20);
		optionsFont = new Font("8BIT WONDER", Font.BOLD, 24);
		saveNotSave = new ArrayList<>();
		idioma = FlappyBird.idioma;
		saveNotSave.add(idioma.getProperty("guardar"));
		saveNotSave.add(idioma.getProperty("noGuardar"));

		this.score = score;
		this.ranking = ranking;

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

	public boolean moveUp() {
		switch (menuPos) {
		case 0:
			letra0 = charUp(letra0);
			break;
		case 1:
			letra1 = charUp(letra1);
			break;
		case 2:
			letra2 = charUp(letra2);
			break;
		case 3:
			try {
				saveNotSave.get(saveNotSavePos - 1);
				saveNotSavePos--;
			} catch (IndexOutOfBoundsException e) {
				saveNotSavePos = saveNotSave.size() - 1;
			}
			break;
		default:
			break;
		}

		this.repaint();
		return true;
	}

	public boolean moveDown() {
		switch (menuPos) {
		case 0:
			letra0 = charDown(letra0);
			break;
		case 1:
			letra1 = charDown(letra1);
			break;
		case 2:
			letra2 = charDown(letra2);
			break;
		case 3:
			try {
				saveNotSave.get(saveNotSavePos + 1);
				saveNotSavePos++;
			} catch (IndexOutOfBoundsException e) {
				saveNotSavePos = 0;
			}
			break;
		default:
			break;
		}

		this.repaint();
		return true;
	}

	public boolean moveLeft() {
		if (menuPos > 0) {
			menuPos--;
			this.repaint();
			return true;
		}
		return false;
	}

	public boolean moveRight() {
		if (menuPos < 3) {
			menuPos++;
			this.repaint();
			return true;
		}
		return false;
	}

	public char charUp(char letra) {
		if (letra == 'A') {
			letra = '9';
		} else if (letra == '0') {
			letra = 'Z';
		} else {
			letra--;
		}
		return letra;
	}

	public char charDown(char letra) {
		if (letra == 'Z') {
			letra = '0';
		} else if (letra == '9') {
			letra = 'A';
		} else {
			letra++;
		}
		return letra;
	}

	public boolean canEnter() {
		if (menuPos == 3) {
			if (saveNotSavePos == 0) {
				uploadNewRanking();
			}
			return true;
		}

		return false;
	}

	public boolean goToMenu() {
		if (saveNotSavePos == 0) {
			return false;
		} else {
			return true;
		}
	}

	private void uploadNewRanking() {
		Map<String, Integer> mapa = new HashMap<>();
		Map<String, Integer> mapaOrdenado;
		Map<String, Object> rankingParaSubir = new HashMap<>();
		String name = "" + letra0 + letra1 + letra2;

		int i = 0;
		for (Entry<String, Object> entry : ranking.entrySet()) {
			String nombre = entry.getValue().toString().split(":")[0];
			Integer puntuacion = Integer.valueOf(entry.getValue().toString().split(":")[1]);
			mapa.put(nombre+i, puntuacion);
			i++;
		}

		mapa.put(name+i, score);

		mapaOrdenado = sortByValue(mapa);

		i = 0;
		char letra = 'A';
		for (Entry<String, Integer> entry : mapaOrdenado.entrySet()) {
			rankingParaSubir.put(String.valueOf(letra), entry.getKey().substring(0, 3) + ":" + entry.getValue());
			i++;
			letra++;
			if (i == 5) {
				break;
			}
		}

		try {
			DatabaseUtility.updateDataTop5(rankingParaSubir);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JacksonUtilityException e) {
			e.printStackTrace();
		} catch (FirebaseException e) {
			e.printStackTrace();
		}

	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
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
	}

	private void paintMain(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(255, 255, 255));
		g.drawString(idioma.getProperty("enhorabuena"), 100, 50);
	}

	private void paintScore(Graphics g) {
		g.drawString(idioma.getProperty("puntuacion"), 100, 175);
		g.drawString(String.valueOf(score), 400, 175);
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
		g.drawString(String.valueOf(letra0), 75, 400);
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

		g.drawString(String.valueOf(letra1), 150, 400);
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

		g.drawString(String.valueOf(letra2), 225, 400);
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

		g.drawString(saveNotSave.get(saveNotSavePos), 325, 400);
	}

}
