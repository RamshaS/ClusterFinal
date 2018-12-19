package edu.aku.ramshasaeed.vasadata.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class DistrictContract {

    private String projectName;
    private int _ID;
    private String geoarea;
    private String pcode;

    public DistrictContract() {
    }

    public DistrictContract sync(JSONObject jsonObject) throws JSONException {
        this.projectName= jsonObject.getString(DistrictTable.COLUMN_PROJECTNAME);
        this.geoarea= jsonObject.getString(DistrictTable.COLUMN_GEOAREA);
        this.pcode= jsonObject.getString(DistrictTable.COLUMN_PCODE);

        return this;
    }

    public DistrictContract hydrate(Cursor cursor) {
        this.projectName = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_PROJECTNAME));
        this._ID = cursor.getInt(cursor.getColumnIndex(DistrictTable.COLUMN__ID));
        this.geoarea = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_GEOAREA));
        this.pcode = cursor.getString(cursor.getColumnIndex(DistrictTable.COLUMN_PCODE));


        return this;
    }

    public String getprojectName() {
        return projectName;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getgeoarea() {
        return geoarea;
    }

    public void setgeoarea(String geoarea) {
        this.geoarea = geoarea;
    }

    public String getpcode() {
        return pcode;
    }

    public void setpcode(String pcode) {
        this.pcode = pcode;
    }

    public void setprojectName(String projectName) {
        this.projectName = projectName;
    }

    public static abstract class DistrictTable implements BaseColumns {

        public static final String TABLE_NAME = "District";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String COLUMN_PROJECTNAME = "projectname";
        public static final String COLUMN__ID = "_id";
        public static final String COLUMN_GEOAREA = "geoarea";
        public static final String COLUMN_PCODE = "pcode";



        public static final String _URI = "getDistricts.php";
    }
}
