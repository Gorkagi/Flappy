package flappybird;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Thread {
	private final double DESIRED_FPS = 60d;
	private final long SLEEP = (long) (GameTimer.ONE_SECOND / DESIRED_FPS);

	public final static String GAME = "audio/Game.mp3";
	public final static String GAME1 = "audio/GameX1.mp3";
	public final static String GAME2 = "audio/GameX2.mp3";

	public final static int LVL1 = 0;
	public final static int LVL2 = 350;
	public final static int LVL3 = 600;

	private FlappyBird fb;
	private Bird bird;
	private ArrayList<Rectangle> rects;
	private GamePanel gamePanel;

	private int scroll = 0;
	private int score = 0;
	private int frequency = 2;
	private MusicPlayer player;

	private boolean paused = true;
	private boolean playing = true;

	public Game(FlappyBird fb) {
		this.fb = fb;
	}

	@Override
	public void run() {
		rects = new ArrayList<Rectangle>();
		bird = new Bird();
		gamePanel = new GamePanel(this, bird, rects);

		fb.getFrame().add(gamePanel);
		fb.getFrame().repaint();

		fb.getFrame().setSize(FlappyBird.WIDTH, FlappyBird.HEIGHT);
		fb.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fb.getFrame().setVisible(true);

		GameTimer timer = new GameTimer();
		timer.start();

		try {
			while (playing) {

				timer.tick();

				gamePanel.repaint();

				if (!paused) {
					if (score == LVL1) {
						player = new MusicPlayer(GAME);
						player.play();
					}

					if (score == LVL2) {
						player.close();
						player = new MusicPlayer(GAME1);
						player.play();
					}

					if (score == LVL3) {
						player.close();
						player = new MusicPlayer(GAME2);
						player.play();
					}

					bird.physics();

					if (scroll % (90 / (frequency / 2)) == 0) {
						Rectangle r = new Rectangle(FlappyBird.WIDTH, 0, GamePanel.PIPE_W,
								(int) ((Math.random() * FlappyBird.HEIGHT) / 3f + FlappyBird.HEIGHT / 4.8f));
						int h2 = (int) (FlappyBird.HEIGHT - 150f - r.getHeight());
						Rectangle r2 = new Rectangle(FlappyBird.WIDTH, FlappyBird.HEIGHT - h2, GamePanel.PIPE_W, h2);
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
							fb.reproducir(FlappyBird.COLISION_SOUND);
							fb.gameOver();

							game = false;
							playing = false;
						}
					}
					rects.removeAll(toRemove);
					scroll++;
					score++;

					if (bird.y > FlappyBird.HEIGHT || bird.y + bird.RAD < 0) {
						game = false;
					}

					if (!game) {
						if (player.isAlive()) {
							player.close();
							player = null;
						}

						rects.clear();
						bird.reset();
						scroll = 0;
						score = 0;
						paused = true;
					}
				}

				long wake = System.nanoTime() + SLEEP - timer.tock();
				do {
					Thread.sleep(0);
				} while (System.nanoTime() < wake);
			}
		} catch (InterruptedException e) {

		}

	}

	public int getScore() {
		return score;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isPaused() {
		return paused;
	}

	public void jump() {
		this.bird.jump();
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void killPlayer() {
		playing = false;
		
		if (player != null) {
			if (player.isAlive()) {
				player.close();
				player = null;
			}
		}

	}

}
