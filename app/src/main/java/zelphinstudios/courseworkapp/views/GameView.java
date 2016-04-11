package zelphinstudios.courseworkapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zelphinstudios.courseworkapp.networking.Client;
import zelphinstudios.courseworkapp.networking.Server;

public class GameView extends SurfaceView implements Runnable {

    // Threading
    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;
    // Networking
    private Server server;
    private Client client;

    public GameView(Context context_) {
        super(context_);
        surfaceHolder = getHolder();
    }

    public GameView(Context context_, Server server_, Client client_) {
        super(context_);
        surfaceHolder = getHolder();
        server = server_;
        client = client_;
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
        if(server != null) {
            server.onPause();
        }
        if (client != null) {
            client.onPause();
        }
    }

    public void onResume() {
        running = true;
        thread = new Thread(this);
        thread.start();
        if(server != null) {
            server.onResume();
        }
        if (client != null) {
            client.onResume();
        }
    }

}
