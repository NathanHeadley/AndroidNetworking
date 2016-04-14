package zelphinstudios.courseworkapp.game.instances;

import android.graphics.Bitmap;

public class Button {
	
	// Variables
	private int x, y, width, height, actionId;
	private Bitmap bitmap;
	private boolean visible;
	
	
	// Constructor
	public Button (int x_, int y_, int width_, int height_,
		int actionId_, Bitmap bitmap_, boolean visible_) {
		x = x_;
		y = y_;
		width = width_;
		height = height_;
		actionId = actionId_;
		bitmap = bitmap_;
		visible = visible_;
	}

	
	// Getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getActionId() {
		return actionId;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public boolean isVisible() {
		return visible;
	}
	
	
	// Setters
	public void setX(int x_) {
		x = x_;
	}
	public void setY(int y_) {
		y = y_;
	}
	public void setWidth(int width_) {
		width = width_;
	}
	public void setHeight(int height_) {
		height = height_;
	}
	public void setActionId(int actionId_) {
		actionId = actionId_;
	}
	public void setBitmap(Bitmap bitmap_) {
		bitmap = bitmap_;
	}
	public void setVisible(boolean visible_) {
		visible = visible_;
	}
}
