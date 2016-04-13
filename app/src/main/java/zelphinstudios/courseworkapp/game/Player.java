package zelphinstudios.courseworkapp.game;

import android.graphics.Bitmap;

public class Player extends GameObject {
	
	// Variables
	int score = 0;
	
	// Constructor
	public Player(int x_, int y_, int width_, int height_,

		Bitmap bitmap_, boolean visible_, boolean solid_) {
		super(x_, y_, width_, height_, bitmap_, visible_, solid_);
	}
}
