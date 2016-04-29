package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.networking.databases.Account;
import zelphinstudios.courseworkapp.system.networking.databases.Object;

public class RemoteDBHelper implements AsyncResponse {

    private Context context;
    private Vector<Account> accounts = new Vector<>();
	private Vector<Object> objects = new Vector<>();
	private Vector<Integer> scores = new Vector<>();

	public RemoteDBHelper(Context context_, String mode_) {
		context = context_;
		switch(mode_) {
			case "accounts":
				GetAccountsTask getAccountsTask = new GetAccountsTask(context_);
				getAccountsTask.asyncResponse = this;
				getAccountsTask.execute();
				break;
			case "objects":
				GetObjectsTask getObjectsTask = new GetObjectsTask(context_);
				getObjectsTask.asyncResponse = this;
				getObjectsTask.execute();
				break;
		}
	}

	public RemoteDBHelper(Context context_, String mode_, TextView[] textViews_) {
		context = context_;
		switch(mode_) {
			case "scores":
				GetScoresTask getScoresTask = new GetScoresTask(context_);
				getScoresTask.asyncResponse = this;
				getScoresTask.execute(textViews_);
				break;
		}
	}

    public void addAccount(Account account_) {
        AddAccountTask task = new AddAccountTask(context);
        task.execute(account_);
    }

	public void addScore(int score_) {
		AddScoreTask task = new AddScoreTask(context);
		task.execute(score_);
	}

    public void processFinishAccounts(Vector<Account> accounts_) {
        accounts = accounts_;
    }

	public void processFinishObjects(Vector<Object> objects_) {
		objects = objects_;
	}

	public void processFinishScores(Vector<Integer> scores_) {
		scores = scores_;
	}

    public Vector<Account> getAccounts() {
        Log.e("Nathan", "Returning: " + accounts.size() + " accounts!");
        return accounts;
    }

	public Vector<Object> getObjects() {
		Log.e("Nathan", "Returning: " + objects.size() + " objects!");
		return objects;
	}

	public Vector<Integer> getScores() {
		return scores;
	}

}
