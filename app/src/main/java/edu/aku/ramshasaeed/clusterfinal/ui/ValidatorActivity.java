package edu.aku.ramshasaeed.clusterfinal.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.ramshasaeed.clusterfinal.Contracts.BLRandomContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.ListingFormContract;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.Core.FormsDBHelper;
import edu.aku.ramshasaeed.clusterfinal.R;
import edu.aku.ramshasaeed.clusterfinal.validation.ClearClass;
import edu.aku.ramshasaeed.clusterfinal.validation.ValidatorClass;

public class ValidatorActivity extends AppCompatActivity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtTeamNoWithFam)
    TextView txtTeamNoWithFam;
    @BindView(R.id.hh08)
    EditText hh08;

    @BindView(R.id.fldGrpa01)
    LinearLayout fldGrpa01;

    @BindView(R.id.hh29)
    RadioGroup hh29;
    @BindView(R.id.hh29a)
    RadioButton hh29a;
    @BindView(R.id.hh29b)
    RadioButton hh29b;
    @BindView(R.id.hh29c)
    RadioButton hh029c;

    @BindView(R.id.hh16)
    EditText hh16;
    @BindView(R.id.hh18)
    EditText hh18;
    @BindView(R.id.hh19)
    EditText hh19;
    @BindView(R.id.hh20)
    EditText hh20;
    @BindView(R.id.hh21)
    EditText hh21;
    @BindView(R.id.hh22)
    EditText hh22;
    @BindView(R.id.hh23)
    EditText hh23;
    @BindView(R.id.hh24)
    EditText hh24;
    @BindView(R.id.hh25)
    EditText hh25;
    @BindView(R.id.hh26)
    EditText hh26;
    @BindView(R.id.hh27)
    EditText hh27;

    BLRandomContract blData;
    FormsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
        ButterKnife.bind(this);

        setContentUI();
        setListener();

    }

    public void setContentUI() {
//        txtTeamNoWithFam.setText("NNS-S" + String.format("%04d", AppMain.hh03txt) + "-H" + String.format("%03d", Integer.valueOf(AppMain.hh07txt)));

        blData = (BLRandomContract) getIntent().getSerializableExtra("blData");
        txtTeamNoWithFam.setText("NNS-S" + blData.getHh());
        hh08.setText(blData.getHhhead());

        this.setTitle("FAMILY LISTING -- CLUSTER : " + blData.getClusterCode());

    }

    public void setListener() {
        hh29.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == hh29a.getId()) {
                    fldGrpa01.setVisibility(View.VISIBLE);
                } else {
                    fldGrpa01.setVisibility(View.GONE);
                    ClearClass.ClearAllFields(fldGrpa01);
                }
            }
        });
    }

    private void SaveDraft(boolean flag) throws JSONException {

        AppMain.lc = new ListingFormContract();
        AppMain.lc.setTagId(AppMain.getTagName(this));
        AppMain.lc.setAppVer(AppMain.versionName + "." + AppMain.versionCode);
        AppMain.lc.setHhDT(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date().getTime()));
        AppMain.lc.setHh02(AppMain.hh02txt);
        AppMain.lc.setUsername(AppMain.userEmail);
        AppMain.lc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        AppMain.lc.setHh08(hh08.getText().toString());
        setGPS();

//        Data from service
        AppMain.lc.setLuid(blData.getLUID());
        AppMain.lc.setClusterCode(blData.getClusterCode());
        AppMain.lc.setHh03(blData.getStructure());
        AppMain.lc.setHh07(blData.getExtension());
        AppMain.lc.setRandDT(blData.getRandomDT());
        AppMain.lc.setSno(blData.getSno());

        JSONObject sA = new JSONObject();
        sA.put("hh18", hh18.getText().toString());
        sA.put("hh19", hh19.getText().toString());
        sA.put("hh20", hh20.getText().toString());
        sA.put("hh21", hh21.getText().toString());
        sA.put("hh22", hh22.getText().toString());
        sA.put("hh23", hh23.getText().toString());
        sA.put("hh24", hh24.getText().toString());
        sA.put("hh25", hh25.getText().toString());
        sA.put("hh26", hh26.getText().toString());
        sA.put("hh27", hh27.getText().toString());
        sA.put("hh28", hh16.getText().toString());

        if (flag)
            sA.put("hh29", hh29a.isChecked() ? "1" : hh29b.isChecked() ? "2" : hh029c.isChecked() ? "3" : "0");
        else
            sA.put("hh29", "8");

        AppMain.lc.setSA(String.valueOf(sA));

        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03());
    }

    public void setGPS() {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);

