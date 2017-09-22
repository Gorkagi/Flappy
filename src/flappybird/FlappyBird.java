/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class FlappyBird implements ActionListener, KeyListener {

	public static final int FPS = 60, WIDTH = 600, HEIGHT = 600;
	public final String MAIN_THEME = "audio/MenuTema.mp3";
	public final String MOVE_SOUND = "audio/MenuMove.mp3";
	public final String SELECT_SOUND = "audio/MenuSelect.mp3";

	private Bird bird;
	private JFrame frame;
	private JPanel panel;
	private MenuPanel menuPanel;
	private RankingPanel rankingPanel;
	private ArrayList<Rectangle> rects;
	private int time, scroll;
	private Timer t;

	private boolean paused;
	private boolean inMenu;
	private boolean inGame = false;

	MusicPlayer player;

	private int menuCount = 0;

	public void go() {
		bird = new Bird();
		rects = new ArrayList<Rectangle>();
		panel = new GamePanel(this, bird, rects);
		frame.add(panel);

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		paused = true;

		t = new Timer(1000 / FPS, this);
		t.start();
		inGame = true;
	}

	public void menu() {

		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
			frame.addKeyListener(this);
		}
		menuPanel = new MenuPanel();
		frame.add(menuPanel);

		inMenu = true;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		player = new MusicPlayer(MAIN_THEME);
		player.play();
	}

	public void ranking() {
		if (frame == null) {
			frame = new JFrame("Flappy Bird");
			frame.setResizable(false);
		}
		rankingPanel = new RankingPanel();
		frame.remove(menuPanel);
		frame.add(rankingPanel);
		frame.repaint();

		inMenu = false;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new FlappyBird().menu();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		panel.repaint();
		if (!paused) {
			bird.physics();
			if (scroll % 90 == 0) {
				Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W,
						(int) ((Math.random() * HEIGHT) / 5f + (0.2f) * HEIGHT));
				int h2 = (int) ((Math.random() * HEIGHT) / 5f + (0.2f) * HEIGHT);
				Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
				rects.add(r);
				rects.add(r2);
			}
			ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
			boolean game = true;
			for (Rectangle r : rects) {
				r.x -= 3;
				if (r.x + r.width <= 0) {
					toRemove.add(r);
				}
				if (r.contains(bird.x, bird.y)) {
					JOptionPane.showMessageDialog(frame, "You lose!\n" + "Your score was: " + time + ".");
					game = false;
				}
			}
			rects.removeAll(toRemove);
			time++;
			scroll++;

			if (bird.y > HEIGHT || bird.y + bird.RAD < 0) {
				game = false;
			}

			if (!game) {
				rects.clear();
				bird.reset();
				time = 0;
				scroll = 0;
				paused = true;
			}
		} else if (inMenu) {
			t.stop();
			frame.remove(panel);
			frame.repaint();
		}
	}

	public int getScore() {
		return time;
	}

	public void keyPressed(KeyEvent e) {

		if (inMenu) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN && menuCount != 4) {
				menuCount++;
				reproducir(MOVE_SOUND);
				menuPanel.updateMenuPosition(menuCount);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && menuCount > 0) {
				menuCount--;
				reproducir(MOVE_SOUND);
				menuPanel.updateMenuPosition(menuCount);
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				reproducir(SELECT_SOUND);
				switch (menuCount) {
				case 0:
					go();
					player.close();
					break;
				case 1:
					ranking();
					break;
				case 4:
					frame.dispose();
					player.close();
					break;

				default:
					break;
				}
				inMenu = false;
			}

		} else if (inGame) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				paused = true;
				inMenu = true;
				frame.remove(panel);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				bird.jump();
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				paused = false;
			}
		} else {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				frame.remove(rankingPanel);
				frame.add(menuPanel);
				frame.repaint();
				inMenu = true;
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public boolean paused() {
		return paused;
	}

	public void reproducir(String mp3) {
		MusicPlayer player = new MusicPlayer(mp3);
		player.play();
	}
}
