package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RankingPanel extends JPanel {

	private Font titleFont, menuFont;
	private Image menuBack;
	private Image buttonBackSelected;

	public RankingPanel() {
		titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
		menuFont = new Font("8BIT WONDER", Font.BOLD, 20);

		try {
			menuBack = ImageIO.read(new File("MenuImage.jpg"));
			buttonBackSelected = ImageIO.read(new File("BotonMenuRetroSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		paintMenu(g);
		paintPlayButton(g);
	}

	private void paintPlayButton(Graphics g) {
		g.drawImage(buttonBackSelected, 150, 100, 300, 50, null);
		g.drawString("Back", 250, 135);
	}

	private void paintMenu(Graphics g) {
		g.setFont(titleFont);
		g.drawImage(menuBack, 0, 0, 600, 600, null);
		g.setColor(new Color(0, 0, 0, 170));
		g.drawString("TOP 10", 200, 50);
		g.setColor(new Color(255, 255, 255));
		g.setFont(menuFont);
	}
}
