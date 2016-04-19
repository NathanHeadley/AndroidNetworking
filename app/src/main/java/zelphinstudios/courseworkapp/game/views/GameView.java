package zelphinstudios.courseworkapp.game.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zelphinstudios.courseworkapp.game.entities.ObjectEntity;
import zelphinstudios.courseworkapp.game.entities.PlayerEntity;
import zelphinstudios.courseworkapp.game.handlers.GUIHandler;
import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.GUI.Button;
import zelphinstudios.courseworkapp.game.instances.GUI.GUI;
import zelphinstudios.courseworkapp.game.instances.GUI.TextField;
import zelphinstudios.courseworkapp.game.instances.Player;

public class GameView extends SurfaceView implements Runnable {

    // Threading
	private Context context;
    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;

    private ObjectHandler objectHandler;
	private GUIHandler guiHandler;
	private Player player;

	public GameView(Context context_) {
		super(context_);
		context = context_;
	}

    public GameView(Context context_, ObjectHandler objectHandler_, GUIHandler guiHandler_, Player player_) {
        super(context_);
	    context = context_;
        surfaceHolder = getHolder();
        objectHandler = objectHandler_;
	    guiHandler = guiHandler_;
		player = player_;
    }

    public void run() {
        while(running) {
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();

	        // Draw background
	        if (objectHandler.getBackground().getBitmap() != null) {
		        for (int y = 0; y < 12; y++) {
			        for (int x = 0; x < 20; x++) {
				        canvas.drawBitmap(objectHandler.getBackground().getBitmap(),
						        x*96, y*96, null);
			        }
		        }
	        }

	        // Draw game objects
	        for (ObjectEntity entity : objectHandler.getEntities()) {
		        if (objectHandler.getObject(entity.getId()).getBitmap() != null) {
			        canvas.drawBitmap(objectHandler.getObject(entity.getId()).getBitmap(),
					        entity.getX(), entity.getY(), null);
		        }
	        }

	        // Draw player
	        PlayerEntity playerEntity = player.getEntity();
	        if (player.getBitmap(playerEntity.getDirection()) != null) {
		        canvas.drawBitmap(player.getBitmap(playerEntity.getDirection()),
				        playerEntity.getX(), playerEntity.getY(), null);
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
