package zelphinstudios.courseworkapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zelphinstudios.courseworkapp.R;
import zelphinstudios.courseworkapp.networking.Client;
import zelphinstudios.courseworkapp.networking.Server;
import zelphinstudios.courseworkapp.views.GameView;

public class GameActivity extends Activity implements View.OnClickListener {

    private GameView gameView;
    private TextView txtConnected;
    private Button btnStartGame;
    private Server server;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        boolean hosting = intent.getBooleanExtra("hosting", false);
        if(hosting) {
            server = new Server();
            btnStartGame = new Button(this);
            btnStartGame.setText(R.string.startButtonText);
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            rl.addView(btnStartGame, lp);
            btnStartGame.setOnClickListener(this);
        }
        client = new Client();

        txtConnected = (TextView)findViewById(R.id.txtConnected);

        gameView = new GameView(this);
    }

    public void onClick(View view_) {
        if(view_ == btnStartGame) {
            if(server.getNumClients() < 2) {
                txtConnected.setText(String.format(getString(R.string.connectedText), server.getNumClients()));
                Log.e("Nathan", "" + server.getNumClients());
            } else {
                setContentView(gameView);
                Log.e("Nathan", "" + server.getNumClients());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(server != null) {
            server.onPause();
        }
        if(client != null) {
            client.onPause();
        }
        if(gameView != null) {
            gameView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(server != null) {
            server.onResume();
        }
        if(client != null) {
            client.onResume();
        }
        if(gameView != null) {
            gameView.onResume();
        }
    }

}
