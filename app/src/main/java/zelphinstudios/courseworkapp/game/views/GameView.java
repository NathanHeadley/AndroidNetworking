package zelphinstudios.courseworkapp.game.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zelphinstudios.courseworkapp.game.entities.ObjectEntity;
import zelphinstudios.courseworkapp.game.entities.PlayerEntity;
import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.Player;
import zelphinstudios.courseworkapp.game.instances.Object;

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

	        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		        if (objectHandler.getBackground().getBitmap() != null) {
			        for (int y = 0; y < 12; y++) {
				        for (int x = 0; x < 20; x++) {
					        canvas.drawBitmap(objectHandler.getBackground().getBitmap(),
							        x*96, y*96, null);
				        }
			        }
		        }

		        for (ObjectEntity entity : objectHandler.getEntities()) {
			        if (objectHandler.getObject(entity.getId()).getBitmap() != null) {
				        canvas.drawBitmap(objectHandler.getObject(entity.getId()).getBitmap(),
						        entity.getX(), entity.getY(), null);
			        }
		        }

		        PlayerEntity playerEntity = player.getEntity();
		        if (player.getBitmap(playerEntity.getDirection()) != null) {
			        canvas.drawBitmap(player.getBitmap(playerEntity.getDirection()),
					        playerEntity.getX(), playerEntity.getY(), null);
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

	public void onDestroy() {
		running = false;
		thread = null;
	}

}
