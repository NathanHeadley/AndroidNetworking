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

public class GameView extends SurfaceView implements Runnable {

    // Threading
    private Thread thread = null;
	private boolean running = false;

	private Context context;
    private SurfaceHolder surfaceHolder;
	private GUIHandler guiHandler;
	private Entities entities;

	public GameView(Context context_) { // Just to keep android studio happy
		super(context_);
		context = context_;
		surfaceHolder = getHolder();
	}

    public GameView(Context context_, GUIHandler guiHandler_, Entities entities_) {
        super(context_);
	    context = context_;
        surfaceHolder = getHolder();
	    guiHandler = guiHandler_;
	    entities = entities_;
    }

    public void run() {
        while(running) {
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
	        // Draw background

	        // Draw game objects
			for(ObjectEntity entity : entities.getObjectEntities()) {
				if(entity.getId() == 0) {
					canvas.drawBitmap(entities.getBarrier(),
							entity.getX(), entity.getY(), null);
				} else if(entity.getId() == 1) {
					canvas.drawBitmap(entities.getFood(),
							entity.getX(), entity.getY(), null);
				}
			}

	        // Draw player
	        for(PlayerEntity player : entities.getPlayerEntities()) {
		        Log.e("Nathan", "X=" + player.getX() + ", Y=" + player.getY());
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
