package zelphinstudios.courseworkapp.game.handlers;

import android.content.Context;

import java.util.Vector;

import zelphinstudios.courseworkapp.game.entities.ObjectEntity;
import zelphinstudios.courseworkapp.game.instances.Object;
import zelphinstudios.courseworkapp.system.util.BitmapDecoder;

public class ObjectHandler extends BaseHandler {

	// Variables
	private Vector<Object> objects = new Vector<>();
	private Vector<ObjectEntity> entities = new Vector<>();
	private Object backgroundObject;

	
	// Constructor
	public ObjectHandler(Context context_) {
		bitmapDecoder = new BitmapDecoder(context_);

		// General Objects
		backgroundObject = new Object(bitmapDecoder.decode("background"), true);

		// Object List
		objects.addElement(new Object(bitmapDecoder.decode("boulder"), false));
		objects.addElement(new Object(bitmapDecoder.decode("food"), true));

		// Entities
		entities.addElement(new ObjectEntity(0, 0, 0));
	}

	
	// Getters
	public Vector<Object> getObjects() {
		return objects;
	}
	public Object getObject(int object_) {
		return objects.get(object_);
	}
	public Vector<ObjectEntity> getEntities() {
		return entities;
	}
	public ObjectEntity getEntity(int entity_) {
		return entities.get(entity_);
	}
	public Object getBackground() {
		return backgroundObject;
	}
}
