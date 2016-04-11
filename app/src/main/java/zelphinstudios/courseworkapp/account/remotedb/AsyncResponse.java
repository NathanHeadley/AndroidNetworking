package zelphinstudios.courseworkapp.account.remotedb;

import java.util.Vector;

import zelphinstudios.courseworkapp.account.Account;

public interface AsyncResponse {
    void processFinish(Vector<Account> accounts_);
}
