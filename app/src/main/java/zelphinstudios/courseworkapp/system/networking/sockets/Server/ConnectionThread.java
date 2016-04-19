package zelphinstudios.courseworkapp.system.networking.sockets.server;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import zelphinstudios.courseworkapp.system.util.BaseThread;

public class ConnectionThread extends BaseThread {

	private Socket socket;
	private Handler serverHandler;

	public ConnectionThread(Socket socket_, Handler serverHandler_) {
		socket = socket_;
		serverHandler = serverHandler_;
		onResume();
	}

	@Override
	public void run() {
		while(running) {
			try {
				DataInputStream inputStream = new DataInputStream(socket.getInputStream());
				if(inputStream.available() > 0) {
					Message message = Message.obtain();
					message.obj = inputStream.readUTF();
					Log.e("Nathan", "Server received: " + message.toString());
					serverHandler.sendMessage(message);
				}
			} catch (IOException io) { io.printStackTrace(); }
		}
	}

	public void sendData(String data_) {
		try {
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			outputStream.writeUTF(data_);
		} catch (IOException io) { io.printStackTrace(); }
	}

}
