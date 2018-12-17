package edu.aku.ramshasaeed.clusterfinal.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;

import edu.aku.ramshasaeed.clusterfinal.Contracts.MarkerContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;
import edu.aku.ramshasaeed.clusterfinal.R;
import edu.aku.ramshasaeed.clusterfinal.databinding.ActivityMainBinding;

public class MainActivity extends MenuActivity {
    ActivityMainBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);

        bi.btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        bi.btnOpenMarker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FormsDBHelper db = new FormsDBHelper(MainActivity.this);
                db.getAllMarkers();
                Collection<MarkerContract> m = db.getMarkers(bi.txtPSU.getText().toString());

                if (m.size() != 0) {
                    AppMain.hh02txt = bi.txtPSU.getText().toString();
                    if (m.size() > 3) {
                        startActivity(new Intent(MainActivity.this, MarkerMapsActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Cluster map do not exist  ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No Coordinates Found For this cluster!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void openForm() {

    }

}
