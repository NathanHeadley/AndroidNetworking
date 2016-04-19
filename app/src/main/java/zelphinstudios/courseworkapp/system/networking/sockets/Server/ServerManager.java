package zelphinstudios.courseworkapp.system.networking.sockets.server;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ServerManager extends BaseThread {

	private Handler serverHandler;
	private ServerThread serverThread;
	private Vector<ConnectionThread> connections = new Vector<>();

	public ServerManager() {
		createHandler();
		serverThread = new ServerThread(connections, serverHandler);
	}

	private void createHandler() {
		serverHandler = new Handler() {
			public void handleMessage(Message message_) {
				String message = (String)message_.obj;
				Log.e("Nathan", "Message = " + message);
				if(message != null) {
					if (message.equals("101")) {
						for (ConnectionThread connection : connections) {
							connection.sendData("To the client!");
						}
					}
				}
			}
		};
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
