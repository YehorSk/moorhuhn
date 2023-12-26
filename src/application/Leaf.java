package application;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Leaf extends ImageView{
	private int spr_am = 19;
	private int spr_am_dead = 22;
	private Image[] sprites = new Image[spr_am];
	private Image[] sprites_dead = new Image[spr_am_dead];
	private Timeline t;
	private int cur_img = 0;
	private int cur_dead_img = 0;
	private BorderPane root;
	private Ammunition ammunition;
	private Game game;
	private Sounds sounds = new Sounds();
	private boolean isDead;

	public Leaf(BorderPane root,float x,Ammunition ammunition,Game game) {
		super();
		this.ammunition = ammunition;
		this.game = game;
		this.isDead = false;
		for(int i = 0; i < spr_am; i++) { 
			sprites[i] = new Image(new File("resources/leaf/leaf__"+i+".png").toURI().toString(), 75, 75, false, false);
        }
		for(int i = 0; i < spr_am; i++) { 
			sprites_dead[i] = new Image(new File("resources/leaf_destr/destroyed_leaf_"+i+".png").toURI().toString(), 75, 75, false, false);
        }
		this.root = root;
		t = new Timeline(new KeyFrame(Duration.millis(50),evt->{
			draw();
			move();
		}));
		
		t.setCycleCount(Animation.INDEFINITE);
		t.play();
		draw();
		setOnMousePressed(evt -> {
        	onClick();
        	evt.consume();
        });
		this.setLayoutX(x);
	}
	public void onClick() {
		if(!this.isDead) {
			if(this.ammunition.getAmmo()>0) {
				kill();
				this.game.addScore("LEAF");
				this.ammunition.useAmmo();
				sounds.shot();
				sounds.shotLeaf();
				this.isDead = true;
			}else {
				sounds.empty();
			}
		}
	}
	public void kill() {
		t.pause();
		t = new Timeline(new KeyFrame(Duration.millis(100),evt->{
			draw_dead();
			move_dead();
		}));
		t.setCycleCount(22);
		t.setOnFinished(e->this.root.getChildren().remove(this));
		t.play();
	}
	public void draw() {
		setImage(sprites[cur_img]);
		if(cur_img==18) {
			cur_img = 0;
		}
		cur_img++;
	}
	public void draw_dead() {
		setImage(sprites_dead[cur_dead_img]);
		if(cur_dead_img==21) {
			cur_dead_img = 0;
		}
		cur_dead_img++;
	}
	public void stop() {
		t.pause();
		kill();
	}
	public void play() {
		t.play();
	}
	public void move() {
		if(this.getLayoutX()>=root.getHeight()) {
			this.root.getChildren().remove(this);
		}
		this.setLayoutY(this.getLayoutY()+5);
	}
	public void move_dead() {
		this.setLayoutY(this.getLayoutY()+5);
	}
}
