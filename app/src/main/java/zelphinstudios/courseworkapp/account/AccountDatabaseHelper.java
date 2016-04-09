package zelphinstudios.courseworkapp.account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AccountDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "1101102";
    private static final String ACCOUNTS_TABLE_NAME = "accounts";
    private static final String[] COLUMN_NAMES = {"Username", "Password"};
    private static final String ACCOUNTS_TABLE_CREATE = "CREATE TABLE " + ACCOUNTS_TABLE_NAME +
            " (" + COLUMN_NAMES[0] + " TEXT, " + COLUMN_NAMES[1] + " TEXT);";

    public AccountDatabaseHelper(Context context_) {
        super(context_, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database_) {
        database_.execSQL(ACCOUNTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database_, int oldVersion, int newVersion) {}

    public boolean addAccount(Account account_) {
        if(!accountExists(account_)) {
            ContentValues contentRow = new ContentValues();
            contentRow.put(COLUMN_NAMES[0], account_.getUsername());
            contentRow.put(COLUMN_NAMES[1], account_.getPassword());
            SQLiteDatabase database = this.getWritableDatabase();
            database.insert(ACCOUNTS_TABLE_NAME, null, contentRow);
            database.close();
            return true;
        }
        return false;
    }

    /*public int getNumAccounts() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor result = database.query(ACCOUNTS_TABLE_NAME, COLUMN_NAMES, null, null, null, null, null, null);
        if(result != null) {
            int count = result.getCount();
            result.close();
            database.close();
            return count;
        }
        database.close();
        return -1;
    }*/

    public Account getAccount(String username_) {
        ArrayList<Account> accounts = getAccountList();
        for(int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if(account.getUsername().equals(username_)) { //dun care but passwurd
                return account;
            }
        }
        return new Account("", "");
    }

    public ArrayList<Account> getAccountList() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor result = database.query(ACCOUNTS_TABLE_NAME, COLUMN_NAMES, null, null, null, null, null, null);
        ArrayList<Account> accounts = new ArrayList<Account>();
        for(int i = 0; i < result.getCount(); i++) {
            result.moveToPosition(i);
            accounts.add(new Account(result.getString(0), result.getString(1)));
        }
        result.close();
        database.close();
        return accounts;
    }

    public boolean accountExists(Account account_) {
        ArrayList<Account> accounts = getAccountList();
        for(int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if(account.getUsername().equals(account_.getUsername())) { //dun care but passwurd
                return true;
            }
        }
        return false;
    }

}
