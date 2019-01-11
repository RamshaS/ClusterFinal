package edu.aku.ramshasaeed.vasadata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.aku.ramshasaeed.vasadata.Contracts.DistrictContract;
import edu.aku.ramshasaeed.vasadata.Contracts.MarkerContract;
import edu.aku.ramshasaeed.vasadata.Contracts.VerticesContract;
import edu.aku.ramshasaeed.vasadata.Core.AppMain;
import edu.aku.ramshasaeed.vasadata.Core.FormsDBHelper;
import edu.aku.ramshasaeed.vasadata.databinding.ActivityMainBinding;
import edu.aku.ramshasaeed.vasadata.get.GetDistricts;
import edu.aku.ramshasaeed.vasadata.get.GetVertices;
import edu.aku.ramshasaeed.vasadata.other.Dist_Prov_Data;
import edu.aku.ramshasaeed.vasadata.validation.validatorClass;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bi;
    private static final int MY_PERMISSIONS_REQUEST_GPS_LOCATION = 0;

    private ArrayList<DistrictContract> districts;
    private FormsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);

        db = new FormsDBHelper(MainActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();
        }

        bi.btnGetDistricts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDistricts(MainActivity.this) {
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        // Getting Districts Exist
                        if (db.getDistrictsExists())
                            bi.btnGetVertices.setEnabled(true);
                        else
                            bi.btnGetVertices.setEnabled(true);

                    }
                }.execute();
            }
        });

        bi.btnGetVertices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatorClass.EmptySpinner(MainActivity.this, bi.spProvince, "Province"))
                    return;

                if (!validatorClass.EmptySpinner(MainActivity.this, bi.spDistricts, "District"))
                    return;

                new GetVertices(MainActivity.this, districts.get(bi.spDistricts.getSelectedItemPosition() - 1).getDistrict_code()).execute();
//                new GetMarkers(MainActivity.this).execute();
            }
        });

        bi.btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatorClass.EmptySpinner(MainActivity.this, bi.spProvince, "Province"))
                    return;

                if (!validatorClass.EmptySpinner(MainActivity.this, bi.spDistricts, "District"))
                    return;

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(MainActivity.this, "Please make sure permission is granted to track Location!", Toast.LENGTH_SHORT).show();
                        checkAndRequestPermissions();
                    }

                } else {
                    Collection<VerticesContract> v = db.getVerticesByCluster(bi.txtPSU.getText().toString());
                    if (v.size() != 0) {
                        AppMain.hh02txt = bi.txtPSU.getText().toString();

                        if (v.size() > 3) {
                            startActivity(new Intent(MainActivity.this, MapsActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Cluster map do not exist  ", Toast.LENGTH_SHORT).show();
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

//        Getting Districts Exist
        if (!db.getDistrictsExists())
            bi.btnGetVertices.setEnabled(false);

//        POPULATING Spinners
        bi.spProvince.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, Dist_Prov_Data.getProvinceNames()));
        bi.spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayList<String> districtsName = new ArrayList<>();
                districtsName.add("....");

                if (i > 0) {
                    districts = db.getAllDistricts(Dist_Prov_Data.getProvinceCode(bi.spProvince.getSelectedItem().toString()));
                    for (DistrictContract districtDT : districts) {
                        districtsName.add(districtDT.getDistrict_name());
                    }
                }

                bi.spDistricts.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, districtsName));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }


}
