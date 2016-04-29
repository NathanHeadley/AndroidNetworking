package zelphinstudios.courseworkapp.system.networking.sockets.server;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ServerThread implements Runnable {

	// Threading
	private Thread thread = null;
	private boolean running = false;


    private ServerSocket socket;
    private static final int serverPort = 8080;
	private Vector<ConnectionThread> connections;
	private Handler serverHandler;

	public ServerThread(Vector<ConnectionThread> connections_, Handler serverHandler_) {
		connections = connections_;
		serverHandler = serverHandler_;
	}

	@Override
    public void run () {
	    try {
		    socket = new ServerSocket(serverPort);
		    Log.e("Nathan", "Started server on: " + serverPort);
	    } catch (IOException io) { Log.e("Nathan", "or me:   " + io.toString()); }

		Message message1 = Message.obtain();
		message1.obj = "started";
		serverHandler.sendMessage(message1);

		while(running) {
            try {
	            Log.e("Nathan", "Listening...");
	            connections.add(new ConnectionThread(socket.accept(), serverHandler));
                Log.e("Nathan", "Accepted connection!");
	            connections.lastElement().sendData(connections.size() + "~server");
            } catch (IOException io) { Log.e("Nathan", "It's not me : " + io.toString()); }
			if(connections.size() == 2) {
				Message message = Message.obtain();
				message.obj = "ready";
				serverHandler.sendMessage(message);
			}
		}

	    if(socket != null) {
		    try {
			    socket.close();
		    } catch (IOException io) { io.printStackTrace(); }
		    socket = null;
	    }
	}

	public void onPause() {
		running = false;
		thread = null;
	}

	public void onResume() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public Vector<ConnectionThread> getConnectionThreads() {
		return connections;
	}

}
