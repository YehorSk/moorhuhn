package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Ammunition extends Group{
	private HBox ammo;
	private BorderPane root;
	private int max_amount = 8;
	private int ammo_amount = 8;
	private boolean stop;
	
	public Ammunition(BorderPane root) {
		this.root = root;
		ammo = new HBox();
		drawAmmo();
		stop = false;
		this.root.setBottom(ammo);
	}
	public void drawAmmo() {
		ammo.getChildren().clear();
		for(int i = 0;i<ammo_amount;i++) {
			ImageView new_ammo = new ImageView();
			new_ammo.setImage(new Image("shell.png", 50, 100, false, false));
			ammo.getChildren().add(new_ammo);
		}
	}
	public void useAmmo() {
		if(!stop) {
			if(ammo_amount>=0) {
				ammo_amount-=1;
			}
			drawAmmo();
		}
		
	}
	public void updateAmmo() {
		ammo_amount = max_amount;
		drawAmmo();
	}
	public void clearAmmo() {
		ammo_amount = 0;
		drawAmmo();
	}
	public int getAmmo() {
		return ammo_amount;
	}
}
