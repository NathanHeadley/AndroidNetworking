package zelphinstudios.courseworkapp.game.entities;

// Class to store player entities
public class PlayerEntity extends BaseEntity {

	// Variables
	private int direction, score;


	// Constructor
	public PlayerEntity(int x_, int y_) {
		x = x_ * 96;
		y = y_ * 96;
		direction = 1;
		score = 0;
	}


	// Getters
	public int getDirection() {
		return direction;
	}
	public int getScore() {
		return score;
	}


	// Setters
	public void setDirection(int direction_) {
		direction = direction_;
	}
	public void addScore(int score_) {
		score += score_;
	}
	public void setScore(int score_) {
		score = score_;
	}
}
