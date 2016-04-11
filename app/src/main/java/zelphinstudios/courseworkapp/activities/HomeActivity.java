package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import zelphinstudios.courseworkapp.R;
import zelphinstudios.courseworkapp.networking.Client;
import zelphinstudios.courseworkapp.networking.Server;

public class HomeActivity extends Activity implements View.OnClickListener {

    // Variables
    private Button btnHost, btnJoin, btnSettings, btnScores;

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Setup UI buttons with click listeners
        btnHost = (Button)findViewById(R.id.btnHost);
        btnJoin = (Button)findViewById(R.id.btnJoin);
        btnSettings = (Button)findViewById(R.id.btnSettings);
        btnScores = (Button)findViewById(R.id.btnScores);
        btnHost.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnScores.setOnClickListener(this);
    }

    public void onClick(View view_) {
        if (view_ == btnHost) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("hosting", true);
            startActivity(intent);
        } else if (view_ == btnJoin) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("hosting", false);
            startActivity(intent);
        } else if (view_ == btnSettings) {

        } else if (view_ == btnScores) {

        }
    }

}