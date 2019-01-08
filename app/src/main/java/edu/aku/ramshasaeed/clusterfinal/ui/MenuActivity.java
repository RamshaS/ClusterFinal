package edu.aku.ramshasaeed.clusterfinal.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.aku.ramshasaeed.clusterfinal.Contracts.BLRandomContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.DistrictContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.ListingFormContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.UsersContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Core.AndroidDatabaseManager;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;
import edu.aku.ramshasaeed.clusterfinal.R;
import edu.aku.ramshasaeed.clusterfinal.get.GetAllData;
import edu.aku.ramshasaeed.clusterfinal.sync.SyncAllData;
import edu.aku.ramshasaeed.clusterfinal.sync.SyncDevice;

public class MenuActivity extends AppCompatActivity implements SyncDevice.SyncDevicInterface {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    String DirectoryName;
    FormsDBHelper db;
    boolean flagSync = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new FormsDBHelper(this);
        dbBackup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                LoginActivity.flag = true;
                onSyncDataClick();
                return true;
            case R.id.menu_upload:
                syncServer();
                return true;

            case R.id.menu_openDB:
                Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
                startActivity(dbmanager);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onSyncDataClick() {
        //TODO implement

        // Require permissions INTERNET & ACCESS_NETWORK_STATE
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            flagSync = true;

            Toast.makeText(this, "Syncing start..", Toast.LENGTH_LONG).show();
            new SyncDevice(this, true).execute();


        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void dbBackup() {

        sharedPref = getSharedPreferences("src", MODE_PRIVATE);
        editor = sharedPref.edit();

        if (sharedPref.getBoolean("flag", false)) {

            String dt = sharedPref.getString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));

            if (dt != new SimpleDateFormat("dd-MM-yy").format(new Date())) {
                editor.putString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));

                editor.commit();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + FormsDBHelper.PROJECT_NAME);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {

                DirectoryName = folder.getPath() + File.separator + sharedPref.getString("dt", "");
                folder = new File(DirectoryName);
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }
                if (success) {

                    try {
                        File dbFile = new File(this.getDatabasePath(FormsDBHelper.DATABASE_NAME).getPath());
                        FileInputStream fis = new FileInputStream(dbFile);

                        String outFileName = DirectoryName + File.separator +
                                FormsDBHelper.DB_NAME;

                        // Open the empty db as the output stream
                        OutputStream output = new FileOutputStream(outFileName);

                        // Transfer bytes from the inputfile to the outputfile
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        // Close the streams
                        output.flush();
                        output.close();
                        fis.close();
                    } catch (IOException e) {
                        Log.e("dbBackup:", e.getMessage());
                    }

                }

            } else {
                Toast.makeText(this, "Not create folder", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void syncServer() {

        // Require permissions INTERNET & ACCESS_NETWORK_STATE
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            flagSync = false;

            Toast.makeText(this, "Device Syncing..", Toast.LENGTH_SHORT).show();
            new SyncDevice(this, false).execute();

            Toast.makeText(getApplicationContext(), "Syncing Forms", Toast.LENGTH_SHORT).show();
            new SyncAllData(
                    this,
                    "Forms",
                    "updateSyncedListing",
                    ListingFormContract.class,
                    AppMain._HOST_URL + ListingFormContract.ListingFormEntry._URL,
                    db.getAllFormListings()
            ).execute();

            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastUpSyncServer", dtToday);

            editor.apply();

        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void processFinish(boolean flag) {
        if (flag && flagSync) {
            HashMap<String, String> tagVal = AppMain.getTagValues(this);
            new syncData(this, tagVal.get("org") != null ? tagVal.get("org").equals("null") ? null : tagVal.get("org") : null).execute();
        }
    }

    public class syncData extends AsyncTask<String, String, String> {

        private Context mContext;
        String orgID;

        public syncData(Context mContext, String orgID) {
            this.mContext = mContext;
            this.orgID = orgID;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (AppMain.district_code.equals("")) {
                        new GetAllData(mContext, "User", AppMain._HOST_URL + UsersContract.UsersTable._URI).execute();
                        new GetAllData(mContext, "Districts", AppMain._HOST_URL + DistrictContract.singleDistricts._URI).execute();
                    } else {
                        new GetAllData(mContext, "Vertices", AppMain._HOST_URL + VerticesContract.singleVertices._URI).execute(orgID);
                        new GetAllData(mContext, "BLRandom", AppMain._HOST_URL + BLRandomContract.singleRandomHH._URI).execute(orgID);
                    }
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

//                    populateSpinner(mContext);

                    editor.putBoolean("flag", true);
                    editor.commit();

                    dbBackup();

                }
            }, 1200);
        }
    }
}
