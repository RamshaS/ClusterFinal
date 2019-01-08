package edu.aku.ramshasaeed.clusterfinal.Contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class DistrictContract {

    private static final String TAG = "Districts_CONTRACT";
    String district_name;
    String district_code;
    String prov_code;

    public DistrictContract() {
        // Default Constructor
    }

    public DistrictContract Sync(JSONObject jsonObject) throws JSONException {
        this.district_name = jsonObject.getString(singleDistricts.COLUMN_DISTRICT_NAME);
        this.district_code = jsonObject.getString(singleDistricts.COLUMN_DISTRICT_CODE);
        this.prov_code = jsonObject.getString(singleDistricts.COLUMN_PROV_CODE);

        return this;

    }

    public DistrictContract HydrateDistricts(Cursor cursor) {
        this.district_code = cursor.getString(cursor.getColumnIndex(singleDistricts.COLUMN_DISTRICT_CODE));
        this.district_name = cursor.getString(cursor.getColumnIndex(singleDistricts.COLUMN_DISTRICT_NAME));
        this.prov_code = cursor.getString(cursor.getColumnIndex(singleDistricts.COLUMN_PROV_CODE));
        return this;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public String getProv_code() {
        return prov_code;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(singleDistricts.COLUMN_DISTRICT_NAME, this.district_name == null ? JSONObject.NULL : this.district_name);
        json.put(singleDistricts.COLUMN_DISTRICT_CODE, this.district_code == null ? JSONObject.NULL : this.district_code);
        json.put(singleDistricts.COLUMN_PROV_CODE, this.prov_code == null ? JSONObject.NULL : this.prov_code);

        return json;
    }


    public static abstract class singleDistricts implements BaseColumns {

        public static final String TABLE_NAME = "Districts";
        public static final String COLUMN_DISTRICT_NAME = "district_name";
        public static final String COLUMN_DISTRICT_CODE = "distict_code";
        public static final String COLUMN_PROV_CODE = "prov_code";

        public static final String _URI = "districts.php";
    }
}