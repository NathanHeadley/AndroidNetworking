package zelphinstudios.courseworkapp.account.remotedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

import zelphinstudios.courseworkapp.account.Account;

public class GetAccountsTask extends AsyncTask<Void, Void, Vector<Account>> {

    // Variables
    public AsyncResponse asyncResponse = null;
    private final ProgressDialog progressDialog;

    // Constructor
    public GetAccountsTask(Context context_) {
        progressDialog = new ProgressDialog(context_);
    }

    // Networking Variables
    private HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet("http://94.194.36.248/public_html/get.php");

    @Override
    protected void onPreExecute() {
        this.progressDialog.setMessage("Processing..");
        this.progressDialog.show();
    }

    @Override
    protected Vector<Account> doInBackground(Void... params) {
        Vector<Account> tempAccounts = new Vector<>();
        String responseString = "";

        try {
            httpGet.addHeader("Authorization", "Basic " + Base64.encodeToString(("admin:A5cEn51On").getBytes(), Base64.NO_WRAP));
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");
            Log.e("Nathan", responseString);
        } catch (IOException io) { Log.e("Nathan", io.toString()); }

        JSONArray accountArray = null;
        if (responseString != null && !responseString.equals("")) {
            try {
                accountArray = new JSONArray(responseString);
            } catch (JSONException e) { Log.e("Nathan", "JSON Error 1: " + e.toString()); }
        }

        if (accountArray != null) {
            for (int i = 0; i < accountArray.length(); i++) {
                try {
                    JSONObject accountEntry = accountArray.getJSONObject(i);
                    String username = accountEntry.get("Username").toString();
                    String password = accountEntry.get("Password").toString();
                    tempAccounts.add(new Account(username, password));
                } catch (JSONException je) { Log.e("Nathan", "JSON Error 2: " + je.toString()); }
            }
        }
        return tempAccounts;
    }

    @Override
    protected void onPostExecute(Vector<Account> accounts_) {
        this.progressDialog.dismiss();
        asyncResponse.processFinish(accounts_);
    }

}
