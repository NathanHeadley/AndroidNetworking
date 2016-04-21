package zelphinstudios.courseworkapp.system.networking.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

import zelphinstudios.courseworkapp.game.entities.PlayerEntity;

public class LocalDBHelper extends SQLiteOpenHelper {

    private static final String[] ACCOUNTS_COLUMN_NAMES = {"Username", "Password"};
    private static final String ACCOUNTS_TABLE_CREATE = "CREATE TABLE accounts (Username TEXT, Password TEXT);";

    public LocalDBHelper(Context context_) {
        super(context_, "1101102", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database_) {
        database_.execSQL(ACCOUNTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database_, int oldVersion, int newVersion) {}

    public boolean addAccount(String username_, String password_) {
        if(getAccount(username_) == null) {
            ContentValues contentRow = new ContentValues();
            contentRow.put("Username", username_);
            contentRow.put("Password", password_);
            SQLiteDatabase database = this.getWritableDatabase();
            database.insert("accounts", null, contentRow);
            database.close();
            return true;
        }
        return false;
    }

    public Vector<Account> getAccountList() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor result = database.query("accounts", ACCOUNTS_COLUMN_NAMES, null, null, null, null, null, null);
        Vector<Account> accounts = new Vector<>();
        for(int i = 0; i < result.getCount(); i++) {
            result.moveToPosition(i);
            accounts.add(new Account(result.getString(0), result.getString(1)));
        }
        result.close();
        database.close();
        return accounts;
    }

    public Account getAccount(String username_) {
        Vector<Account> accounts = getAccountList();
        for(int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if(account.getUsername().equals(username_)) {
                return account;
            }
        }
        return null;
    }

}
