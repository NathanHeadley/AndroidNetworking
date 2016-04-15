package zelphinstudios.courseworkapp.game.entities;

public class PlayerEntity extends BaseEntity {

	// Variables
	private int direction;


	// Constructor
	public PlayerEntity(int x_, int y_) {
		x = x_;
		y = y_;
		direction = 1;
		visible = true;
	}


	// Getters
	public int getDirection() {
		return direction;
	}

}
