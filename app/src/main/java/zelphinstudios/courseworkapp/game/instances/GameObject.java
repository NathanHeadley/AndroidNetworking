package zelphinstudios.courseworkapp.game.instances;

import android.graphics.Bitmap;

public class GameObject {
	
	// Variables
	private int x, y, width, height;
	private Bitmap bitmap;
	private boolean visible, solid;
	
	
	// Constructor
	public GameObject(int x_, int y_, int width_, int height_, Bitmap bitmap_, boolean visible_, boolean solid_) {
		x = x_;
		y = y_;
		width = width_;
		height = height_;
		bitmap = bitmap_;
		visible = visible_;
		solid = solid_;
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
	public Bitmap getBitmap() {
		return bitmap;
	}
	public boolean isVisible() {
		return visible;
	}
	public boolean isSolid() {
		return solid;
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
	public void setSolid(boolean solid_) {
		solid = solid_;
	}
}
