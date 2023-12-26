package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Game extends Group{
	
	private ImageView background;
	private Sounds sounds = new Sounds();
	private Timeline timeline;
	private Image cursor = new Image("aim.png");
	private Generator gen;
	private BorderPane root;
	private Ammunition am;
	private Text textScore = new Text();
	private Text textInstruction = new Text();
	private int seconds = 90;
	private int max_seconds = 90;
	private Text timer;
	private int score = 0;
	private Scene scene;
	
	public Game(BorderPane root,Scene scene) {
		this.root = root;
		setStartBG();
		this.scene = scene;
		setCursor();

		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
		setText();
		instructions();
	}
	public void setGen(Generator gen) {
		this.gen = gen;
	}
	public void setAm(Ammunition am) {
		this.am = am;
	}
	public void setCursor() {
		scene.setCursor(new ImageCursor(cursor,
				cursor.getWidth(),
				cursor.getHeight()));
	}
	private void removeCursor() {
		scene.setCursor(Cursor.DEFAULT);
	}
	public void instructions() {
		textInstruction = new Text();
		textInstruction.setX(root.getWidth()/4); 
		textInstruction.setY(root.getHeight()/2);
		textInstruction.setFont(Font.font("Cambria", 40));
		textInstruction.setFill(Color.WHITE);
		textInstruction.setText("TO RELOAD PRESS SPACE");
		getChildren().add(textInstruction);
		Timeline instr = new Timeline(new KeyFrame(Duration.seconds(5),evt ->getChildren().remove(textInstruction)));
		instr.setCycleCount(1);
		instr.play();
	}
	public void setStartBG() {
		getChildren().remove(background);
		Image bg = new Image("bg.jpg", root.getWidth(), root.getHeight(), false, false);
		background = new ImageView(bg);
		getChildren().add(background);
	}
	public void setOverBG() {
		getChildren().remove(background);
		Image bg = new Image("over_bg.png", root.getWidth(), root.getHeight(), false, false);
		background = new ImageView(bg);
		getChildren().add(background);
	}
	
	public void results() {
		Text textResults = new Text();
	    textResults.setFont(Font.font("Cambria", 40));
	    textResults.setFill(Color.WHITE);
	    textResults.setText("YOUR SCORE IS " + score);

	    Button continueButton = new Button("Continue");

	    double centerX = root.getWidth() / 2;
	    textResults.setX(centerX - textResults.getLayoutBounds().getWidth() / 2);
	    continueButton.setLayoutX(centerX - continueButton.getLayoutBounds().getWidth() / 2);

	    double centerY = root.getHeight() / 2;
	    textResults.setY(centerY);
	    continueButton.setLayoutY(centerY + 50);

	    getChildren().addAll(textResults, continueButton);
        continueButton.setOnAction(event -> {
            gen.play();
            timeline.play();
            seconds = max_seconds;
            score = 0;
            updateScore();
            getChildren().removeAll(textResults, continueButton);
            setStartBG();
            sounds.start();
            timer.setFill(Color.WHITE);
            setText();
    		instructions();
    		am.updateAmmo();
    		setCursor();
        });
        
    }

	public void updateTimer() {
		if(seconds <=10) {
			sounds.time();
			timer.setFill(Color.RED);
		}
		if(seconds>=1) {
			seconds--;
		}else {
			gen.stop();
			timeline.stop();
			am.clearAmmo();
			setOverBG();
			results();
			sounds.over();
			gen.removeAllChicken();
			gen.removeAllLeaf();
			removeCursor();
		}
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        timer.setText(String.format("%d:%02d", minutes, remainingSeconds));
	}
	public void setText() {
		textScore = new Text();
		textScore.setX(root.getWidth()-100); 
		textScore.setY(50);
		textScore.setFont(Font.font("Cambria", 40));
		textScore.setFill(Color.WHITE);
		textScore.setText("0");
		getChildren().add(textScore);
		timer = new Text();
		timer.setX(50); 
		timer.setY(50);
		timer.setFont(Font.font("Cambria", 40));
		timer.setFill(Color.WHITE);
		timer.setText("1:30");
		getChildren().add(timer);
	}
	public void addScore(String size) {
		switch(size) {
		case "L":
			score+=10;
		break;
		case "M":
			score+=15;
		break;
		case "S":
			score+=20;
		break;
		case "LEAF":
			score+=50;
		break;
		}
		updateScore();
	}
	public void updateScore() {
		textScore.setText(""+score);
		
	}

}
