package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import android.content.Context;
import android.util.Log;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.networking.databases.Account;

public class RemoteDBHelper implements AsyncResponse {

    private Context context;
    private Vector<Account> accounts = new Vector<>();

    public RemoteDBHelper(Context context_) {
        context = context_;
        GetAccountsTask getAccountsTask = new GetAccountsTask(context_);
        getAccountsTask.asyncResponse = this;
        getAccountsTask.execute();
    }

    public void addAccount(Account account_) {
        AddAccountTask task = new AddAccountTask(context);
        task.execute(account_);
    }

    public void processFinish(Vector<Account> accounts_) {
        accounts = accounts_;
    }

    public Vector<Account> getAccounts() {
        Log.e("Nathan", "Returning: " + accounts.size() + " accounts!");
        return accounts;
    }

}
