package zelphinstudios.courseworkapp.system.networking.sockets;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerThread implements Runnable {

    // Variables
    private Thread thread = null;
    private boolean running = false;

    private ServerSocket socket;
    private Vector<Socket> clients = new Vector<>();
    private static final int serverPort = 8080;
    private String inData = "";

    public void run () {
	    try {
		    socket = new ServerSocket(serverPort);
	    } catch (IOException io) { Log.e("Nathan", io.toString()); }

		while(running && socket != null) {

			// check connections
			for(int i = 0; i < clients.size(); i++) {
                if(clients.get(i) == null) {
                    clients.remove(i);
                }
            }

            try {
                clients.addElement(socket.accept());
                Log.e("Nathan", "Accepted connection from: " + clients.get(clients.size() - 1).getRemoteSocketAddress());
            } catch (IOException io) { Log.e("Nathan", io.toString()); }

			// check for data
			for(Socket client : clients) {
                try {
                    DataInputStream inputStream = new DataInputStream(client.getInputStream());
                    inData = inputStream.readUTF();
                    Log.e("Nathan", "Received message: " + inData);
                } catch (IOException io) { Log.e("Nathan", "Not received any messages: " + io.toString()); }
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
		try {
			thread.join();
		} catch (InterruptedException ie) { Log.e("Nathan", ie.toString()); }
		thread = null;
	}
	
	public void onResume() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

}
