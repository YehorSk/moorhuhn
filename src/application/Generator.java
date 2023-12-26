package application;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Generator extends Group{
	private Timeline t;
	private String[] sizes = {"L","M","S"};
	private BorderPane root;
	private Game game;
	private Random random = new Random();
	private String directions[] = {"right","left"};
	private Ammunition ammunition;
	public Generator(BorderPane root,Ammunition ammunition,Game game) {
		this.root = root;
		this.ammunition = ammunition;
		this.game = game;
		
		t = new Timeline(new KeyFrame(Duration.seconds(1),evt->{
			addChicken();
			addLeaf();
		}));
		t.setCycleCount(Animation.INDEFINITE);
		t.play();
	}
	public void addChicken() {
		Chicken ch = new Chicken(sizes[random.nextInt(3)],directions[random.nextInt(2)],root,(float)(random.nextDouble(root.getHeight() - 100)),ammunition,game);
		this.root.getChildren().add(ch);
	}
	
	public void addLeaf() {
		Leaf leaf = new Leaf(this.root,random.nextInt((int) root.getWidth())-200,ammunition,game);
		this.root.getChildren().add(leaf);
	}
	public void removeAllChicken() {
		for(int i = 0; i<root.getChildren().size();i++) {
			Node my = root.getChildren().get(i);
			if(my instanceof Chicken) ((Chicken)my).stop();
		}
	}
	public void removeAllLeaf() {
		for(int i = 0; i<root.getChildren().size();i++) {
			Node my = root.getChildren().get(i);
			if(my instanceof Leaf) ((Leaf)my).stop();
		}
	}
	public void stop() {
		t.pause();
	}
	public void play() {
		t.play();
	}
}
