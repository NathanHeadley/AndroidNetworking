package zelphinstudios.courseworkapp.game.instances;

import android.graphics.Bitmap;

import zelphinstudios.courseworkapp.game.entities.PlayerEntity;

public class Player {
	
	// Variables
	private PlayerEntity playerEntity;
	private int score = 0;
	private Bitmap[] bitmap = new Bitmap[4];


	// Constructor
	public Player() {
		playerEntity = new PlayerEntity(10, 6);
	}


	// Getters
	public int getScore() {
		return score;
	}


	// Setters
	public PlayerEntity getEntity() {
		return playerEntity;
	}
	public void setScore(int score_) {
		score = score_;
	}
	public void addScore(int score_) {
		score += score_;
	}
	public Bitmap getBitmap(int bitmap_) {
		return bitmap[bitmap_];
	}

}
