package zelphinstudios.courseworkapp.game.handlers;

import android.content.Context;

import java.util.Vector;

import zelphinstudios.courseworkapp.game.instances.GameObject;
import zelphinstudios.courseworkapp.util.BitmapDecoder;

public class ObjectHandler {

	// Variables
	private Vector<GameObject> gameObjects = new Vector<>();
	private BitmapDecoder bitmapDecoder;
	private GameObject backgroundObject;

	
	// Constructor
	public ObjectHandler(Context context_) {
		bitmapDecoder = new BitmapDecoder(context_);
		backgroundObject = new GameObject(0, 0, 32, 32, bitmapDecoder.decode("background"), true, true);
		gameObjects.addElement(new GameObject(0, 0, 32, 32, bitmapDecoder.decode("boulder"), true, true));
		gameObjects.addElement(new GameObject(50, 50, 32, 32, bitmapDecoder.decode("boulder"), true, true));
	}

	
	// Getters
	public GameObject getBackground() {
		return backgroundObject;
	}
	public GameObject getGameObject(int object_) {
		return gameObjects.get(object_);
	}
	public Vector<GameObject> getGameObjects() {
		return gameObjects;
	}
}
