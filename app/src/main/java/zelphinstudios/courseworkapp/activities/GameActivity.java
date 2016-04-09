package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import zelphinstudios.courseworkapp.views.GameView;

public class GameActivity extends Activity implements View.OnTouchListener {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);

        gameView = new GameView(this);

        setContentView(gameView);
    }

    @Override
    public boolean onTouch(View view_, MotionEvent motionEvent_) {
        if(motionEvent_.getAction() == MotionEvent.ACTION_DOWN) {
            //do somit
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }

}
