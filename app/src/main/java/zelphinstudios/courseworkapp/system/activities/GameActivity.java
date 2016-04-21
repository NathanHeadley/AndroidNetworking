package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

import zelphinstudios.courseworkapp.game.entities.ObjectEntity;
import zelphinstudios.courseworkapp.game.entities.Entities;
import zelphinstudios.courseworkapp.game.entities.PlayerEntity;
import zelphinstudios.courseworkapp.game.gui.Button;
import zelphinstudios.courseworkapp.game.gui.GUI;
import zelphinstudios.courseworkapp.game.gui.GUIHandler;
import zelphinstudios.courseworkapp.system.networking.sockets.client.ClientThread;
import zelphinstudios.courseworkapp.system.networking.sockets.server.ServerManager;
import zelphinstudios.courseworkapp.game.views.GameView;

public class GameActivity extends Activity implements View.OnTouchListener {

    private GameView gameView;
	private ServerManager serverManager;
	private ClientThread clientThread;
	private Handler clientHandler;
	private Entities entities;

	// Handlers
	private GUIHandler guiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
	    super.onCreate(savedInstanceState_);

	    createHandler();

	    guiHandler = new GUIHandler(this);
	    entities = new Entities(this);
	    gameView = new GameView(this, guiHandler, entities);
		gameView.setOnTouchListener(this);

	    Intent intent = getIntent();
	    boolean hosting = intent.getBooleanExtra("hosting", false);
	    if(hosting) {
		    serverManager = new ServerManager(this, clientHandler);
        } else {
		    clientThread = new ClientThread(clientHandler);
	    }

	    setContentView(gameView);
    }

	private void createHandler() {
		clientHandler = new Handler(Looper.getMainLooper()) {
			public void handleMessage(Message message_) {
				String message = (String) message_.obj;
				Log.e("Nathan", "ClientHandler: " + message);
				if(message != null) {
					String[] type = message.split("~");
					if(type[0].equals("server")) {
						if (type[1].equals("started")) {
							clientThread = new ClientThread(clientHandler);
						}
					} else if(type[0].equals("objects")) {
							Log.e("Nathan", "Full Message = " + message);
							String[] subMessages = type[1].split("\\|");
							Vector<ObjectEntity> receivedEntities = new Vector<>();
							for (String s : subMessages) {
								String[] subMessage = s.split("\\-");
								//Log.e("Nathan", "SubMessage - " + subMessage[0] + "-" + subMessage[1] + "-" + subMessage[2]);
								if (Integer.parseInt(subMessage[0]) < 2) {
									receivedEntities.addElement(new ObjectEntity(Integer.parseInt(subMessage[0]),
											Integer.parseInt(subMessage[2]),
											Integer.parseInt(subMessage[1])));
								} else {
									entities.addPlayerEntity(new PlayerEntity(Integer.parseInt(subMessage[2]),
											Integer.parseInt(subMessage[1])));
								}
							}
							entities.addEntities(receivedEntities);
					} else if(type[0].equals("position")) {
						if(type[1].equals("y")) {
							entities.getPlayerEntity(0).setY(Integer.parseInt(type[2]));
							Log.e("Nathan", "Moved to Y = " + type[2]);
						}
					}
				}
			}
		};
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
								clientThread.sendData(Integer.toString(button.getActionId()));
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
