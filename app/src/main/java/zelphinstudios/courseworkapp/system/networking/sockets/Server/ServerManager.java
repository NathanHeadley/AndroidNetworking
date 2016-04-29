package zelphinstudios.courseworkapp.system.networking.sockets.server;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.net.ConnectException;
import java.util.Vector;

import zelphinstudios.courseworkapp.game.entities.ObjectEntity;
import zelphinstudios.courseworkapp.game.entities.PlayerEntity;
import zelphinstudios.courseworkapp.system.networking.databases.LocalDBHelper;
import zelphinstudios.courseworkapp.system.networking.databases.Object;
import zelphinstudios.courseworkapp.system.networking.databases.remotedb.RemoteDBHelper;
import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ServerManager implements Runnable {

	//Threading
	private Thread thread = null;
	private boolean running = false;


	private Handler serverHandler, clientHandler;
	private ServerThread serverThread;
	private Vector<ConnectionThread> connections = new Vector<>();

	private RemoteDBHelper remoteDBHelper, remoteDBHelperScores;
	private Vector<PlayerEntity> players = new Vector<>();
	private Vector<Object> objects = new Vector<>();

	public ServerManager(Context context_, Handler clientHandler_) {
		clientHandler = clientHandler_;
		players.addElement(new PlayerEntity(4, 6));
		players.addElement(new PlayerEntity(16, 6));
		Log.e("Nathan", "position 0 = "+players.get(0).getX() + " - " + players.get(0).getY());
		createHandler();
		serverThread = new ServerThread(connections, serverHandler);
		remoteDBHelper = new RemoteDBHelper(context_, "objects");
		remoteDBHelperScores = new RemoteDBHelper(context_, "scores");
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
						msgSend.obj = "0~server~started";
						clientHandler.sendMessage(msgSend);
						Log.e("Nathan", "ServerManager -> clientHandler : " + msgSend);
					}
					if(message.equals("ready")) {
						String toSend = "";
						objects = remoteDBHelper.getObjects();
						for (Object object : objects) {
							toSend += object.getId() + "-" + object.getX() + "-" + object.getY() + "|";
						}
						sendData("objects~" + toSend);
					}
					if(message.contains("~")) {
						Log.e("Nathan", "ManagerMessage = " + message);
						String[] messages = message.split("~");
						int client = Integer.parseInt(messages[0]) - 1;
						int oldX = players.get(client).getAbsX();
						int oldY = players.get(client).getAbsY();
						int newX = 0, newY = 0;
						Object food = null;
						switch(messages[1]) {
							case "100":
								newX = oldX;
								newY = oldY - 1;
								break;
							case "101":
								newX = oldX + 1;
								newY = oldY;
								break;
							case "102":
								newX = oldX;
								newY = oldY + 1;
								break;
							case "103":
								newX = oldX - 1;
								newY = oldY;
								break;
						}

						for(Object object : objects) {
							if(object.getX() == newX && object.getY() == newY) {
								if(object.getId() == 0) {
									return;
								} else if(object.getId() == 1) {
									food = object;
								}
							}
						}

						if(newX != oldX) {
							players.get(client).setX(newX);
							sendData("position~x~" + newX, client+1);
						} else if(newY != oldY) {
							players.get(client).setY(newY);
							sendData("position~y~" + newY, client+1);
						}

						if(food != null) {
							objects.removeElement(food);
							players.get(client).addScore(10);
							sendData("score~" + players.get(client).getScore(), client+1);
						}

						if(players.get(0).getScore() + players.get(1).getScore() == 20) {
							remoteDBHelperScores.addScore(players.get(0).getScore());
							remoteDBHelperScores.addScore(players.get(1).getScore());
							sendData("gameover", 0);
						}
					}
				}
			}
		};
	}

	private void sendData(String data_) {
		for(int i = 0; i < connections.size(); i++) {
			connections.get(i).sendData(i + "~" + data_);
		}
	}

	private void sendData(String data_, int client_) {
		for(ConnectionThread connection : connections) {
			connection.sendData(client_ + "~" + data_);
		}
	}

	@Override
	public void run() {
		Looper.prepare();
		serverHandler.handleMessage(Message.obtain());
		Looper.loop();
	}

	public void onPause() {
		running = false;
		thread = null;
	}

	public void onResume() {
		for(ConnectionThread connection : connections) {
			connection.onResume();
		}
		if(serverThread != null) {
			serverThread.onResume();
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public ServerThread getServerThread() {
		return serverThread;
	}

}
