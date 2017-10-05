package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import error.FirebaseException;
import util.DatabaseUtility;

public class RankingPanel extends JPanel {

	private Font titleFont, menuFont;
	private Image menuBack;
	private Image buttonBackSelected;
	private Image oro, plata, bronce;

	private Map<String, Object> ranking;

	private List<String> names;

	private Properties idioma;

	public RankingPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);
		idioma = FlappyBird.idioma;

		names = new ArrayList<>();

		try {
			menuBack = ImageIO.read(new File("MenuImage.jpg"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
			oro = ImageIO.read(new File("oro.png"));
			plata = ImageIO.read(new File("plata.png"));
			bronce = ImageIO.read(new File("bronce.png"));
			ranking = DatabaseUtility.retrieveDataTop5();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FirebaseException e) {
			e.printStackTrace();
		}

		names.add(ranking.get("A").toString());
		names.add(ranking.get("B").toString());
		names.add(ranking.get("C").toString());
		names.add(ranking.get("D").toString());
		names.add(ranking.get("E").toString());
	}

	@Override
	public void paintComponent(Graphics g) {
		paintMenu(g);
		paintPlayButton(g);
		paintTop1(g);
		paintTop2(g);
		paintTop3(g);
		paintRestTop(g);
	}

	private void paintTop1(Graphics g) {
		g.drawImage(oro, 50, 170, null);
		g.setColor(new Color(255, 215, 0, 255));
		g.drawString("1", 80, 190);
		g.drawString(names.get(0).split(":")[1], 150, 190);
		g.drawString(names.get(0).split(":")[0], 400, 190);
	}

	private void paintTop2(Graphics g) {
		g.drawImage(plata, 50, 200, null);
		g.setColor(new Color(192, 192, 192, 255));
		g.drawString("2", 80, 220);
		g.drawString(names.get(1).split(":")[1], 150, 220);
		g.drawString(names.get(1).split(":")[0], 400, 220);
	}

	private void paintTop3(Graphics g) {
		g.drawImage(bronce, 50, 230, null);
		g.setColor(new Color(205, 127, 50, 255));
		g.drawString("3", 80, 250);
		g.drawString(names.get(2).split(":")[1], 150, 250);
		g.drawString(names.get(2).split(":")[0], 400, 250);
	}

	private void paintRestTop(Graphics g) {
		g.setColor(new Color(0, 0, 0, 170));
		/* 4 */
		g.drawString("4", 80, 280);
		g.drawString(names.get(3).split(":")[1], 150, 280);
		g.drawString(names.get(3).split(":")[0], 400, 280);

		/* 5 */
		g.drawString("5", 80, 310);
		g.drawString(names.get(4).split(":")[1], 150, 310);
		g.drawString(names.get(4).split(":")[0], 400, 310);
	}

	private void paintPlayButton(Graphics g) {
		g.drawImage(buttonBackSelected, 150, 500, 300, 50, null);
		g.drawString(idioma.getProperty("atras"), 250, 535);
	}

	private void paintMenu(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(0, 0, 0, 170));
		g.drawString(idioma.getProperty("top10"), 200, 50);
		g.setFont(menuFont);
		g.drawString(idioma.getProperty("pos"), 50, 150);
		g.drawString(idioma.getProperty("puntuacion"), 150, 150);
		g.drawString(idioma.getProperty("nombre"), 400, 150);
		g.setColor(new Color(255, 255, 255));
		//g.setFont(menuFont);
	}
}
