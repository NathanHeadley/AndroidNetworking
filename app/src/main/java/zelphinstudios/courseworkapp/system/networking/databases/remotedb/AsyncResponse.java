package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.networking.databases.Account;

public interface AsyncResponse {
    void processFinish(Vector<Account> accounts_);
}
