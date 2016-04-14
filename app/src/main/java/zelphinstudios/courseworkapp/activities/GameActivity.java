package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.Player;
import zelphinstudios.courseworkapp.networking.Client;
import zelphinstudios.courseworkapp.networking.ServerThread;
import zelphinstudios.courseworkapp.views.GameView;

public class GameActivity extends Activity {

    private GameView gameView;
    private ServerThread serverThread;
    private Client client;
    private boolean running = true;

	// Game Variables
	private ObjectHandler objectHandler;
	private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);

	    objectHandler = new ObjectHandler(this);
		player = new Player();
	    Log.e("Nathan", objectHandler.getGameObject(0).getBitmap().toString());
        gameView = new GameView(this, objectHandler, player);
        setContentView(gameView);

	    Intent intent = getIntent();
	    boolean hosting = intent.getBooleanExtra("hosting", false);
	    if(hosting) {
		    serverThread = new ServerThread();
	    }

	    client = new Client();
        run();
    }

    private void run() {
        client.connectToServer();
        /*while(running) {
			String cData = client.getData();
			if(cData == "") {
				
			}
            Log.e("Nathan", client.getData());
            // Check client for new data
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        gameView.onPause();
		if(serverThread != null) {
			serverThread.onPause();
		}
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        gameView.onResume();
		if(serverThread != null) {
			serverThread.onResume();
		}
        run();
    }

}