//        String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            AppMain.lc.setGPSLat(GPSPref.getString("Latitude", "0"));
            AppMain.lc.setGPSLng(GPSPref.getString("Longitude", "0"));
            AppMain.lc.setGPSAcc(GPSPref.getString("Accuracy", "0"));
            AppMain.lc.setGPSAlt(GPSPref.getString("Altitude", "0"));
            AppMain.lc.setGPSTime(date); // Timestamp is converted to date above

            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }

    private boolean formValidation() {

        if (!ValidatorClass.EmptyRadioButton(this, hh29, hh29a, getString(R.string.hh08a1))) {
            return false;
        }

        if (!hh29a.isChecked())
            return true;

        if (!ValidatorClass.EmptyTextBox(this, hh18, getString(R.string.hh18))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh16, getString(R.string.hh16))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh19, getString(R.string.hh19))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh20, getString(R.string.hh20))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh21, getString(R.string.hh21))) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh27, getString(R.string.hh27))) {
            return false;
        }

        /*if (!ValidatorClass.RangeTextBox(this, hh21, 0, 99, getString(R.string.hh21), "Deaths")) {
            return false;
        }*/

        if (!ValidatorClass.EmptyTextBox(this, hh22, getString(R.string.hh22))) {
            return false;
        }

       /* if (!ValidatorClass.RangeTextBox(this, hh22, 0, 99, getString(R.string.hh22), "Deaths")) {
            return false;
        }*/

        if (!ValidatorClass.EmptyTextBox(this, hh24, getString(R.string.hh24))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, hh24, 0, 9, getString(R.string.hh24), "Deaths")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh25, getString(R.string.hh25))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, hh25, 0, 9, getString(R.string.hh25), "Deaths")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh26, getString(R.string.hh26))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, hh26, 0, 9, getString(R.string.hh26), "Deaths")) {
            return false;
        }

        if (!ValidatorClass.EmptyTextBox(this, hh23, getString(R.string.hh23))) {
            return false;
        }

        if (!ValidatorClass.RangeTextBox(this, hh23, 0, 9, getString(R.string.hh23), "Deaths")) {
            return false;
        }

        int total = Integer.valueOf(hh19.getText().toString()) + Integer.valueOf(hh20.getText().toString()) + Integer.valueOf(hh21.getText().toString()) +
                Integer.valueOf(hh22.getText().toString()) + Integer.valueOf(hh23.getText().toString()) + Integer.valueOf(hh24.getText().toString()) +
                Integer.valueOf(hh25.getText().toString()) + Integer.valueOf(hh26.getText().toString()) + Integer.valueOf(hh27.getText().toString());

//        return ValidatorClass.RangeTextBox(this, hh16, total, 9, getString(R.string.hh16), " Deaths");

        if (Integer.valueOf(hh16.getText().toString()) < total || Integer.valueOf(hh16.getText().toString()) > 9) {
            Toast.makeText(this, "ERROR(invalid): " + getString(R.string.hh16), Toast.LENGTH_SHORT).show();
            hh16.setError("Total death do not match death count below");    // Set Error on last radio button
            hh16.setFocusableInTouchMode(true);
            hh16.requestFocus();
            return false;
        } else {
            hh16.setError(null);
            hh16.clearFocus();
            return true;
        }

    }

    @OnClick(R.id.btnNextHH)
    void onBtnNextHHClick() {
        if (formValidation()) {

            try {
                SaveDraft(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB(true)) {
                finish();

                BLRandomContract getBLData = db.lastBLRandomRecord(blData.getClusterCode(), blData.getHh());
                if (getBLData != null) {
                    startActivity(new Intent(this, ValidatorActivity.class)
                            .putExtra("blData", getBLData));
                } else {
                    Toast.makeText(this, "No more household found!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }

            }

        }

    }

    @OnClick(R.id.btnExit)
    void onBtnExitClick() {
        //TODO implement

        try {
            SaveDraft(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB(false)) {
            finish();

            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private boolean UpdateDB(boolean flag) {
        db = new FormsDBHelper(this);

        long updcount = db.addForm(AppMain.lc, blData.getHh(), flag);

        AppMain.lc.setID(String.valueOf(updcount));

        if (updcount != 0) {

            AppMain.lc.setUID(
                    (AppMain.lc.getDeviceID() + AppMain.lc.getID()));

            db.updateListingUID();

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
