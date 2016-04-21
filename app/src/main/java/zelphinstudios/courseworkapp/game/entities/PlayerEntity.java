package zelphinstudios.courseworkapp.game.entities;

public class PlayerEntity extends BaseEntity {

	// Variables
	private int direction;


	// Constructor
	public PlayerEntity(int x_, int y_) {
		x = x_ * 96;
		y = y_ * 96;
		direction = 1;
	}


	// Getters
	public int getDirection() {
		return direction;
	}


	// Setters
	public void changeY(int change_) {
		y += change_;
	}
}
