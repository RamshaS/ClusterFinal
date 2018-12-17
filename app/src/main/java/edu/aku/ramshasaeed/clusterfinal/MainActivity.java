package edu.aku.ramshasaeed.clusterfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;
import edu.aku.ramshasaeed.clusterfinal.databinding.ActivityMainBinding;
import edu.aku.ramshasaeed.clusterfinal.get.GetVertices;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bi;
    private static final int MY_PERMISSIONS_REQUEST_GPS_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             checkAndRequestPermissions();
        }

        bi.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetVertices(MainActivity.this).execute();
//                new GetMarkers(MainActivity.this).execute();
//                new GetDistricts(MainActivity.this).execute();
            }
        });

        bi.btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(MainActivity.this,"Please make sure permission is granted to track Location!",Toast.LENGTH_SHORT).show();
                        checkAndRequestPermissions();
                        }

                }else {
                    FormsDBHelper db = new FormsDBHelper(MainActivity.this);
                    Collection<VerticesContract> v = db.getVerticesByCluster(bi.txtPSU.getText().toString());
                    if(v.size() != 0){
                        AppMain.hh02txt = bi.txtPSU.getText().toString();

                        if (v.size() > 3) {
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Cluster map do not exist  ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "No Coordinates Found For this cluster!!!", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }
    private boolean checkAndRequestPermissions() {

        int accessFineLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int accessCoarseLocation = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_GPS_LOCATION);
            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
          if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                }
            } else if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }


}
