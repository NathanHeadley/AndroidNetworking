package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import android.content.Context;
import android.util.Log;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.networking.databases.Account;
import zelphinstudios.courseworkapp.system.networking.databases.Object;

public class RemoteDBHelper implements AsyncResponse {

    private Context context;
    private Vector<Account> accounts = new Vector<>();
	private Vector<Object> objects = new Vector<>();

    public RemoteDBHelper(Context context_, String mode_) {
        context = context_;
        if(mode_.equals("accounts")) {
            GetAccountsTask getAccountsTask = new GetAccountsTask(context_);
            getAccountsTask.asyncResponse = this;
            getAccountsTask.execute();
        } else if(mode_.equals("objects")) {
			GetObjectsTask getObjectsTask = new GetObjectsTask(context_);
	        getObjectsTask.asyncResponse = this;
	        getObjectsTask.execute();
        }
    }

    public void addAccount(Account account_) {
        AddAccountTask task = new AddAccountTask(context);
        task.execute(account_);
    }

    public void processFinishAccounts(Vector<Account> accounts_) {
        accounts = accounts_;
    }

	public void processFinishObjects(Vector<Object> objects_) {
		objects = objects_;
	}

    public Vector<Account> getAccounts() {
        Log.e("Nathan", "Returning: " + accounts.size() + " accounts!");
        return accounts;
    }

	public Vector<Object> getObjects() {
		Log.e("Nathan", "Returning: " + objects.size() + " objects!");
		return objects;
	}

}
