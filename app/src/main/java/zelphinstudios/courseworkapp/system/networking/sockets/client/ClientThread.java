package zelphinstudios.courseworkapp.system.networking.sockets.client;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import zelphinstudios.courseworkapp.system.activities.HighscoreActivity;
import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ClientThread implements Runnable {

	// Threading
	private Thread thread = null;
	private boolean running = false;


	private Socket socket = null;
	private final String serverAddress = "10.0.2.2";
	private final int serverPort = 5050;
	private String inData = "";
	private String outData = "";
	private Handler clientHandler;
	private int clientNumber;

	public ClientThread(Handler clientHandler_) {
		clientHandler = clientHandler_;
		//onResume();
	}

	@Override
	public void run() {
		Log.e("Nathan", "Runnnnn");
		try {
			socket = new Socket(serverAddress, serverPort);
			Log.e("Nathan", "Attempting to connect on: " + serverAddress + ":" + serverPort);
		} catch (IOException io) {
			Log.e("Nathan", io.toString());
		}

		while(running) {
			if(socket != null) {
				if(!socket.isClosed()) {
					try {
						DataInputStream inputStream = new DataInputStream(socket.getInputStream());
						if (inputStream.available() > 0) {
							Message message = Message.obtain();
							message.obj = inputStream.readUTF();
							clientHandler.sendMessage(message);
						}
					} catch (IOException io) {
						io.printStackTrace();
					}

					if (!outData.equals("")) {
						try {
							DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
							outputStream.writeUTF(outData);
							Log.e("Nathan", "Client sent: " + outData);
							outData = "";
						} catch (IOException io) {
							Log.e("Nathan", "it's me: " + io.toString());
						}
					}
				}
			}
		}
		if(socket != null) {
			try {
				socket.close();
				Log.e("Nathan", "Client socket closing...");
			} catch (IOException io) { io.printStackTrace(); }
			socket = null;
		}
	}

	public void sendData(String data_) {
		outData = clientNumber + "~" + data_;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int number_) {
		clientNumber = number_;
		Log.e("Nathan", "Client number assigned: " + clientNumber);
	}

	public void onPause() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException ie) { Log.e("Nathan", "ClientThread: " + ie.toString()); }
		thread = null;
	}

	public void onResume() {
		Log.e("Nathan", "Start Resuming..");
		running = true;
		thread = new Thread(this);
		thread.start();
		Log.e("Nathan", "End Resuming..");
	}

}
