package zelphinstudios.courseworkapp.databases.remotedb;

import java.util.Vector;

import zelphinstudios.courseworkapp.databases.Account;

public interface AsyncResponse {
    void processFinish(Vector<Account> accounts_);
}
