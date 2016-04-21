package zelphinstudios.courseworkapp.system.networking.sockets.client;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ClientThread extends BaseThread {

	private Socket socket = null;
	private final String serverAddress = "10.0.2.2";
	private final int serverPort = 5050;
	private String inData = "";
	private String outData = "";
	private Handler clientHandler;

	public ClientThread(Handler clientHandler_) {
		clientHandler = clientHandler_;
		onResume();
	}

	@Override
	public void run() {
		//if(socket == null) {
			try {
				socket = new Socket(serverAddress, serverPort);
				Log.e("Nathan", "Attempting to connect on: " + serverAddress + ":" + serverPort);
			} catch (IOException io) {
				Log.e("Nathan", io.toString());
			}
		//}

		while(running) {
			if(socket != null) {
				try {
					DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					if(inputStream.available() > 0) {
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
