package flappybird;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer extends Thread {

	Player player;
	String musicPath;

	public MusicPlayer(String musicPath) {
		this.musicPath = musicPath;
		try {
			player = new Player(new FileInputStream(musicPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		this.start();

	}

	public void close() {
		player.close();
	}

}
