package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import zelphinstudios.courseworkapp.networking.Client;
import zelphinstudios.courseworkapp.networking.ServerThread;
import zelphinstudios.courseworkapp.views.GameView;

public class GameActivity extends Activity {

    private GameView gameView;
    private ServerThread serverThread;
    private Client client;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);

        Intent intent = getIntent();
        boolean hosting = intent.getBooleanExtra("hosting", false);
        if(hosting) {
            serverThread = new ServerThread();
        }

        client = new Client();
        gameView = new GameView(this);
        setContentView(gameView);
        run();
    }

    private void run() {
        if(serverThread != null) {
            serverThread.startServer();
        }
        client.connectToServer();
        while(running) {
            serverThread.checkConnections();
            // Check serverThread for new data
            Log.e("Nathan", serverThread.getData());
            Log.e("Nathan", client.getData());
            // Check client for new data
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        if(gameView != null) {
            gameView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        if(gameView != null) {
            gameView.onResume();
        }
        run();
    }

}
