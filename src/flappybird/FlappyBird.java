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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class FlappyBird implements KeyListener {

	public static final int WIDTH = 600, HEIGHT = 600;
	public final String MAIN_THEME = "audio/MenuTema.mp3";
	public final String MOVE_SOUND = "audio/MenuMove.mp3";
	public final String SELECT_SOUND = "audio/MenuSelect.mp3";
	public final static String COLISION_SOUND = "audio/Colision.mp3";
	public final String WINGS_SOUND = "audio/Wings.mp3";
	public static final String OPTIONS_FILE = "options.txt";
	public static final String ES_PROPERTIES = "es.properties";
	public static final String EN_PROPERTIES = "en.properties";

	private JFrame frame;
	private MenuPanel menuPanel;
	private RankingPanel rankingPanel;
	private InstructionsPanel instructionsPanel;
	private OptionsPanel optionsPanel;
	private GameOverPanel gameOverPanel;
	private Game game;

	private boolean inMenu;
	private boolean inGame = false;
	private boolean inRanking = false;
	private boolean inInstructions = false;
	private boolean inOptions = false;
	private boolean inGameOver = false;

	public static Properties opciones = new Properties();
	public static Properties idioma = new Properties();

	MusicPlayer player;

	private int menuCount = 0;

	public static Properties getOpciones() {
		return opciones;
	}

	public void go() {
		frame.remove(menuPanel);

		inGame = true;
		inMenu = false;

		game = new Game(this);
		game.start();
	}

	public void menu() {

		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
			frame.addKeyListener(this);
		}

		loadLanguage();

		menuPanel = new MenuPanel();
		frame.add(menuPanel);

		inMenu = true;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		if (opciones.getProperty("musica").equals("Si")) {
			player = new MusicPlayer(MAIN_THEME);
			player.play();
		}
	}

	public void ranking() {
		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
		}
		rankingPanel = new RankingPanel();
		frame.remove(menuPanel);
		frame.add(rankingPanel);
		frame.repaint();

		inMenu = false;
		inRanking = true;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void instructions() {
		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
		}
		instructionsPanel = new InstructionsPanel();
		frame.remove(menuPanel);
		frame.add(instructionsPanel);
		frame.repaint();

		inMenu = false;
		inInstructions = true;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void options() {
		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
		}
		optionsPanel = new OptionsPanel();
		frame.remove(menuPanel);
		frame.add(optionsPanel);
		frame.repaint();

		inMenu = false;
		inOptions = true;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void gameOver(int score) {
		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
		}

		gameOverPanel = new GameOverPanel();
		gameOverPanel.setScore(score);
		frame.remove(game.getGamePanel());
		frame.add(gameOverPanel);
		frame.repaint();

		inGame = false;
		game.setPaused(true);
		inGameOver = true;

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new FlappyBird().menu();
	}

	public void loadLanguage() {
		try {
			opciones.load(new FileInputStream(OPTIONS_FILE));

			String language = opciones.getProperty("idioma");

			if (language.equals("Espanol")) {
				idioma.load(new FileInputStream(ES_PROPERTIES));
			}

			if (language.equals("Ingles")) {
				idioma.load(new FileInputStream(EN_PROPERTIES));
			}

		} catch (IOException e) {
			System.out.println("Idioma no cargado");
		}
	}

	public void keyPressed(KeyEvent e) {
		if (inMenu) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN && menuCount < 4) {
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
					if (player != null) {
						player.close();
					}
					break;
				case 1:
					ranking();
					break;
				case 2:
					instructions();
					break;
				case 3:
					options();
					break;
				case 4:
					frame.dispose();
					if (player != null) {
						player.close();
					}
					break;
				default:
					break;
				}
				inMenu = false;
			}

		} else if (inGame) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				game.setPaused(true);
				game.killPlayer();
				inMenu = true;
				inGame = false;
				frame.remove(game.getGamePanel());
				frame.add(menuPanel);
				frame.repaint();

				if (opciones.getProperty("musica").equals("Si")) {
					player = new MusicPlayer(MAIN_THEME);
					player.play();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (!game.isPaused()) {
					game.jump();
					reproducir(WINGS_SOUND);
				} else {
					game.setPaused(false);
				}
			}
		} else if (inRanking) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				reproducir(SELECT_SOUND);
				frame.remove(rankingPanel);
				inRanking = false;
				frame.add(menuPanel);
				frame.repaint();
				inMenu = true;
			}
		} else if (inInstructions) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				reproducir(SELECT_SOUND);
				frame.remove(instructionsPanel);
				inInstructions = false;
				frame.add(menuPanel);
				frame.repaint();
				inMenu = true;
			}
		} else if (inOptions) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (optionsPanel.canExit()) {
					loadLanguage();

					if (opciones.getProperty("musica").equals("Si")) {
						if (player == null) {
							player = new MusicPlayer(MAIN_THEME);
							player.play();
						} else if (!player.isAlive()) {
							player = new MusicPlayer(MAIN_THEME);
							player.play();
						}
					}
					if (opciones.getProperty("musica").equals("No")) {
						if (player != null) {
							player.close();
						}
					}

					reproducir(SELECT_SOUND);
					frame.remove(optionsPanel);
					inOptions = false;
					frame.add(menuPanel);
					frame.repaint();
					inMenu = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (optionsPanel.moveUp()) {
					reproducir(MOVE_SOUND);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (optionsPanel.moveDown()) {
					reproducir(MOVE_SOUND);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (optionsPanel.moveLeft()) {
					reproducir(MOVE_SOUND);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (optionsPanel.moveRight()) {
					reproducir(MOVE_SOUND);
				}
			}
		} else if (inGameOver) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (gameOverPanel.moveUp()) {
					reproducir(MOVE_SOUND);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (gameOverPanel.moveDown()) {
					reproducir(MOVE_SOUND);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				reproducir(SELECT_SOUND);
				switch (gameOverPanel.getMenuPos()) {
				case 0:
					frame.remove(gameOverPanel);
					inGameOver = false;
					// t.stop();
					go();
					break;
				case 1:
					frame.remove(gameOverPanel);
					inGameOver = false;
					frame.add(menuPanel);
					frame.repaint();
					inMenu = true;

					if (opciones.getProperty("musica").equals("Si")) {
						player = new MusicPlayer(MAIN_THEME);
						player.play();
					}
					break;
				case 2:
					frame.dispose();
					break;
				default:
					break;
				}
				inGameOver = false;
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void reproducir(String mp3) {
		MusicPlayer player = new MusicPlayer(mp3);
		player.play();
	}

	public JFrame getFrame() {
		return frame;
	}
}
