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
public class FlappyBird implements ActionListener, KeyListener {

	public static final int WIDTH = 600, HEIGHT = 600;
	public final String MAIN_THEME = "audio/MenuTema.mp3";
	public final String MOVE_SOUND = "audio/MenuMove.mp3";
	public final String SELECT_SOUND = "audio/MenuSelect.mp3";
	public final String COLISION_SOUND = "audio/Colision.mp3";
	public final String WINGS_SOUND = "audio/Wings.mp3";
	public static final String OPTIONS_FILE = "options.txt";
	public static final String ES_PROPERTIES = "es.properties";
	public static final String EN_PROPERTIES = "en.properties";

	private Bird bird;
	private JFrame frame;
	private JPanel gamePanel;
	private MenuPanel menuPanel;
	private RankingPanel rankingPanel;
	private InstructionsPanel instructionsPanel;
	private OptionsPanel optionsPanel;
	private GameOverPanel gameOverPanel;
	private ArrayList<Rectangle> rects;
	private int time, scroll;
	private Timer t;
	
	private static int FPS = 75;
	
	static int frequency = 3;

	private boolean paused;
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
		bird = new Bird();
		rects = new ArrayList<Rectangle>();
		gamePanel = new GamePanel(this, bird, rects);

		frame.remove(menuPanel);
		frame.add(gamePanel);
		frame.repaint();

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		t = new Timer(1000 / FPS, this);
		t.setDelay(10);
		t.start();
		
		inGame = true;
		paused = true;
		inMenu = false;
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
	
	public void gameOver() {
		if (frame == null) {
			frame = new JFrame("Flappy");
			frame.setResizable(false);
		}
		
		
		gameOverPanel = new GameOverPanel();
		frame.remove(gamePanel);
		frame.add(gameOverPanel);
		frame.repaint();
		
		inGame = false;
		paused = true;
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

	@Override
	public void actionPerformed(ActionEvent e) {

		gamePanel.repaint();
		if (!paused) {
			bird.physics();
			if (scroll % (90/(frequency/2)) == 0) {
				Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W,
						(int) ((Math.random() * HEIGHT) / 3f + HEIGHT / 4.8f));
				int h2 = (int) (HEIGHT - 150f - r.getHeight());
				Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
				rects.add(r);
				rects.add(r2);
			}
			ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
			boolean game = true;
			for (Rectangle r : rects) {
				r.x -= frequency;
				if (r.x + r.width <= 0) {
					toRemove.add(r);
				}
				if (r.contains(bird.x, bird.y)) {
					
					if (GamePanel.player.isAlive()) {
						
						GamePanel.player.close();
					} 
					
					if (GamePanel.player1.isAlive()) {
						
						GamePanel.player1.close();
					} 
					
					if (GamePanel.player2.isAlive()) {
						
						GamePanel.player2.close();
					} 
					
					GamePanel.killPlayer = true;
					GamePanel.killPlayer1 = true;
					GamePanel.killPlayer2 = true;
					reproducir(COLISION_SOUND);
										
					gameOver();
			
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
		}
	}

	public int getScore() {
		return time;
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
				paused = true;
				inMenu = true;
				inGame = false;
				frame.remove(gamePanel);
				frame.add(menuPanel);
				frame.repaint();
				
				if (opciones.getProperty("musica").equals("Si")) {
					player = new MusicPlayer(MAIN_THEME);
					player.play();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				bird.jump();
				reproducir(WINGS_SOUND);
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				paused = false;
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
					t.stop();
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
					t.stop();
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
	
	public void setSpeedFPS(int delay) {
		t.setDelay(delay);
	}

	public boolean paused() {
		return paused;
	}

	public void reproducir(String mp3) {
		MusicPlayer player = new MusicPlayer(mp3);
		player.play();
	}
}
