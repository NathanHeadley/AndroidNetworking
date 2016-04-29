package zelphinstudios.courseworkapp.game.gui;

import android.graphics.Color;
import android.graphics.Paint;

// Class to store text fields
public class TextField extends BaseGUI {

	// Variables
	private String text;
	private Paint paint = new Paint();


	// Constructor
	public TextField(int x_, int y_, String text_, boolean visible_) {
		x = x_;
		y = y_;
		text = text_;
		paint.setTextSize(40);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setColor(Color.WHITE);
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
