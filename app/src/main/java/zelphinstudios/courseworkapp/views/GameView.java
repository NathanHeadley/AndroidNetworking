package zelphinstudios.courseworkapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.GameObject;

public class GameView extends SurfaceView implements Runnable {

    // Threading
    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;

    private ObjectHandler objectHandler;

	/*public GameView(Context context_) {
		super(context_);
	}*/

    public GameView(Context context_, ObjectHandler objectHandler_) {
        super(context_);
        surfaceHolder = getHolder();
        objectHandler = objectHandler_;
    }

    public void run() {
        while(running) {
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();

            for(GameObject object : objectHandler.getGameObjects()) {
	            Log.e("Nathan", ""+object.getBitmap().toString());
	            if(object.getBitmap() != null) {
		            canvas.drawBitmap(object.getBitmap(), object.getX(), object.getY(), null);
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
