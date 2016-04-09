package zelphinstudios.courseworkapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import zelphinstudios.courseworkapp.R;
import zelphinstudios.courseworkapp.account.Account;
import zelphinstudios.courseworkapp.account.AccountDatabaseHelper;
import zelphinstudios.courseworkapp.account.AccountRemoteDatabaseHelper;

public class LoginActivity extends Activity implements View.OnClickListener {

    // Variables
    private Button btnLogin, btnRegister;
    private EditText txtUsername, txtPassword;
    private AccountDatabaseHelper accountDatabaseHelper;
    private AccountRemoteDatabaseHelper accountRemoteDatabaseHelper;

    // Methods
    @Override
    protected void onCreate(Bundle savedInstanceState_) {
        super.onCreate(savedInstanceState_);
        setContentView(R.layout.activity_login);

        // Initialise and set up variables
        accountDatabaseHelper = new AccountDatabaseHelper(this);
        accountRemoteDatabaseHelper = new AccountRemoteDatabaseHelper(this);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void onClick(View view_) {
        if(view_ == btnLogin) { // If login button is pressed
            if(addToAccounts(txtUsername.getText().toString(), txtPassword.getText().toString())) { // If it didn't exist already
                Toast.makeText(this, "You have created an account!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                Account account = accountDatabaseHelper.getAccount(txtUsername.getText().toString());
                if(account.getPassword().equals(txtPassword.getText().toString())) {
                    Toast.makeText(this, "Welcome " + txtUsername.getText() + "!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Incorrect username/password combination!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if(view_ == btnRegister) { // If register button is pressed
            if(addToAccounts(txtUsername.getText().toString(), txtPassword.getText().toString())) { // If it didn't exist already
                Toast.makeText(this, "You have created an account!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "That account already exists!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean addToAccounts(String username_, String password_) {
        if(accountDatabaseHelper.addAccount(new Account(username_, password_))) {
            accountRemoteDatabaseHelper.addAccount(new Account(username_, password_));
            return true;
        }
        return false;
    }

    public void downloadRemoteDB() {
        ArrayList<Account> accounts = accountRemoteDatabaseHelper.getAccountList();
        for(int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            accountDatabaseHelper.addAccount(account);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadRemoteDB();
    }

}
