package edu.aku.ramshasaeed.clusterfinal.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class MarkerContract {

    private String projectName;
    private int _ID;
    private String m_lat;
    private String m_lng;
    private String cluster_code;
    private String hhno;

    public MarkerContract() {
    }

    public MarkerContract sync(JSONObject jsonObject) throws JSONException {
        this.m_lat= jsonObject.getString(MarkerTable.COLUMN_M_LAT);
        this.m_lng= jsonObject.getString(MarkerTable.COLUMN_M_LNG);
        this.cluster_code= jsonObject.getString(MarkerTable.COLUMN_CLUSTER_CODE);
        this.hhno= jsonObject.getString(MarkerTable.COLUMN_HHNO);
        return this;
    }

    public MarkerContract hydrate(Cursor cursor) {
        this.m_lat = cursor.getString(cursor.getColumnIndex(MarkerTable.COLUMN_M_LAT));
        this.m_lng = cursor.getString(cursor.getColumnIndex(MarkerTable.COLUMN_M_LNG));
        this.cluster_code = cursor.getString(cursor.getColumnIndex(MarkerTable.COLUMN_CLUSTER_CODE));
        this.hhno = cursor.getString(cursor.getColumnIndex(MarkerTable.COLUMN_HHNO));

        return this;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getm_lat() {
        return m_lat;
    }

    public void setm_lat(String m_lat) {
        this.m_lat = m_lat;
    }

    public String getm_lng() {
        return m_lng;
    }

    public void setm_lng(String m_lng) {
        this.m_lng = m_lng;
    }

    public String getcluster_code() {
        return cluster_code;
    }

    public void setcluster_code(String cluster_code) {
        this.cluster_code = cluster_code;
    }

    public String gethhno() {
        return hhno;
    }

    public void sethhno(String hhno) {
        this.hhno = hhno;
    }

    public String getprojectName() {
        return projectName;
    }

    public void setprojectName(String projectName) {
        this.projectName = projectName;
    }

    public static abstract class MarkerTable implements BaseColumns {

        public static final String TABLE_NAME = "Marker";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String COLUMN_PROJECTNAME = "projectname";
        public static final String COLUMN__ID = "_id";
        public static final String COLUMN_M_LAT = "gpslat";
        public static final String COLUMN_M_LNG = "gpslng";
        public static final String COLUMN_CLUSTER_CODE = "cluster_no";
        public static final String COLUMN_HHNO = "hh_no";


        public static final String _URI = "getMarkers.php";
    }
}
