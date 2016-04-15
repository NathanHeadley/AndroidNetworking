package zelphinstudios.courseworkapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.Player;
import zelphinstudios.courseworkapp.game.instances.GameObject;
import android.graphics.*;

public class GameView extends SurfaceView implements Runnable {

    // Threading
	private Context context;
    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;

    private ObjectHandler objectHandler;
	private Player player;

	public GameView(Context context_) {
		super(context_);
		context = context_;
	}

    public GameView(Context context_, ObjectHandler objectHandler_, Player player_) {
        super(context_);
	    context = context_;
        surfaceHolder = getHolder();
        objectHandler = objectHandler_;
		player = player_;
    }

    public void run() {
        while(running) {
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();

	        if(context.getResources().getConfiguration().orientation == 1) { //1 = portrait, 2 = landscape
		        if (objectHandler.getBackground().getBitmap() != null) {
			        for (int y = 0; y < 5; y++) {
				        for (int x = 0; x < 5; x++) {
					        canvas.drawBitmap(objectHandler.getBackground().getBitmap(), 96 * x, 96 * y, null);
				        }
			        }
		        }

		        for (GameObject object : objectHandler.getGameObjects()) {
			        if (object.getBitmap() != null) {
				        canvas.drawBitmap(object.getBitmap(), object.getX(), object.getY(), null);
			        }
		        }

		        if (player.getBitmap() != null) {
			        canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), null);
		        }
	        } else if(context.getResources().getConfiguration().orientation == 2) {
		        //landscape
		        Log.e("Nathan", "I'm in Landscape mode!");
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
