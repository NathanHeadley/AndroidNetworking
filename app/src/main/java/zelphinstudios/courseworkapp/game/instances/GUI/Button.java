package zelphinstudios.courseworkapp.game.instances.GUI;

import android.graphics.Bitmap;

public class Button extends BaseGUI {
	
	// Variables
	private int actionId;
	
	
	// Constructor
	public Button (int x_, int y_, int width_, int height_,
		int actionId_, Bitmap bitmap_, boolean visible_) {
		x = x_;
		y = y_;
		width = width_;
		height = height_;
		actionId = actionId_;
		bitmap = bitmap_;
		visible = visible_;
	}

	
	// Getters
	public int getActionId() {
		return actionId;
	}
	
	
	// Setters
	public void setActionId(int actionId_) {
		actionId = actionId_;
	}

}
