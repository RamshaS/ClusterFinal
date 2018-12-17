package edu.aku.ramshasaeed.clusterfinal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.ramshasaeed.clusterfinal.Core.AppMain;
import edu.aku.ramshasaeed.clusterfinal.R;
import edu.aku.ramshasaeed.clusterfinal.validation.validation.validatorClass;

public class ValidatorActivity extends AppCompatActivity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtTeamNoWithFam)
    TextView txtTeamNoWithFam;
    @BindView(R.id.hh08)
    EditText hh08;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);
        ButterKnife.bind(this);

        this.setTitle("FAMILY LISTING");
//        txtTeamNoWithFam.setText("NNS-S" + String.format("%04d", AppMain.hh03txt) + "-H" + String.format("%03d", Integer.valueOf(AppMain.hh07txt)));

    }

    private void SaveDraft() {

        /*AppMain.lc.setHh07(AppMain.hh07txt);
        AppMain.lc.setHh08a1("1");
        AppMain.lc.setHh08(hh08.getText().toString());
        AppMain.lc.setHh09(hh09.getText().toString());
        AppMain.lc.setHh10(hh10a.isChecked() ? "1" : hh10b.isChecked() ? "2" : "0");
        AppMain.lc.setHh11(hh11.getText().toString().isEmpty() ? "0" : hh11.getText().toString());
        AppMain.lc.setHh12(hh12a.isChecked() ? "1" : hh12b.isChecked() ? "2" : "0");
        AppMain.lc.setHh13(hh13.getText().toString().isEmpty() ? "0" : hh13.getText().toString());
        AppMain.lc.setHh14(hh14a.isChecked() ? "1" : hh14b.isChecked() ? "2" : "0");
        AppMain.lc.setHh15(hh15.getText().toString().isEmpty() ? "0" : hh15.getText().toString());
        AppMain.lc.setHh16(hh16.getText().toString());
        AppMain.lc.setIsNewHH(hh17.isChecked() ? "1" : "2");

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

        AppMain.lc.setCounter(String.valueOf(sA));

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03());*/

    }

    private boolean formValidation() {

        if (!validatorClass.EmptyTextBox(this, hh18, getString(R.string.hh18))) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh19, getString(R.string.hh19))) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh20, getString(R.string.hh20))) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh21, getString(R.string.hh21))) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh27, getString(R.string.hh27))) {
            return false;
        }

        /*if (!validatorClass.RangeTextBox(this, hh21, 0, 99, getString(R.string.hh21), "Deaths")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh22, getString(R.string.hh22))) {
            return false;
        }

       /* if (!validatorClass.RangeTextBox(this, hh22, 0, 99, getString(R.string.hh22), "Deaths")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh24, getString(R.string.hh24))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, hh24, 0, 9, getString(R.string.hh24), "Deaths")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh25, getString(R.string.hh25))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, hh25, 0, 9, getString(R.string.hh25), "Deaths")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh26, getString(R.string.hh26))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, hh26, 0, 9, getString(R.string.hh26), "Deaths")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh23, getString(R.string.hh23))) {
            return false;
        }

        return validatorClass.RangeTextBox(this, hh23, 0, 99, getString(R.string.hh23), "Deaths");
    }


    @OnClick(R.id.btnNextHH)
    void onBtnNextHHClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                AppMain.hh07txt = String.valueOf(Integer.valueOf(AppMain.hh07txt) + 1);
//                AppMain.lc.setHh07(AppMain.hh07txt);

                finish();
                Intent fA = new Intent(this, ValidatorActivity.class);
                startActivity(fA);
            }

        }

    }

    private boolean UpdateDB() {
        /*FormsDBHelper db = new FormsDBHelper(this);

        long updcount = db.addForm(AppMain.lc);

        AppMain.lc.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            AppMain.lc.setUID(
                    (AppMain.lc.getDeviceID() + AppMain.lc.getID()));

            db.updateListingUID();

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }*/
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }

}
