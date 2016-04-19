package zelphinstudios.courseworkapp.system.networking.sockets.client;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ClientThread extends BaseThread {

	private Socket socket = null;
	private final String serverAddress = "10.0.2.2";
	private final int serverPort = 5050;
	private String inData = "";
	private String outData = "";

	public ClientThread() {
		onResume();
	}

	@Override
	public void run() {
		try {
			socket = new Socket(serverAddress, serverPort);
			Log.e("Nathan", "Attempting to connect on: " + serverAddress + ":" + serverPort);
		} catch (IOException io) { Log.e("Nathan", io.toString()); }

		while(running) {
			if(socket != null) {
				try {
					DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					if(inputStream.available() > 0) {
						inData = inputStream.readUTF();
						Log.e("Nathan", "Client Received: " + inData);
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
						Log.e("Nathan", io.toString());
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
		outData = data_;
	}

}
