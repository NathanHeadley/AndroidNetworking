package zelphinstudios.courseworkapp.game.entities;

public class ObjectEntity extends BaseEntity {

	// Variables
	private int id;


	// Constructors
	public ObjectEntity(int id_, int x_, int y_) {
		id = id_;
		x = x_ * 96;
		y = y_ * 96;
	}


	// Getters
	public int getId() {
		return id;
	}

}
