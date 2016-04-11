package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

import zelphinstudios.courseworkapp.R;
import zelphinstudios.courseworkapp.account.remotedb.AsyncResponse;
import zelphinstudios.courseworkapp.account.remotedb.GetAccountsTask;
import zelphinstudios.courseworkapp.account.remotedb.RemoteDBHelper;
import zelphinstudios.courseworkapp.account.Account;
import zelphinstudios.courseworkapp.account.LocalDBHelper;

public class LoginActivity extends Activity implements View.OnClickListener {

    // Variables
    private Button btnLogin, btnRegister;
    private EditText txtUsername, txtPassword;
    private LocalDBHelper localDBHelper;
    private RemoteDBHelper remoteDBHelper;
    private Vector<Account> accounts = new Vector<>();

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setContentView(R.layout.activity_login);

        // Initialise and set up variables
        localDBHelper = new LocalDBHelper(this);
        remoteDBHelper = new RemoteDBHelper(this);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void onClick(View view_) {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if(accounts.size() == 0) {
            accounts = remoteDBHelper.getAccounts();
        } else {
            Log.e("Nathan", "Accounts size = " + accounts.size());
            for(Account account : accounts) {
                Log.e("Nathan", account.getUsername());
            }
        }
        if(view_ == btnLogin) { // If login button is pressed
            Account account = localDBHelper.getAccount(username);
            if(account == null) {
                addAccount(username, password);
                Toast.makeText(this, "You have created an account!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                if(account.getPassword().equals(password)) {
                    Toast.makeText(this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Incorrect username/password combination!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if(view_ == btnRegister) { // If register button is pressed
            Account account = localDBHelper.getAccount(username);
            if(account == null) {
                addAccount(username, password);
                Toast.makeText(this, "You have created an account!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "That account already exists!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addAccount(String username_, String password_) {
        localDBHelper.addAccount(username_, password_);
        remoteDBHelper.addAccount(new Account(username_, password_));
    }

}
