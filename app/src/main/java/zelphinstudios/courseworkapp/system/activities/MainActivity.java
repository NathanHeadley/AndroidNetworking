package zelphinstudios.courseworkapp.system.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zelphinstudios.courseworkapp.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;
	private Context context = this;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String service = Context.CONNECTIVITY_SERVICE;
        connectivityManager = (ConnectivityManager)getSystemService(service);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    public void onClick(View view_) {
        if(view_ == btnLogin) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
	            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	            alertDialogBuilder.setTitle("No Network Found!");
	            alertDialogBuilder
			            .setMessage("You don't have an internet connection. You will need to connect to the internet to login and play!")
			            .setCancelable(false)
			            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog_, int id_) {
					            dialog_.cancel();
				            }
			            });
	            AlertDialog alertDialog = alertDialogBuilder.create();
	            alertDialog.show();
            } else if(networkInfo != null) {
                if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
	                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	                alertDialogBuilder.setTitle("Using Network Data!");
	                alertDialogBuilder
			                .setMessage("You are not using a Wi-Fi connection. Playing this game could be costly on mobile data!")
			                .setCancelable(false)
			                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog_, int id_) {
					                dialog_.cancel();
					                Intent intent = new Intent(context, LoginActivity.class);
					                startActivity(intent);
				                }
			                })
			                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog_, int id_) {
					                MainActivity.this.finish();
				                }
			                });
	                AlertDialog alertDialog = alertDialogBuilder.create();
	                alertDialog.show();
                } else {
	                Intent intent = new Intent(this, LoginActivity.class);
	                startActivity(intent);
                }
            }
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
