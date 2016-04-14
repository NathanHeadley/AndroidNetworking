package zelphinstudios.courseworkapp.game.instances;

import android.graphics.Bitmap;

public class Player extends GameObject {
	
	// Variables
	int score = 0;


	// Constructor
	public Player() {
		super(0, 0, 32, 32, null, true, true);
	}
	public Player(int x_, int y_, int width_, int height_, Bitmap bitmap_, boolean visible_, boolean solid_) {
		super(x_, y_, width_, height_, bitmap_, visible_, solid_);
	}


	// Getters
	public int getScore() {
		return score;
	}


	// Setters
	public void setScore(int score_) {
		score = score_;
	}
	public void addScore(int score_) {
		score += score_;
	}
}
