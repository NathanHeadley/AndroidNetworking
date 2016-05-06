package zelphinstudios.courseworkapp.system.networking.databases.remotedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

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

import zelphinstudios.courseworkapp.system.networking.databases.Object;

public class GetObjectsTask extends AsyncTask<Void, Void, Vector<Object>> {

	// Variables
	public AsyncResponse asyncResponse = null;
	private final ProgressDialog progressDialog;

	// Constructor
	public GetObjectsTask(Context context_) {
		progressDialog = new ProgressDialog(context_);
	}

	// Networking Variables
	private HttpClient httpClient = new DefaultHttpClient();
	HttpGet httpGet = new HttpGet("http://151.225.131.91/public_html/objects/get_objects.php");

	@Override
	protected void onPreExecute() {
		this.progressDialog.setMessage("Processing..");
		this.progressDialog.show();
	}

	@Override
	protected Vector<Object> doInBackground(Void... params) {
		Vector<Object> tempObjects = new Vector<>();
		String responseString = "";

		try {
			httpGet.addHeader("Authorization", "Basic " + Base64.encodeToString(("admin:A5cEn51On").getBytes(), Base64.NO_WRAP));
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException io) { Log.e("Nathan", io.toString()); }

		JSONArray objectArray = null;
		if (responseString != null && !responseString.equals("")) {
			try {
				objectArray = new JSONArray(responseString);
			} catch (JSONException e) { Log.e("Nathan", "JSON Error 1: " + e.toString()); }
		}

		if (objectArray != null) {
			for (int i = 0; i < objectArray.length(); i++) {
				try {
					JSONObject objectEntry = objectArray.getJSONObject(i);
					int id = Integer.parseInt((String)objectEntry.get("id"));
					int x = Integer.parseInt((String)objectEntry.get("x"));
					int y = Integer.parseInt((String)objectEntry.get("y"));
					tempObjects.add(new Object(id, x, y));
				} catch (JSONException je) { Log.e("Nathan", "JSON Error 2: " + je.toString()); }
			}
		}
		return tempObjects;
	}

	@Override
	protected void onPostExecute(Vector<Object> objects_) {
		this.progressDialog.dismiss();
		asyncResponse.processFinishObjects(objects_);
	}

}

