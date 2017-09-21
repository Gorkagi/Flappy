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
	
    public MenuPanel() {
        titleFont = new Font("8BIT WONDER", Font.BOLD, 34);
        menuFont = new Font("8BIT WONDER", Font.BOLD, 20);
        
        try {
        	menuBack = ImageIO.read(new File("MenuImage.jpg"));
        	buttonBack = ImageIO.read(new File("BotonMenuRetro.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateMenuPosition(int position) {
    	System.out.println("Menu Position Updated to "+position);
    }
    
    @Override
    public void paintComponent(Graphics g) {
            g.setFont(titleFont);
            g.drawImage(menuBack, 0, 0, 600, 600, null);
            g.setColor(new Color(0,0,0,170));
            g.drawString("Flappy", 200, 50);
            g.setColor(new Color(255,255,255));
            g.setFont(menuFont);
            g.drawImage(buttonBack, 150, 100, 300, 50, null);
            g.drawString("Jugar", 250, 135);
            g.drawImage(buttonBack, 150, 170, 300, 50, null);
            g.drawString("Ranking", 240, 205);
            g.drawImage(buttonBack, 150, 240, 300, 50, null);
            g.drawString("Instrucciones", 175, 275);
            g.drawImage(buttonBack, 150, 310, 300, 50, null);
            g.drawString("Opciones", 225, 345);
            g.drawImage(buttonBack, 150, 380, 300, 50, null);
            g.drawString("Salir", 255, 415);
        }

}
