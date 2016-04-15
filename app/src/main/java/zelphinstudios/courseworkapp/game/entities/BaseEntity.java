package zelphinstudios.courseworkapp.game.entities;

public class BaseEntity {

	// Variables
	protected int x, y;
	protected boolean visible;


	// Getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getAbsX() {
		return x / 96;
	}
	public int getAbsY() {
		return y / 96;
	}
	public boolean isVisible() {
		return visible;
	}


	// Setters
	public void setX(int x_) {
		x = x_ * 96;
	}
	public void setY(int y_) {
		y = y_ * 96;
	}
	public void setVisible(boolean visible_) {
		visible = visible_;
	}
}
