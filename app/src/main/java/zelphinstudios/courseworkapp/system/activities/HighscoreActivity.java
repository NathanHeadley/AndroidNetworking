package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

import zelphinstudios.courseworkapp.R;
import zelphinstudios.courseworkapp.system.networking.databases.remotedb.RemoteDBHelper;

public class HighscoreActivity extends Activity implements View.OnClickListener {

	private Vector<Integer> scores = new Vector<>();
	private RemoteDBHelper remoteDBHelper;
	private TextView[] textViews = new TextView[2];
	private Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);

		textViews[0] = (TextView)findViewById(R.id.txtScore1);
		textViews[1] = (TextView)findViewById(R.id.txtScore2);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(this);

		remoteDBHelper = new RemoteDBHelper(this, "scores", textViews);
		scores = remoteDBHelper.getScores();
	}

	public void onClick(View view_) {
		if(view_ == btnBack) {
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
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
