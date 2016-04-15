package zelphinstudios.courseworkapp.game.instances.GUI;

import android.graphics.Paint;

public class TextField extends BaseGUI {

	// Variables
	private String text;
	private Paint paint;


	// Constructor
	public TextField(int x_, int y_, String text_, Paint paint_, boolean visible_) {
		x = x_;
		y = y_;
		text = text_;
		paint = paint_;
		visible = visible_;
	}


	// Getters
	public String getText() {
		return text;
	}
	public Paint getPaint() {
		return paint;
	}


	// Setters
	public void setText(String text_) {
		text = text_;
	}
	public void setPaint(Paint paint_) {
		paint = paint_;
	}

}
