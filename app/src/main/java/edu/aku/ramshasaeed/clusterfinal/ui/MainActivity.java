package edu.aku.ramshasaeed.clusterfinal.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collection;

import edu.aku.ramshasaeed.clusterfinal.Contracts.BLRandomContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;
import edu.aku.ramshasaeed.clusterfinal.R;
import edu.aku.ramshasaeed.clusterfinal.databinding.ActivityMainBinding;
import edu.aku.ramshasaeed.clusterfinal.validation.ValidatorClass;

public class MainActivity extends MenuActivity {
    ActivityMainBinding bi;
    FormsDBHelper db;
    private Boolean exit = false;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dbManager = menu.findItem(R.id.menu_openDB);

        if (!AppMain.admin)
            dbManager.setVisible(false);


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);

        db = new FormsDBHelper(this);

    }

    public void openForm() {
        if (!ValidatorClass.EmptyTextBox(this, bi.txtPSU, "Enter Cluster"))
            return;

        AppMain.hh02txt = bi.txtPSU.getText().toString();

        Boolean loginFlag = false;
        int clus = Integer.valueOf(bi.txtPSU.getText().toString());
        if (clus < 6000) {
            loginFlag = !(AppMain.userEmail.equals("test1234") || AppMain.userEmail.equals("dmu@aku") || AppMain.userEmail.substring(0, 4).equals("user"));
        } else {
            loginFlag = AppMain.userEmail.equals("test1234") || AppMain.userEmail.equals("dmu@aku") || AppMain.userEmail.substring(0, 4).equals("user");
        }
        if (loginFlag) {

            BLRandomContract getBLData;
            String message;

            if (AppMain.PSUExist(bi.txtPSU.getText().toString())) {
                getBLData = db.lastBLRandomRecord(bi.txtPSU.getText().toString(), AppMain.hh03txt);

                message = "No more household found!!";

            } else {
                getBLData = db.lastBLRandomRecord(bi.txtPSU.getText().toString());

                message = "Cluster don't exist!!";
            }

            if (getBLData != null) {
                startActivity(new Intent(this, ValidatorActivity.class)
                        .putExtra("blData", getBLData));
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Can't proceed test cluster for current user!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void openMap() {

        if (!ValidatorClass.EmptyTextBox(this, bi.txtPSU, "Enter Cluster"))
            return;

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Toast.makeText(MainActivity.this, "Please make sure permission is granted to track Location!", Toast.LENGTH_SHORT).show();
                LoginActivity.checkAndRequestPermissions(MainActivity.this, MainActivity.this);
            }

        } else {
            FormsDBHelper db = new FormsDBHelper(MainActivity.this);
            Collection<VerticesContract> v = db.getVerticesByCluster(bi.txtPSU.getText().toString());
            if (v.size() != 0) {
                AppMain.hh02txt = bi.txtPSU.getText().toString();

                if (v.size() > 3) {
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Cluster map does not exist  ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "No Coordinates Found For this cluster!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity

            startActivity(new Intent(this, LoginActivity.class));

        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }

}
