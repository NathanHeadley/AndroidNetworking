package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.os.Bundle;

import zelphinstudios.courseworkapp.networking.Client;
import zelphinstudios.courseworkapp.networking.Server;
import zelphinstudios.courseworkapp.views.GameView;

public class GameActivity extends Activity {

    private GameView gameView;
    private Server server;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        server = new Server();
        client = new Client();
        gameView = new GameView(this, server, client);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(server != null) {
            server.onPause();
        }
        if(client != null) {
            client.onPause();
        }
        if(gameView != null) {
            gameView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(server != null) {
            server.onResume();
        }
        if(client != null) {
            client.onResume();
        }
        if(gameView != null) {
            gameView.onResume();
        }
    }

}
