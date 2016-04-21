package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import java.util.Vector;

import zelphinstudios.courseworkapp.system.networking.databases.Account;
import zelphinstudios.courseworkapp.system.networking.databases.Object;

public interface AsyncResponse {
    void processFinishAccounts(Vector<Account> accounts_);
    void processFinishObjects(Vector<Object> objects_);
}
