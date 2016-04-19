package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import zelphinstudios.courseworkapp.game.handlers.GUIHandler;
import zelphinstudios.courseworkapp.game.handlers.ObjectHandler;
import zelphinstudios.courseworkapp.game.instances.GUI.Button;
import zelphinstudios.courseworkapp.game.instances.GUI.GUI;
import zelphinstudios.courseworkapp.game.instances.Player;
import zelphinstudios.courseworkapp.system.networking.sockets.client.ClientThread;
import zelphinstudios.courseworkapp.system.networking.sockets.server.ServerManager;
import zelphinstudios.courseworkapp.game.views.GameView;

public class GameActivity extends Activity implements View.OnTouchListener {

    private GameView gameView;
	private ServerManager serverManager;
	private ClientThread clientThread;

	// Handlers
	private GUIHandler guiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
	    super.onCreate(savedInstanceState_);

	    ObjectHandler objectHandler = new ObjectHandler(this);
	    guiHandler = new GUIHandler(this);
	    Player player = new Player();
	    gameView = new GameView(this, objectHandler, guiHandler, player);
		gameView.setOnTouchListener(this);

	    Intent intent = getIntent();
	    boolean hosting = intent.getBooleanExtra("hosting", false);
	    if(hosting) {
		    serverManager = new ServerManager();
        }

	    setContentView(gameView);
    }

	@Override
	public boolean onTouch(View view_, MotionEvent motionEvent_) {
		if(motionEvent_.getAction() == MotionEvent.ACTION_DOWN) {
			float x = motionEvent_.getX();
			float y = motionEvent_.getY();
			for(GUI gui : guiHandler.getGUIs()) {
				if(gui.isVisible()) {
					for(Button button : gui.getButtons()) {
						if(button.isVisible()) {
							if(x >= gui.getX() + button.getX()
									&& x <= (gui.getX() + button.getX() + button.getWidth())
									&& y >= gui.getY() + button.getY()
									&& y <= (gui.getY() + button.getY() + button.getHeight())) {
								if(button.getActionId() == 100) {
									Log.e("Nathan", "Button Pressed: " + button.getActionId());
									clientThread = new ClientThread();
								} else if(button.getActionId() == 101) {
									clientThread.sendData("101");
								}


								//clientThread.sendData(Integer.toString(button.getActionId()));
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
		if(serverManager != null) {
			serverManager.onPause();
		}
	    if(clientThread != null) {
		    clientThread.onPause();
	    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
		if(serverManager != null) {
			serverManager.onResume();
		}
	    if(clientThread != null) {
		    clientThread.onResume();
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
