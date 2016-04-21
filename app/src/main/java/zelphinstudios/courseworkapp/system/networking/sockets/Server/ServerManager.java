package zelphinstudios.courseworkapp.system.networking.sockets.server;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.net.ConnectException;
import java.util.Vector;

import zelphinstudios.courseworkapp.game.entities.PlayerEntity;
import zelphinstudios.courseworkapp.system.networking.databases.LocalDBHelper;
import zelphinstudios.courseworkapp.system.networking.databases.Object;
import zelphinstudios.courseworkapp.system.networking.databases.remotedb.RemoteDBHelper;
import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ServerManager extends BaseThread {

	private Handler serverHandler, clientHandler;
	private ServerThread serverThread;
	private Vector<ConnectionThread> connections = new Vector<>();

	private RemoteDBHelper remoteDBHelper;
	private Vector<PlayerEntity> players = new Vector<>();

	public ServerManager(Context context_, Handler clientHandler_) {
		clientHandler = clientHandler_;
		players.addElement(new PlayerEntity(6, 4));
		players.addElement(new PlayerEntity(6, 16));
		Log.e("Nathan", "position 0 = "+players.get(0).getX() + " - " + players.get(0).getY());
		createHandler();
		serverThread = new ServerThread(connections, serverHandler);
		remoteDBHelper = new RemoteDBHelper(context_, "objects");
	}

	private void createHandler() {
		serverHandler = new Handler() {
			public void handleMessage(Message message_) {
				String message = (String) message_.obj;
				Message msgSend = Message.obtain();
				Log.e("Nathan", "Message = " + message);
				if (message != null) {
					if(message.equals("started")) {
						Log.e("Nathan", "Started...");
						msgSend.obj = "server~started";
						clientHandler.sendMessage(msgSend);
					}
					if(message.equals("ready")) {
						String toSend = "";
						for (Object object : remoteDBHelper.getObjects()) {
							toSend += object.getId() + "-" + object.getX() + "-" + object.getY() + "|";
						}
						sendData("objects~" + toSend);
					}
					if(message.equals("100")) {
						players.get(0).setY(players.get(0).getAbsY() - 1);
						Log.e("Nathan", ""+players.get(0).getY());
						sendData("position~y~" + players.get(0).getAbsY());
					}
					if (message.equals("101")) {
						Log.e("Nathan", "Right button pressed.!");
					}
				}
			}
		};
	}

	private void sendData(String data_) {
		for(ConnectionThread connection : connections) {
			connection.sendData(data_);
		}
	}

	@Override
	public void run() {
		Looper.prepare();
		serverHandler.handleMessage(Message.obtain());
		Looper.loop();
	}

	@Override
	public void onPause() {
		super.onPause();
		if(serverThread != null) {
			serverThread.onPause();
		}
		for(ConnectionThread connection : connections) {
			connection.onPause();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if(serverThread != null) {
			serverThread.onResume();
		}
		for(ConnectionThread connection : connections) {
			connection.onResume();
		}
	}
}
