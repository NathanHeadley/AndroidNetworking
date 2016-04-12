package zelphinstudios.courseworkapp.networking;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    public void run (){}

    public void startServer() {
        StartServer startServer = new StartServer();
        startServer.execute();
    }

    public void checkConnections() {
        CheckConnections checkConnections = new CheckConnections();
        checkConnections.execute();
    }

    public String getData() {
        GetData getData = new GetData();
        getData.execute();
        Log.e("Nathan", "Returning: " + inData);
        return inData;
    }

    public void sendData(String data_) {
        SendData sendData = new SendData();
        sendData.execute(data_);
    }

    private class StartServer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
            if(socket == null) {
                try {
                    socket = new ServerSocket(serverPort);
                    Log.e("Nathan", "ServerThread started on port: " + serverPort);
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
            return null;
        }
    }

    private class CheckConnections extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 0; i < clients.size(); i++) {
                if(clients.get(i) == null) {
                    clients.remove(i);
                }
            }
            try {
                clients.addElement(socket.accept());
                Log.e("Nathan", "Accepted connection from: " + clients.get(clients.size() - 1).getRemoteSocketAddress());
            } catch (IOException io) { Log.e("Nathan", io.toString()); }
            return null;
        }
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            for(Socket client : clients) {
                try {
                    DataInputStream inputStream = new DataInputStream(client.getInputStream());
                    inData = inputStream.readUTF();
                    Log.e("Nathan", "Received message: " + inData);
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
            return null;
        }
    }

    private class SendData extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... data_) {
            for(Socket client: clients) {
                try {
                    DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
                    outputStream.writeUTF(data_[0]);
                    Log.e("Nathan", "Sent message: " + data_[0]);
                } catch (IOException io) { Log.e("Nathan", io.toString()); }
            }
            return null;
        }
    }

}
