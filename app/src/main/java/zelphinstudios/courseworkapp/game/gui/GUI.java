package zelphinstudios.courseworkapp.game.gui;

import android.graphics.Bitmap;

import java.util.Vector;

public class GUI extends BaseGUI {

	// Variables
	private Vector<Button> buttons = new Vector<>();
	private Vector<TextField> textFields = new Vector<>();


	// Constructor
	public GUI(int x_, int y_, Bitmap background_, boolean visible_) {
		x = x_;
		y = y_;
		bitmap = background_;
		visible = visible_;
	}


	// Methods
	public void addButton(Button button_) {
		buttons.addElement(button_);
	}
	public void addTextField(TextField textField_) {
		textFields.addElement(textField_);
	}


	// Getters
	public Vector<Button> getButtons() {
		return buttons;
	}
	public Vector<TextField> getTextFields() {
		return textFields;
	}
	public Button getButton(int button_) {
		return buttons.get(button_);
	}
	public TextField getTextField(int textField_) {
		return textFields.get(textField_);
	}

}
