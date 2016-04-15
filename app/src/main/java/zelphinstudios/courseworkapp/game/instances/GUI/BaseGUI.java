package zelphinstudios.courseworkapp.game.instances.GUI;

import android.graphics.Bitmap;

public class BaseGUI {

	// Variables
	protected int x, y, width, height;
	protected Bitmap bitmap;
	protected boolean visible;


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
	public void setBitmap(Bitmap bitmap_) {
		bitmap = bitmap_;
	}
	public void setVisible(boolean visible_) {
		visible = visible_;
	}
}
