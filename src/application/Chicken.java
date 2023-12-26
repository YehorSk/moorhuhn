package application;

import java.io.File;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Chicken extends ImageView{
	private int spr_am = 13;
	private int spr_am_dead = 8;
	private Image[] sprites = new Image[spr_am];
	private Image[] sprites_dead = new Image[spr_am];
	private String direction;
	private Integer[] sizes = {140,152,100,108,60,65};
	private String size;
	private Timeline t;
	private BorderPane root;
	private int cur_img = 0;
	private int cur_dead_img = 0;
	private int width,height;
	private Ammunition ammunition;
	private Game game;
	private Sounds sounds = new Sounds();
	private boolean isDead;
	
	public Chicken(String size,String direction,BorderPane root,float y,Ammunition ammunition,Game game) {
		super();
		this.size = size;
		setSize();
		this.ammunition = ammunition;
		this.game = game;
		this.isDead = false;
		for(int i = 0; i < spr_am; i++) { 
			sprites[i] = new Image(new File("resources/bird_"+size+"/bird_"+direction+"_"+size+"_"+i+".png").toURI().toString(), width+10, height+10, false, false);
        }
		for(int i = 0; i < spr_am_dead; i++) {
			sprites_dead[i] = new Image(new File("resources/bird_dead_"+size+"/bird_dead_"+direction+"_"+size+"_"+i+".png").toURI().toString(), width+10, height+10, false, false);
        }
		this.direction = direction;
		this.root = root;
		t = new Timeline(new KeyFrame(Duration.millis(50),evt->{
			draw();
			move();
		}));
		
		t.setCycleCount(Animation.INDEFINITE);
		t.play();
		draw();
        switch(this.direction) {
        case "right":
        	this.setLayoutX(-100);
		break;
		case "left":
			this.setLayoutX(root.getWidth());
		break;
        }
        setOnMousePressed(evt -> {
        	onClick();
        	evt.consume();
        });
        this.setLayoutY(y);
	}
	public void onClick() {
		if(!this.isDead) {
		if(this.ammunition.getAmmo()>0) {
			kill();
			this.game.addScore(getSize());
			this.ammunition.useAmmo();
			sounds.shot();
			sounds.kill();
			this.isDead = true;
		}else {
			sounds.empty();
		}
		}
	}
	public void stop() {
		t.pause();
		kill();
	}
	public void play() {
		t.play();
	}
	public String getSize() {
		return this.size;
	}
	public void setSize() {
		switch(this.size) {
		case "L":
			width = sizes[0];
			height = sizes[1];
		break;
		case "M":
			width = sizes[2];
			height = sizes[3];
		break;
		case "S":
			width = sizes[4];
			height = sizes[5];
		break;
		}
	}
	public void kill() {
		t.pause();
		t = new Timeline(new KeyFrame(Duration.millis(100),evt->{
			draw_dead();
			move_dead();
		}));
		t.setCycleCount(8);
		t.setOnFinished(e->this.root.getChildren().remove(this));
		t.play();
	}

	public void move_dead() {
		this.setLayoutY(this.getLayoutY()+15);
	}
	public void move() {
		
		switch(this.direction) {
		case "right":
			this.setLayoutX(this.getLayoutX()+5);
		break;
		case "left":
			this.setLayoutX(this.getLayoutX()-5);
		break;
		}
		if(this.getLayoutX()<=-100 || this.getLayoutX()>=root.getWidth()) {
			this.root.getChildren().remove(this);
		}
	}
	public void draw() {
		setImage(sprites[cur_img]);
		if(cur_img==12) {
			cur_img = 0;
		}
		cur_img++;
	}
	public void draw_dead() {
		setImage(sprites_dead[cur_dead_img]);
		if(cur_dead_img==8) {
			cur_dead_img = 0;
		}
		cur_dead_img++;
	}
}
