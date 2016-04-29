package zelphinstudios.courseworkapp.game.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zelphinstudios.courseworkapp.game.entities.Entities;
import zelphinstudios.courseworkapp.game.entities.ObjectEntity;
import zelphinstudios.courseworkapp.game.entities.PlayerEntity;
import zelphinstudios.courseworkapp.game.gui.Button;
import zelphinstudios.courseworkapp.game.gui.GUI;
import zelphinstudios.courseworkapp.game.gui.GUIHandler;
import zelphinstudios.courseworkapp.game.gui.TextField;

// Class to draw everything to screen on a surface view
public class GameView extends SurfaceView implements Runnable {

	// Variables
    // Threading
    private Thread thread = null;
	private boolean running = false;
	// Handlers
    private SurfaceHolder surfaceHolder;
	private GUIHandler guiHandler;
	private Entities entities;


	// Constructors
	public GameView(Context context_) { // Just to keep android studio happy
		super(context_);
		surfaceHolder = getHolder();
	}
    public GameView(Context context_, GUIHandler guiHandler_, Entities entities_) {
        super(context_);
        surfaceHolder = getHolder();
	    guiHandler = guiHandler_;
	    entities = entities_;
    }


	// Main method for the thread
    public void run() {
        while(running) {
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);

	        // Draw game objects
			for(ObjectEntity entity : entities.getObjectEntities()) {
				if(entity.isVisible()) {
					if (entity.getId() == 0) {
						canvas.drawBitmap(entities.getBarrier(),
								entity.getX(), entity.getY(), null);
					} else if (entity.getId() == 1) {
						canvas.drawBitmap(entities.getFood(),
								entity.getX(), entity.getY(), null);
					}
				}
			}

	        // Draw player
	        for(PlayerEntity player : entities.getPlayerEntities()) {
		        canvas.drawBitmap(entities.getPlayer(player.getDirection()),
				        player.getX(), player.getY(), null);
	        }

	        // Draw interfaces
	        for(GUI gui : guiHandler.getGUIs()) {
		        if(gui.isVisible()) {
			        // Draw background
			        if(gui.getBitmap() != null) {
				        canvas.drawBitmap(gui.getBitmap(),
						        gui.getX(), gui.getY(), null);
			        }
			        // Draw buttons
			        for(Button button : gui.getButtons()) {
				        if(button.isVisible()) {
					        if(button.getBitmap() != null) {
						        canvas.drawBitmap(button.getBitmap(),
								        gui.getX() + button.getX(), gui.getY() + button.getY(), null);
					        }
				        }
			        }
			        // Draw textfields
			        for(TextField textField : gui.getTextFields()) {
				        if(textField.isVisible()) {
					        if(textField.getText() != null) {
						        canvas.drawText(textField.getText(),
								        gui.getX() + textField.getX(), gui.getY() + textField.getY(), textField.getPaint());
					        }
				        }
			        }
		        }
	        }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


	// Methods for stopping and starting thread
    public void onPause() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        thread = null;
    }
    public void onResume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

}
