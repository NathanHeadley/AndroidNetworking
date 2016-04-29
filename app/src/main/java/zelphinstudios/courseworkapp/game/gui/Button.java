package zelphinstudios.courseworkapp.game.gui;

import android.graphics.Bitmap;

// Class to store buttons
public class Button extends BaseGUI {
	
	// Variables
	private int actionId;
	
	
	// Constructor
	public Button (int actionId_, int x_, int y_, int width_, int height_, Bitmap bitmap_, boolean visible_) {
		actionId = actionId_;
		x = x_;
		y = y_;
		width = width_;
		height = height_;
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
