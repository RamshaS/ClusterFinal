package edu.aku.ramshasaeed.clusterfinal.Contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class BLRandomContract implements Serializable {

    private static final String TAG = "BLRandom_CONTRACT";

    private String col_ID;
    private String _ID;
    private String LUID;
    private String clusterCode; // hh02
    private String structure;   // Structure
    private String extension;   // Extension
    private String hh;
    private String hhhead;
    private String randomDT;
    private String contact;
    private String selStructure;
    private String sno;

    public BLRandomContract() {
    }

    public BLRandomContract Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(singleRandomHH.COLUMN_NAME_ID);
        this.LUID = jsonObject.getString(singleRandomHH.COLUMN_LUID);
        this.clusterCode = jsonObject.getString(singleRandomHH.COLUMN_CLUSTER_CODE);
        this.structure = jsonObject.getString(singleRandomHH.COLUMN_STRUCTURE_NO);

        this.structure = String.format("%04d", Integer.valueOf(this.structure));

        this.extension = jsonObject.getString(singleRandomHH.COLUMN_FAMILY_EXT_CODE);
        this.hh = jsonObject.getString(singleRandomHH.COLUMN_STRUCTURE_NO)
                + "-" + jsonObject.getString(singleRandomHH.COLUMN_FAMILY_EXT_CODE);
        this.randomDT = jsonObject.getString(singleRandomHH.COLUMN_RANDOMDT);
        this.hhhead = jsonObject.getString(singleRandomHH.COLUMN_HH_HEAD);
        this.contact = jsonObject.getString(singleRandomHH.COLUMN_CONTACT);
        this.selStructure = jsonObject.getString(singleRandomHH.COLUMN_HH_SELECTED_STRUCT);
        this.sno = jsonObject.getString(singleRandomHH.COLUMN_SNO_HH);

        return this;
    }

    public BLRandomContract Hydrate(Cursor cursor) {
        this.col_ID = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_COL_ID));
        this._ID = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_NAME_ID));
        this.LUID = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_LUID));
        this.clusterCode = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_CLUSTER_CODE));
        this.structure = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_STRUCTURE_NO));
        this.extension = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_FAMILY_EXT_CODE));
        this.hh = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_HH));
        this.randomDT = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_RANDOMDT));
        this.hhhead = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_HH_HEAD));
        this.contact = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_CONTACT));
        this.selStructure = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_HH_SELECTED_STRUCT));
        this.sno = cursor.getString(cursor.getColumnIndex(singleRandomHH.COLUMN_SNO_HH));

        return this;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getLUID() {
        return LUID;
    }

    public void setLUID(String LUID) {
        this.LUID = LUID;
    }

    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getHh() {
        return structure + "-" + "" + extension;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getRandomDT() {
        return randomDT;
    }

    public void setRandomDT(String randomDT) {
        this.randomDT = randomDT;
    }

    public String getHhhead() {
        return hhhead;
    }

    public void setHhhead(String hhhead) {
        this.hhhead = hhhead;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSelStructure() {
        return selStructure;
    }

    public void setSelStructure(String selStructure) {
        this.selStructure = selStructure;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCol_ID() {
        return col_ID;
    }

    public void setCol_ID(String col_ID) {
        this.col_ID = col_ID;
    }

    public static abstract class singleRandomHH implements BaseColumns {

        public static final String TABLE_NAME = "BLRandom";
        public static final String COLUMN_COL_ID = "a_id";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_RANDOMDT = "randDT";
        public static final String COLUMN_LUID = "UID";
        public static final String COLUMN_CLUSTER_CODE = "hh02";
        public static final String COLUMN_STRUCTURE_NO = "hh03";
        public static final String COLUMN_FAMILY_EXT_CODE = "hh07";
        public static final String COLUMN_HH = "hh";
        public static final String COLUMN_HH_HEAD = "hh08";
        public static final String COLUMN_CONTACT = "hh09";
        public static final String COLUMN_HH_SELECTED_STRUCT = "hhss";
        public static final String COLUMN_SNO_HH = "sno";

        public static String _URI = "bl_listings.php";
    }

}