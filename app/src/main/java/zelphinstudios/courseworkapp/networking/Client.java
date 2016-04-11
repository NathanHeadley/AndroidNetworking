package zelphinstudios.courseworkapp.networking;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    // Variables
    // Threading
    private Thread thread = null;
    private boolean running = false;
    // Networking
    private Socket socket = null;
    private static final String serverAddress = "10.0.2.2";
    private static final int serverPort = 5050;

    public Client() {
        onResume();
    }

    // Main loop for thread
    public void run() {
        // Whilst the thread is running
        while(running) {
            // Connect to server if not connected
            if(socket == null) {
                try {
                    socket = new Socket(serverAddress, serverPort);
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
        }
    }

    // If the app is paused, stop threading
    public void onPause() {
        running = false;
        // Disconnect from server if haven't already
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
        }
        try {
            thread.join();
        } catch (InterruptedException ie) { Log.e("Nathan", ie.toString()); }
        thread = null;
    }

    // When the app is resumed, start threading again
    public void onResume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

}
