package zelphinstudios.courseworkapp.game.instances;

import android.graphics.Bitmap;

public class Object {
	
	// Variables
	private Bitmap bitmap;
	private boolean walkable;

	
	// Constructors
	public Object(Bitmap bitmap_, boolean walkable_) {
		bitmap = bitmap_;
		walkable = walkable_;
	}

	
	// Getters
	public Bitmap getBitmap() {
		return bitmap;
	}
	public boolean getWalkable() {
		return walkable;
	}
	
	// Setters
	public void setBitmap(Bitmap bitmap_) {
		bitmap = bitmap_;
	}
	public void setWalkable(boolean walkable_) {
		walkable = walkable_;
	}
}
