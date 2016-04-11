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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import zelphinstudios.courseworkapp.account.Account;

public class AddAccountTask extends AsyncTask<Account, Void, Void> {

    // Variables
    private final ProgressDialog progressDialog;

    // Constructor
    public AddAccountTask(Context context_) {
        progressDialog = new ProgressDialog(context_);
    }

    // Networking Variables
    private HttpClient httpClient = new DefaultHttpClient();
    private HttpPost httpPost = new HttpPost("http://94.194.36.248/public_html/insert.php");

    @Override
    protected void onPreExecute() {
        this.progressDialog.setMessage("Processing..");
        this.progressDialog.show();
    }

    @Override
    protected Void doInBackground(Account... accounts) {
        Account account = accounts[0];

        Vector<NameValuePair> accountDetails = new Vector<>(2);
        accountDetails.add(new BasicNameValuePair("Username", account.getUsername()));
        accountDetails.add(new BasicNameValuePair("Password", account.getPassword()));

        try {
            httpPost.addHeader("Authorization", "Basic " + Base64.encodeToString(("admin:A5cEn51On").getBytes(), Base64.NO_WRAP));
            httpPost.setEntity(new UrlEncodedFormEntity(accountDetails));
        } catch (UnsupportedEncodingException uee) { Log.e("Nathan", uee.toString()); }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.e("Nathan", responseString);
        } catch (IOException io) { Log.e("Nathan", io.toString()); }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        this.progressDialog.dismiss();
    }

}
