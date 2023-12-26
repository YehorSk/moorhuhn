package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,600);	
			Game game = new Game(root,scene);
			Sounds sounds = new Sounds();
			sounds.start();
			sounds.playMain();
			root.getChildren().add(game);
			Ammunition am = new Ammunition(root);
			Generator gen = new Generator(root,am,game);
			game.setGen(gen);
			game.setAm(am);
				scene.setOnMousePressed(evt->{
					if(am.getAmmo()>0) {
						am.useAmmo();
						sounds.shot();
					}else {
						sounds.empty();
					}
					evt.consume();
				});
			scene.setOnKeyPressed(evt->{
				if(evt.getCode().toString().equals("SPACE")) {
					am.updateAmmo();
					sounds.reload();
				}
			});
			root.getChildren().addAll(gen,am,sounds);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setMinWidth(800);
	        primaryStage.setMinHeight(600);
			primaryStage.setMaxWidth(800);
	        primaryStage.setMaxHeight(600);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
