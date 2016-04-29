package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.content.Context;
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

	private Context context = this;
    private GameView gameView;
	private ServerManager serverManager;
	private ClientThread clientThread;
	private Handler clientHandler;
	private Entities entities;
	private boolean hosting = false;

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
	    hosting = intent.getBooleanExtra("hosting", false);
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
				if(message != null) {
					String[] type = message.split("~");
					int client = Integer.parseInt(type[0]);
					switch(type[1]) {
						case "server":
							if(type.length == 2) {
								clientThread.setClientNumber(client);
							} else {
								if(hosting) {
									clientThread = new ClientThread(clientHandler);
									clientThread.onResume();
								}
							}
							break;
						case "objects":
							guiHandler.getGUI(2).setVisible(false);
							guiHandler.getGUI(1).setVisible(true);
							String[] subMessages = type[2].split("\\|");
							Vector<ObjectEntity> receivedEntities = new Vector<>();
							for (String s : subMessages) {
								String[] subMessage = s.split("\\-");
								if (Integer.parseInt(subMessage[0]) < 2) {
									receivedEntities.addElement(new ObjectEntity(Integer.parseInt(subMessage[0]),
											Integer.parseInt(subMessage[1]),
											Integer.parseInt(subMessage[2])));
								} else {
									entities.addPlayerEntity(new PlayerEntity(Integer.parseInt(subMessage[1]),
											Integer.parseInt(subMessage[2])));
								}
							}
							entities.addEntities(receivedEntities);
							break;
						case "position":
							if(type[2].equals("x")) {
								int oldX = entities.getPlayerEntity(client - 1).getAbsX();
								if(Integer.parseInt(type[3]) - oldX > 0) {
									entities.getPlayerEntity(client - 1).setDirection(1);
								} else {
									entities.getPlayerEntity(client - 1).setDirection(3);
								}
								entities.getPlayerEntity(client - 1).setX(Integer.parseInt(type[3]));
							} else if(type[2].equals("y")) {
								int oldY = entities.getPlayerEntity(client - 1).getAbsY();
								if(Integer.parseInt(type[3]) - oldY > 0) {
									entities.getPlayerEntity(client - 1).setDirection(2);
								} else {
									entities.getPlayerEntity(client - 1).setDirection(0);
								}
								entities.getPlayerEntity(client - 1).setY(Integer.parseInt(type[3]));
							}
							break;
						case "score":
							if(clientThread.getClientNumber() == client) {
								entities.getPlayerEntity(client - 1).setScore(Integer.parseInt(type[2]));
								guiHandler.getGUI(1).getTextField(0).setText("Score: " + entities.getPlayerEntity(client - 1).getScore());
							}
							for(ObjectEntity object : entities.getObjectEntities()) {
								if(object.getX() == entities.getPlayerEntity(client - 1).getX() && object.getY() == entities.getPlayerEntity(client - 1).getY()) {
									object.setVisible(false);
								}
							}
							break;
						case "gameover":
							Intent intent = new Intent(context, HighscoreActivity.class);
							context.startActivity(intent);
							break;
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
	    clientThread.onPause();
	    if(serverManager != null) {
		    if(serverManager.getServerThread() != null) {
			    serverManager.getServerThread().getConnectionThreads().get(0).onPause();
			    serverManager.getServerThread().onPause();
			    serverManager.onPause();
		    }
	    }
	    gameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
	    if(clientThread != null) {
		    clientThread.onResume();
	    }
	    if(serverManager != null) {
		    serverManager.onResume();
	    }
	    if(gameView != null) {
		    gameView.onResume();
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
