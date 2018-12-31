package edu.aku.ramshasaeed.clusterfinal.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class ListingFormContract {

    public String ID;
    public String UID;
    public String hhDT;
    public String luid;
    public String clusterCode;
    public String randDT;
    public String sno;
    public String hh02;
    public String hh03;
    public String hh07;
    public String hh08;
    public String sA;
    public String DeviceID;
    public String GPSLat;
    public String GPSLng;
    public String GPSTime;
    public String GPSAcc;
    public String GPSAlt;
    public String AppVer;
    public String tagId;

    private String username; // User Name

    public ListingFormContract() {
    }

    public ListingFormContract(String ID) {
        this.ID = ID;
    }

    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHhDT() {
        return hhDT;
    }

    public void setHhDT(String hhDT) {
        this.hhDT = hhDT;
    }

    public String getLuid() {
        return luid;
    }

    public void setLuid(String luid) {
        this.luid = luid;
    }

    public String getRandDT() {
        return randDT;
    }

    public void setRandDT(String randDT) {
        this.randDT = randDT;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getHh02() {
        return hh02;
    }

    public void setHh02(String hh02) {
        this.hh02 = hh02;
    }

    public String getHh03() {
        return hh03;
    }

    public void setHh03(String hh03) {
        this.hh03 = hh03;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getGPSLat() {
        return GPSLat;
    }

    public void setGPSLat(String GPSLat) {
        this.GPSLat = GPSLat;
    }

    public String getGPSLng() {
        return GPSLng;
    }

    public void setGPSLng(String GPSLng) {
        this.GPSLng = GPSLng;
    }

    public String getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(String GPSTime) {
        this.GPSTime = GPSTime;
    }

    public String getGPSAcc() {
        return GPSAcc;
    }

    public void setGPSAcc(String GPSAcc) {
        this.GPSAcc = GPSAcc;
    }

    public String getAppVer() {
        return AppVer;
    }

    public void setAppVer(String appVer) {
        AppVer = appVer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getGPSAlt() {
        return GPSAlt;
    }

    public void setGPSAlt(String GPSAlt) {
        this.GPSAlt = GPSAlt;
    }

    public String getHh07() {
        return hh07;
    }

    public void setHh07(String hh07) {
        this.hh07 = hh07;
    }

    public String getHh08() {
        return hh08;
    }

    public void setHh08(String hh08) {
        this.hh08 = hh08;
    }

    public String getsA() {
        return sA;
    }

    public void setSA(String counter) {
        this.sA = counter;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("projectname", "NNS-LINELISTING 2018");
        json.put(ListingFormEntry._ID, this.ID);
        json.put(ListingFormEntry.COLUMN_NAME_UID, this.UID);
        json.put(ListingFormEntry.COLUMN_NAME_HHDATETIME, this.hhDT);
        json.put(ListingFormEntry.COLUMN_NAME_LUID, this.luid);
        json.put(ListingFormEntry.COLUMN_NAME_CLUSTERCODE, this.clusterCode);
        json.put(ListingFormEntry.COLUMN_RANDDT, this.randDT);
        json.put(ListingFormEntry.COLUMN_SNO, this.sno);
        json.put(ListingFormEntry.COLUMN_NAME_HH02, this.hh02);
        json.put(ListingFormEntry.COLUMN_NAME_HH03, this.hh03);
        json.put(ListingFormEntry.COLUMN_NAME_HH07, this.hh07);
        json.put(ListingFormEntry.COLUMN_NAME_HH08, this.hh08);
        json.put(ListingFormEntry.COLUMN_NAME_DEVICEID, this.DeviceID);
        json.put(ListingFormEntry.COLUMN_NAME_GPSLat, this.GPSLat);
        json.put(ListingFormEntry.COLUMN_NAME_GPSLng, this.GPSLng);
        json.put(ListingFormEntry.COLUMN_NAME_GPSTime, this.GPSTime);
        json.put(ListingFormEntry.COLUMN_NAME_GPSAccuracy, this.GPSAcc);
        json.put(ListingFormEntry.COLUMN_NAME_GPSAltitude, this.GPSAlt);
        json.put(ListingFormEntry.COLUMN_APPVER, this.AppVer);
        json.put(ListingFormEntry.COLUMN_USERNAME, this.username);
        json.put(ListingFormEntry.COLUMN_TAGID, this.tagId);
        if (!this.sA.equals("") && this.sA != null) {
            json.put(ListingFormEntry.COLUMN_SA, this.sA.equals("") ? JSONObject.NULL : new JSONObject(this.sA));
        }

        return json;
    }

    public ListingFormContract hydrate(Cursor c) {
        ListingFormContract lc = new ListingFormContract(c.getString(c.getColumnIndex(ListingFormEntry._ID)));
        lc.setHhDT(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_HHDATETIME))));
        lc.setLuid(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_LUID))));
        lc.setUID(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_UID))));
        lc.setClusterCode(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_CLUSTERCODE))));
        lc.setRandDT(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_RANDDT))));
        lc.setSno(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_SNO))));
        lc.setHh02(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_HH02))));
        lc.setHh03(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_HH03))));
        lc.setHh07(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_HH07))));
        lc.setHh08(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_HH08))));
        lc.setDeviceID(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_DEVICEID))));
        lc.setGPSLat(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_GPSLat))));
        lc.setGPSLng(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_GPSLng))));
        lc.setGPSTime(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_GPSTime))));
        lc.setGPSAcc(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_GPSAccuracy))));
        lc.setGPSAlt(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_NAME_GPSAltitude))));
        lc.setAppVer(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_APPVER))));
        lc.setTagId(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_TAGID))));
        lc.setUsername(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_USERNAME))));
        lc.setSA(String.valueOf(c.getString(c.getColumnIndex(ListingFormEntry.COLUMN_SA))));

        return lc;
    }

    public ListingFormContract Sync(JSONObject jsonObject) throws JSONException {

        this.ID = jsonObject.getString(ListingFormEntry._ID);
        this.UID = jsonObject.getString(ListingFormEntry.COLUMN_NAME_UID);
        this.hhDT = jsonObject.getString(ListingFormEntry.COLUMN_NAME_HHDATETIME);
        this.luid = jsonObject.getString(ListingFormEntry.COLUMN_NAME_LUID);
        this.clusterCode = jsonObject.getString(ListingFormEntry.COLUMN_NAME_CLUSTERCODE);
        this.randDT = jsonObject.getString(ListingFormEntry.COLUMN_RANDDT);
        this.sno = jsonObject.getString(ListingFormEntry.COLUMN_SNO);
        this.hh02 = jsonObject.getString(ListingFormEntry.COLUMN_NAME_HH02);
        this.hh03 = jsonObject.getString(ListingFormEntry.COLUMN_NAME_HH03);
        this.hh07 = jsonObject.getString(ListingFormEntry.COLUMN_NAME_HH07);
        this.hh08 = jsonObject.getString(ListingFormEntry.COLUMN_NAME_HH08);
        this.DeviceID = jsonObject.getString(ListingFormEntry.COLUMN_NAME_DEVICEID);
        this.GPSLat = jsonObject.getString(ListingFormEntry.COLUMN_NAME_GPSLat);
        this.GPSLng = jsonObject.getString(ListingFormEntry.COLUMN_NAME_GPSLng);
        this.GPSTime = jsonObject.getString(ListingFormEntry.COLUMN_NAME_GPSTime);
        this.GPSAcc = jsonObject.getString(ListingFormEntry.COLUMN_NAME_GPSAccuracy);
        this.GPSAlt = jsonObject.getString(ListingFormEntry.COLUMN_NAME_GPSAltitude);
        this.AppVer = jsonObject.getString(ListingFormEntry.COLUMN_APPVER);
        this.tagId = jsonObject.getString(ListingFormEntry.COLUMN_TAGID);
        this.username = jsonObject.getString(ListingFormEntry.COLUMN_USERNAME);

        this.sA = jsonObject.getString(ListingFormEntry.COLUMN_SA);

        return this;
    }

    public static abstract class ListingFormEntry implements BaseColumns {

        public static final String TABLE_NAME = "listings";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_HHDATETIME = "hhdt";
        public static final String COLUMN_NAME_LUID = "luid";
        public static final String COLUMN_NAME_CLUSTERCODE = "clustercode";
        public static final String COLUMN_RANDDT = "randDT";
        public static final String COLUMN_SNO = "sno";
        public static final String COLUMN_NAME_HH02 = "hh02";
        public static final String COLUMN_NAME_HH03 = "hh03";
        public static final String COLUMN_NAME_HH07 = "hh07";
        public static final String COLUMN_NAME_HH08 = "hh08";
        public static final String COLUMN_SA = "sA";
        public static final String COLUMN_NAME_DEVICEID = "deviceid";
        public static final String COLUMN_NAME_GPSLat = "gpslat";
        public static final String COLUMN_NAME_GPSLng = "gpslng";
        public static final String COLUMN_NAME_GPSTime = "gpstime";
        public static final String COLUMN_NAME_GPSAccuracy = "gpsacc";
        public static final String COLUMN_NAME_GPSAltitude = "gpsalt";
        public static final String COLUMN_APPVER = "appver";
        public static final String COLUMN_TAGID = "tagId";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_RANDOMIZED = "randomized";

        public static final String COLUMN_USERNAME = "username";
        public static final String _URL = "listings_form.php";
    }
}