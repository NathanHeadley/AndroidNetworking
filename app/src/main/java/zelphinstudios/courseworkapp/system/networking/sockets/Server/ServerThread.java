package zelphinstudios.courseworkapp.system.networking.sockets.server;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ServerThread extends BaseThread {

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
	    } catch (IOException io) { Log.e("Nathan", io.toString()); }

		while(running && socket != null) {
            try {
	            connections.add(new ConnectionThread(socket.accept(), serverHandler));
                Log.e("Nathan", "Accepted connection!");
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
		}

	    if(socket != null) {
		    try {
			    socket.close();
		    } catch (IOException io) { io.printStackTrace(); }
		    socket = null;
	    }
	}

}
