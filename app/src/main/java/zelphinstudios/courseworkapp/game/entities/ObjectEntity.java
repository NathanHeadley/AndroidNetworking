package zelphinstudios.courseworkapp.game.entities;

public class ObjectEntity extends BaseEntity {

	// Variables
	private int id;


	// Constructors
	public ObjectEntity(int id_, int x_, int y_) {
		id = id_;
		x = x_ * 96;
		y = y_ * 96;
		visible = true;
	}
	public ObjectEntity(int id_, int x_, int y_, boolean visible_) {
		id = id_;
		x = x_ * 96;
		y = y_ * 96;
		visible = visible_;
	}


	// Getters
	public int getId() {
		return id;
	}

}
