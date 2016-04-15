package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.Player;
import zelphinstudios.courseworkapp.system.networking.sockets.Client;
import zelphinstudios.courseworkapp.system.networking.sockets.ServerThread;
import zelphinstudios.courseworkapp.system.util.BitmapDecoder;
import zelphinstudios.courseworkapp.game.views.GameView;

public class GameActivity extends Activity {

    private GameView gameView;
    private ServerThread serverThread;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
	    super.onCreate(savedInstanceState_);

	    BitmapDecoder bitmapDecoder = new BitmapDecoder(this);
	    ObjectHandler objectHandler = new ObjectHandler(this);
	    Player player = new Player();
	    gameView = new GameView(this, objectHandler, player);

	    setContentView(gameView);

	    Intent intent = getIntent();
	    boolean hosting = intent.getBooleanExtra("hosting", false);
	    if(hosting) {
	        serverThread = new ServerThread();
        }

	    client = new Client();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
		if(serverThread != null) {
			serverThread.onPause();
		}
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
		if(serverThread != null) {
			serverThread.onResume();
		}
    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			View decorView = getWindow().getDecorView();
			if (Build.VERSION.SDK_INT < 19) {
				decorView.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
								| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_FULLSCREEN);
			} else {
				decorView.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
								| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_FULLSCREEN
								| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			}
		}
	}

}
