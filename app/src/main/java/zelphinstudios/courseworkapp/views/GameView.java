package zelphinstudios.courseworkapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;

    public GameView(Context context_) {
        super(context_);
        surfaceHolder = getHolder();
    }

    public void run() {
        while(running) {
            if(!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();

            //canvas.drawBitmap()...

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
