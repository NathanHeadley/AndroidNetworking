package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zelphinstudios.courseworkapp.R;

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT < 19) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
            } else {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

}