package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zelphinstudios.courseworkapp.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    public void onClick(View view_) {
        if(view_ == btnLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
