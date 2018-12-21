package edu.aku.ramshasaeed.clusterfinal.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class VerticesContract {

    private String cluster_code;
    private Double poly_lat;
    private Double poly_lng;
    private String poly_seq;
    private String marker_hh;
    private String geoarea;
    private String pcode;


    public VerticesContract() {
    }

    public VerticesContract sync(JSONObject jsonObject) throws JSONException {
        this.cluster_code = jsonObject.getString(singleVertices.COLUMN_CLUSTER_CODE);
        this.poly_lat = jsonObject.getDouble(singleVertices.COLUMN_POLY_LAT);
        this.poly_lng = jsonObject.getDouble(singleVertices.COLUMN_POLY_LANG);
        this.poly_seq = jsonObject.getString(singleVertices.COLUMN_POLY_SEQ);
        this.marker_hh = jsonObject.getString(singleVertices.COLUMN_MARKER_HH);
        this.geoarea = jsonObject.getString(singleVertices.COLUMN_GEO_AREA);
        this.pcode = jsonObject.getString(singleVertices.COLUMN_PSCODE);


        return this;
    }

    public VerticesContract hydrate(Cursor cursor) {
        this.cluster_code = cursor.getString(cursor.getColumnIndex(singleVertices.COLUMN_CLUSTER_CODE));
        this.poly_lat = cursor.getDouble(cursor.getColumnIndex(singleVertices.COLUMN_POLY_LAT));
        this.poly_lng = cursor.getDouble(cursor.getColumnIndex(singleVertices.COLUMN_POLY_LANG));
        this.poly_seq = cursor.getString(cursor.getColumnIndex(singleVertices.COLUMN_POLY_SEQ));
        this.marker_hh =cursor.getString(cursor.getColumnIndex(singleVertices.COLUMN_MARKER_HH));
        this.geoarea = cursor.getString(cursor.getColumnIndex(singleVertices.COLUMN_GEO_AREA));
        this.pcode = cursor.getString(cursor.getColumnIndex(singleVertices.COLUMN_PSCODE));
        return this;
    }

    public String getCluster_code() {
        return cluster_code;
    }

    public void setCluster_code(String cluster_code) {
        this.cluster_code = cluster_code;
    }

    public Double getPoly_lat() {
        return poly_lat;
    }

    public void setPoly_lat(Double poly_lat) {
        this.poly_lat = poly_lat;
    }

    public Double getPoly_lng() {
        return poly_lng;
    }

    public void setPoly_lng(Double poly_lng) {
        this.poly_lng = poly_lng;
    }

    public String getPoly_seq() {
        return poly_seq;
    }

    public void setPoly_seq(String poly_seq) {
        this.poly_seq = poly_seq;
    }

    public String getmarker_hh() {
        return marker_hh;
    }

    public void setmarker_hh(String marker_hh) {
        this.marker_hh = marker_hh;
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

    public static abstract class singleVertices implements BaseColumns {

        public static final String TABLE_NAME = "Vertices";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_CLUSTER_CODE = "cluster_code";
        public static final String COLUMN_POLY_LAT = "poly_lat";
        public static final String COLUMN_POLY_LANG = "poly_lng";
        public static final String COLUMN_POLY_SEQ = "poly_seq";
        public static final String COLUMN_MARKER_HH = "marker_hh";
        public static final String COLUMN_GEO_AREA = "geoarea";
        public static final String COLUMN_PSCODE = "pcode";

        public static final String _URI = "vertices.php";
    }

}