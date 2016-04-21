package zelphinstudios.courseworkapp.game.entities;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.util.BitmapDecoder;

public class Entities {

	private Vector<ObjectEntity> objectEntities = new Vector<>();
	private Vector<PlayerEntity> playerEntities = new Vector<>();
	private Bitmap barrier, food;
	private Bitmap[] player = new Bitmap[4];


	public Entities(Context context_) {
		BitmapDecoder bitmapDecoder = new BitmapDecoder(context_);
		barrier = bitmapDecoder.decode("barrier");
		food = bitmapDecoder.decode("food");
		player[0] = bitmapDecoder.decode("player_north");
		player[1] = bitmapDecoder.decode("player_east");
		player[2] = bitmapDecoder.decode("player_south");
		player[3] = bitmapDecoder.decode("player_west");
	}


	public Vector<ObjectEntity> getObjectEntities() {
		return objectEntities;
	}
	public Vector<PlayerEntity> getPlayerEntities() {
		return playerEntities;
	}
	public PlayerEntity getPlayerEntity(int id_) {
		return playerEntities.get(id_);
	}
	public Bitmap getBarrier() {
		return barrier;
	}
	public Bitmap getFood() {
		return food;
	}
	public Bitmap getPlayer(int direction_) {
		return player[direction_];
	}


	public void addEntities(Vector<ObjectEntity> entities_) {
		objectEntities = entities_;
	}
	public void addEntity(ObjectEntity entity_) {
		objectEntities.addElement(entity_);
	}
	public void addPlayerEntity(PlayerEntity entity_) {
		playerEntities.addElement(entity_);
	}
	public void setY(int y_) {
		playerEntities.get(0).setY(y_);
	}
}
