package zelphinstudios.courseworkapp.game.entities;

// Class to store object entities
public class ObjectEntity extends BaseEntity {

	// Variables
	private int id;
	private boolean visible;


	// Constructor
	public ObjectEntity(int id_, int x_, int y_) {
		id = id_;
		x = x_ * 96;
		y = y_ * 96;
		visible = true;
	}


	// Getters
	public int getId() {
		return id;
	}
	public boolean isVisible() {
		return visible;
	}


	// Setters
	public void setVisible(boolean visible_) {
		visible = visible_;
	}
}
