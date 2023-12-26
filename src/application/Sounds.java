package application;

import java.io.File;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sounds extends Group {
    private Random random;
    private MediaPlayer backgroundMediaPlayer;

    public Sounds() {
        random = new Random();
        Media backgroundMusic = new Media(new File("resources/sounds/ambientloop.mp3").toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }


    public void playMain() {
        backgroundMediaPlayer.play();
    }

    public void stopMain() {
        backgroundMediaPlayer.stop();
    }

    public void shot() {
        playSound("resources/sounds/gunblast.mp3");
    }
    public void shotLeaf() {
        playSound("resources/sounds/leaf-hit.mp3");
    }

    public void empty() {
        playSound("resources/sounds/empty magazine.mp3");
    }

    public void reload() {
        playSound("resources/sounds/gun-reload.mp3");
    }
    public void time() {
        playSound("resources/sounds/time_running.mp3");
    }
    public void over() {
        playSound("resources/sounds/game_over.mp3");
    }
    public void start() {
    	Media start = new Media(new File("resources/sounds/game_start.mp3").toURI().toString());
        MediaPlayer mediaStart = new MediaPlayer(start);
        mediaStart.play();
    }

    public void kill() {
        String hits[] = {"chick_hit1.mp3", "chick_hit2.mp3", "chick_hit3.mp3"};
        playSound("resources/sounds/" + hits[random.nextInt(3)]);
    }

    private void playSound(String path) {
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}

