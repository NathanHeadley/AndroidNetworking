package zelphinstudios.courseworkapp.system.networking.databases;

public class Object {

	private int id, x, y;

	public Object(int id_, int x_, int y_) {
		id = id_;
		x = x_;
		y = y_;
	}

	public int getId() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void setId(int id_) {
		id = id_;
	}
	public void setX(int x_) {
		x = x_;
	}
	public void setY(int y_) {
		y = y_;
	}
}
