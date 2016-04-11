package zelphinstudios.courseworkapp.networking;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable {

    // Variables
    // Threading
    private Thread thread = null;
    private boolean running = false;
    // Networking
    private ServerSocket serverSocket;
    private Vector<Socket> clientSockets = new Vector<>();
    private static final int serverPort = 8080;

    public Server() {
        onResume();
    }

    // Main loop for thread
    public void run() {
        // Create server socket if null
        if(serverSocket == null) {
            try {
                serverSocket = new ServerSocket(serverPort);
                Log.e("Nathan", "Server started on port: " + serverPort);
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
        }
        // Whilst the thread is running
        while(running) {
            // Check if there is a new client
            try {
                clientSockets.addElement(serverSocket.accept());
                Log.e("Nathan", "Accepted connection from: " + clientSockets.get(clientSockets.size() - 1).getRemoteSocketAddress());
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
            // Check if any client has disconnected
            for(int i = 0; i < clientSockets.size(); i++) {
                if(clientSockets.get(i) == null) {
                    clientSockets.remove(i);
                }
            }
            // Check clients for data
            for(Socket client : clientSockets) {
                try {
                    DataInputStream inputStream = new DataInputStream(client.getInputStream());
                    String inData = inputStream.readUTF();
                    Log.e("Nathan", "Received message: " + inData);
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
        }
    }

    public int getNumClients() {
        return clientSockets.size();
    }

    // If the app is paused, stop threading
    public void onPause() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
        thread = null;
    }

    // When the app is resumed, start threading again
    public void onResume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

}
