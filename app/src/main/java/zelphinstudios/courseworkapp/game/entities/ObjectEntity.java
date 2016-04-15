package zelphinstudios.courseworkapp.game.entities;

public class ObjectEntity extends BaseEntity {

	// Variables
	private int id;


	// Constructors
	public ObjectEntity(int id_, int x_, int y_) {
		id = id_;
		x = x_;
		y = y_;
		visible = true;
	}
	public ObjectEntity(int id_, int x_, int y_, boolean visible_, boolean walkable_) {
		id = id_;
		x = x_;
		y = y_;
		visible = visible_;
	}


	// Getters
	public int getId() {
		return id;
	}

}
