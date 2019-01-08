package edu.aku.ramshasaeed.clusterfinal.get;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;


public class GetAllData extends AsyncTask<String, String, String> {

    HttpURLConnection urlConnection;
    private String TAG = "";
    private Context mContext;
    private ProgressDialog pd;
    private String syncClass, URL;


    public GetAllData(Context context, String syncClass, String url) {
        mContext = context;
        this.syncClass = syncClass;
        this.URL = url;

        TAG = "Get" + syncClass;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Syncing " + syncClass);
        pd.setMessage("Getting connected to server...");
        pd.show();

    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        URL url = null;
        try {
            url = new URL(URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            switch (syncClass) {
                case "BLRandom":
                case "Vertices":

//                    if (args[0] != null && !args[0].equals("")) {
//                        if (Integer.valueOf(args[0]) > 0) {
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("charset", "utf-8");
                    urlConnection.setUseCaches(false);

                    // Starts the query
                    urlConnection.connect();
                    JSONArray jsonSync = new JSONArray();
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    JSONObject json = new JSONObject();
                    try {

                        if (syncClass.equals("BLRandom")) {
                            String argId = args[0] == null || args[0].equals("") || Integer.valueOf(args[0]) == 0 ? "1" : args[0];
                            json.put("id_org", argId);
                        }
                        json.put("pcode", AppMain.district_code);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    Log.d(TAG, "downloadUrl: " + json.toString());
                    wr.writeBytes(json.toString());
                    wr.flush();
                    wr.close();
//                        }
//                    }
                    break;
            }


            Log.d(TAG, "doInBackground: " + urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, syncClass + " In: " + line);
                    result.append(line);
                }
            }
        } catch (java.net.SocketTimeoutException e) {
            return null;
        } catch (java.io.IOException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        //Do something with the JSON string
        if (result != null) {
            String json = result;
            if (json.length() > 0) {
                try {
                    JSONArray jsonArray = new JSONArray(json);

                    FormsDBHelper db = new FormsDBHelper(mContext);

                    switch (syncClass) {
                        case "User":
                            db.syncUsers(jsonArray);
                            break;
                        case "BLRandom":
                            db.syncBLRandom(jsonArray);
                            break;
                        case "Vertices":
                            db.syncVertices(jsonArray);
                            break;
                        case "Districts":
                            db.syncDistricts(jsonArray);
                            break;
                    }

                    pd.setMessage("Received: " + jsonArray.length());
                    pd.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                pd.setMessage("Received: " + json.length() + "");
                pd.show();
            }
        } else {
            pd.setTitle("Connection Error");
            pd.setMessage("Server not found!");
            pd.show();
        }
    }

}
