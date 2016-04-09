package zelphinstudios.courseworkapp.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AccountRemoteDatabaseHelper {

    private final String rootURL = "http://vmlwww07.uad.ac.uk/~1101102/"; //"vmlwww07.uad.ac.uk/~1101102/";http://mayar.abertay.ac.uk/~1101102/";
    private final String insertURL = rootURL + "insert.php";
    private final String getListURL = rootURL + "getlist.php";
    private static final String[] COLUMN_NAMES = {"Username", "Password"};
    private final Context parentActivityContext;

    private ArrayList<Account> accountsOutput = new ArrayList<>();

    public AccountRemoteDatabaseHelper(Context context_) {
        parentActivityContext = context_;
    }

    public void addAccount(Account account_) {
        addAccountTask task = new addAccountTask();
        task.execute(account_);
    }

    public ArrayList<Account> getAccountList() {
        getAccountsTask task = new getAccountsTask();
        task.execute();
        return accountsOutput;
    }

    private class addAccountTask extends AsyncTask<Account, Void, Void> {
        private final ProgressDialog progressDialog = new ProgressDialog(parentActivityContext);
        private HttpClient httpClient = new DefaultHttpClient();
        private HttpPost httpPost = new HttpPost(insertURL);

        @Override
        protected void onPreExecute() {
            this.progressDialog.setMessage("Adding account...");
            this.progressDialog.show();
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            Account account = accounts[0];

            ArrayList<NameValuePair> accountDetails = new ArrayList<NameValuePair>(2);
            accountDetails.add(new BasicNameValuePair(COLUMN_NAMES[0], account.getUsername()));
            accountDetails.add(new BasicNameValuePair(COLUMN_NAMES[1], account.getPassword()));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(accountDetails));
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity, "UTF-8");
                Log.e("Nathan", responseString);
            } catch (ClientProtocolException cpe) {
                cpe.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            this.progressDialog.dismiss();
        }

    }

    private class getAccountsTask extends AsyncTask<Void, Void, ArrayList<Account>> {
        private final ProgressDialog progressDialog = new ProgressDialog(parentActivityContext);
        private HttpClient httpClient = new DefaultHttpClient();
        private HttpGet httpGet = new HttpGet(getListURL);

        @Override
        protected void onPreExecute() {
            this.progressDialog.setMessage("Retrieving Account list");
            this.progressDialog.show();
        }

        @Override
        protected ArrayList<Account> doInBackground(Void... params) {

            HttpResponse response = null;
            String responseString = "";
            try {
                Log.e("Nathan", "beforeeeeeee");
                response = httpClient.execute(httpGet);
                Log.e("Nathan", "afterrrrrrrrrrrrr");
                HttpEntity entity = response.getEntity();
                responseString = EntityUtils.toString(entity, "UTF-8");

                Log.i("Response:", responseString);
            } catch (HttpHostConnectException e) {
                Log.e("Nathan", e.toString());
            } catch (ClientProtocolException e) {
                Log.e("Nathan", e.toString());
            } catch (IOException io) {
                Log.e("Nathan", io.toString());
            }

            // Process the results in background.
            JSONArray accountArray = null;
            if (responseString != null && responseString != "") {
                try {
                    accountArray = new JSONArray(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /* Create and ArrayList to hold our contacts. */
            ArrayList<Account> accounts = new ArrayList<Account>();

            if (accountArray != null) {
                /* Go through the list of contacts, and convert them to Contact objects, putting
                 those into an ArrayList. */
                for (int i = 0; i < accountArray.length(); i++) {
                    try {
                        JSONObject accountEntry = accountArray.getJSONObject(i);
                        String username = accountEntry.get(COLUMN_NAMES[0]).toString();
                        String password = accountEntry.get(COLUMN_NAMES[1]).toString();
                        // Log for debugging.
                        Log.i("Response ACCOUNT:", "" + username + ", " + password);
                        accounts.add(new Account(username, password));
                    } catch (JSONException e) {
                        // Log exceptions
                        e.printStackTrace();
                    }
                }
            }
            /* Return the list of contacts. */
            return accounts;
        }

        @Override
        protected void onPostExecute(ArrayList<Account> result) {
            accountsOutput = result;
            this.progressDialog.dismiss();
        }
    }
}
