package zelphinstudios.courseworkapp.networking;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    // Variables
    private Socket socket = null;
    private final String serverAddress = "10.0.2.2";
    private final int serverPort = 5050;
    private String inData = "";

    public void connectToServer() {
        ConnectToServer connectToServer = new ConnectToServer();
        connectToServer.execute(socket);
    }

    public String getData() {
        GetData getData = new GetData();
        getData.execute();
        Log.e("Nathan", "Client returning: " + inData);
        return inData;
    }

    public void sendData(String data_) {
        SendData sendData = new SendData();
        sendData.execute(data_);
    }

    private class ConnectToServer extends AsyncTask<Socket, Void, Socket> {
        @Override
        protected Socket doInBackground(Socket... socket_) {
            Socket socket = socket_[0];
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
            if(socket == null) {
                try {
                    socket = new Socket(serverAddress, serverPort);
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
            return socket;
        }

        @Override
        protected void onPostExecute(Socket socket_) {
            socket = socket_;
        }
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                inData = inputStream.readUTF();Log.e("Nathan", "Received message: " + inData);
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
            return null;
        }
    }

    private class SendData extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... data_) {
            String data = data_[0];
            try {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(data);
                Log.e("Nathan", "Client->ServerThread | " + data);
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
            return null;
        }
    }

}
