package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

import zelphinstudios.courseworkapp.R;

public class GetScoresTask extends AsyncTask<TextView, Void, Vector<Integer>> {

	public AsyncResponse asyncResponse = null;
	private final ProgressDialog progressDialog;
	private Context context;
	private TextView textViews[] = new TextView[2];

	public GetScoresTask(Context context_) {
		context = context_;
		progressDialog = new ProgressDialog(context_);
	}

	private HttpClient httpClient = new DefaultHttpClient();
	private HttpGet httpGet = new HttpGet("http://151.225.131.91/public_html/high_scores/get_scores.php");

	@Override
	protected void onPreExecute() {
		this.progressDialog.setMessage("Processing..");
		this.progressDialog.show();
	}

	@Override
	protected Vector<Integer> doInBackground(TextView... params) {
		Vector<Integer> tempScores = new Vector<>();
		String responseString = "";
		if(params != null && params.length > 0) {
			textViews[0] = params[0];
			textViews[1] = params[1];
		}

		try {
			httpGet.addHeader("Authorization", "Basic " + Base64.encodeToString(("admin:A5cEn51On").getBytes(), Base64.NO_WRAP));
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException io) { io.printStackTrace(); }

		JSONArray scoresArray = null;
		if(responseString != null && !responseString.equals("")) {
			try {
				scoresArray = new JSONArray(responseString);
			} catch (JSONException e) { e.printStackTrace(); }
		}

		if(scoresArray != null) {
			for(int i = 0; i < scoresArray.length(); i++) {
				try {
					JSONObject scoreEntry = scoresArray.getJSONObject(i);
					int score = Integer.parseInt(scoreEntry.get("score").toString());
					tempScores.addElement(score);
				} catch (JSONException e) { e.printStackTrace(); }
			}
		}
		Log.e("Nathan", ""+tempScores.size());
		return tempScores;
	}

	@Override
	protected void onPostExecute(Vector<Integer> scores_) {
		this.progressDialog.dismiss();
		asyncResponse.processFinishScores(scores_);
		int[] highest = {0, 0};
		for(int score : scores_) {
			if(score > highest[0]) {
				highest[1] = highest[0];
				highest[0] = score;
			} else if(score > highest[1]) {
				highest[1] = score;
			}
		}
		textViews[0].setText(context.getString(R.string.highscore_text_1, highest[0]));
		textViews[1].setText(context.getString(R.string.highscore_text_2, highest[1]));
	}

}
