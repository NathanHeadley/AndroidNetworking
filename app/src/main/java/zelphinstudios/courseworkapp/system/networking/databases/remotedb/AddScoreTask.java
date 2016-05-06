package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import zelphinstudios.courseworkapp.system.activities.HighscoreActivity;

public class AddScoreTask extends AsyncTask<Integer, Void, Void> {

	private final ProgressDialog progressDialog;
	private Context context;

	public AddScoreTask(Context context_) {
		context = context_;
		progressDialog = new ProgressDialog(context_);
	}

	private HttpClient httpClient = new DefaultHttpClient();
	private HttpPost httpPost = new HttpPost("http://151.225.131.91/public_html/high_scores/add_score.php");

	@Override
	protected void onPreExecute() {
		this.progressDialog.setMessage("Processing..");
		this.progressDialog.show();
	}

	@Override
	protected Void doInBackground(Integer... integers) {
		Integer integer = integers[0];

		Vector<NameValuePair> scoreDetails = new Vector<>(2);
		scoreDetails.add(new BasicNameValuePair("score", String.valueOf(integer)));

		try {
			httpPost.addHeader("Authorization", "Basic " + Base64.encodeToString(("admin:A5cEn51On").getBytes(), Base64.NO_WRAP));
			httpPost.setEntity(new UrlEncodedFormEntity(scoreDetails));
		} catch (UnsupportedEncodingException uee) { uee.printStackTrace(); }

		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			Log.e("Nathan", responseString);
		} catch (IOException io) { io.printStackTrace(); }
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		this.progressDialog.dismiss();
	}
}
