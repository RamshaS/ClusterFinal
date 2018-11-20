package edu.aku.ramshasaeed.clusterfinal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;

import edu.aku.ramshasaeed.clusterfinal.Contracts.MarkerContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;
import edu.aku.ramshasaeed.clusterfinal.databinding.ActivityMainBinding;
import edu.aku.ramshasaeed.clusterfinal.get.GetDistricts;
import edu.aku.ramshasaeed.clusterfinal.get.GetMarkers;
import edu.aku.ramshasaeed.clusterfinal.get.GetVertices;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);

        bi.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetVertices(MainActivity.this).execute();
                new GetMarkers(MainActivity.this).execute();
//                new GetDistricts(MainActivity.this).execute();
            }
        });

        bi.btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        bi.btnOpenMarker.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                FormsDBHelper db = new FormsDBHelper(MainActivity.this);
                Collection<MarkerContract> m = db.getMarkers(bi.txtPSU.getText().toString());

                if(m.size() != 0){
                    AppMain.hh02txt = bi.txtPSU.getText().toString();
                    if (m.size() > 3) {
                        startActivity(new Intent(MainActivity.this, MarkerMapsActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Cluster map do not exist  ", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "No Coordinates Found For this cluster!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
