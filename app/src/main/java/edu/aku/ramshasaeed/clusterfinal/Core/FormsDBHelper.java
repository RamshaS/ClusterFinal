package edu.aku.ramshasaeed.clusterfinal.Core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.aku.ramshasaeed.clusterfinal.Contracts.BLRandomContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.BLRandomContract.singleRandomHH;
import edu.aku.ramshasaeed.clusterfinal.Contracts.ListingFormContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.ListingFormContract.ListingFormEntry;
import edu.aku.ramshasaeed.clusterfinal.Contracts.UsersContract.UsersTable;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract.singleVertices;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // The name of database.
    public static final String DATABASE_NAME = "CF-NNS2018.db";
    public static final String DB_NAME = DATABASE_NAME.replace(".db", "-copy.db");
    public static final String FOLDER_NAME = "DMU-CF-NNS2018";
    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 1;
    public static String TAG = "FormsDBHelper";
    public static final String PROJECT_NAME = "CF-NNS2018";

    // Create a table to hold Listings.
    final String SQL_CREATE_VERTICES_TABLE = "CREATE TABLE " + singleVertices.TABLE_NAME + " (" +
            singleVertices._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleVertices.COLUMN_CLUSTER_CODE + " TEXT," +
            singleVertices.COLUMN_POLY_LAT + " TEXT, " +
            singleVertices.COLUMN_POLY_LANG + " TEXT, " +
            singleVertices.COLUMN_POLY_SEQ + " TEXT ," +
            singleVertices.COLUMN_MARKER_HH + " TEXT ," +
//            singleVertices.COLUMN_GEO_AREA + " TEXT, " +
            singleVertices.COLUMN_PSCODE + " TEXT, " +
            singleVertices.COLUMN_MORTALITY + " TEXT " +
            ");";

    final String SQL_CREATE_USERS = "CREATE TABLE " + UsersTable.TABLE_NAME + "("
            + UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.ROW_USERNAME + " TEXT,"
            + UsersTable.ROW_PASSWORD + " TEXT );";

    final String SQL_CREATE_BL_RANDOM = "CREATE TABLE " + singleRandomHH.TABLE_NAME + "("
            + singleRandomHH.COLUMN_COL_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleRandomHH.COLUMN_NAME_ID + " TEXT,"
            + singleRandomHH.COLUMN_CLUSTER_CODE + " TEXT,"
            + singleRandomHH.COLUMN_LUID + " TEXT,"
            + singleRandomHH.COLUMN_HH + " TEXT,"
            + singleRandomHH.COLUMN_STRUCTURE_NO + " TEXT,"
            + singleRandomHH.COLUMN_FAMILY_EXT_CODE + " TEXT,"
            + singleRandomHH.COLUMN_HH_HEAD + " TEXT,"
            + singleRandomHH.COLUMN_CONTACT + " TEXT,"
            + singleRandomHH.COLUMN_HH_SELECTED_STRUCT + " TEXT,"
            + singleRandomHH.COLUMN_RANDOMDT + " TEXT,"
            + singleRandomHH.COLUMN_SNO_HH + " TEXT );";

    // Create a table to hold Listings.
    final String SQL_CREATE_LISTING_TABLE = "CREATE TABLE " + ListingFormEntry.TABLE_NAME + " (" +
            ListingFormEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ListingFormEntry.COLUMN_NAME_UID + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_HHDATETIME + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_LUID + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_CLUSTERCODE + " TEXT, " +
            ListingFormEntry.COLUMN_RANDDT + " TEXT, " +
            ListingFormEntry.COLUMN_SNO + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_HH02 + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_HH03 + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_HH07 + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_HH08 + " TEXT, " +
            ListingFormEntry.COLUMN_SA + " TEXT, " +
            ListingFormEntry.COLUMN_USERNAME + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_DEVICEID + " TEXT, " +
            ListingFormEntry.COLUMN_TAGID + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_GPSLat + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_GPSLng + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_GPSTime + " TEXT, " +
            ListingFormEntry.COLUMN_APPVER + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_GPSAccuracy + " TEXT, " +
            ListingFormEntry.COLUMN_NAME_GPSAltitude + " TEXT, " +
            ListingFormEntry.COLUMN_RANDOMIZED + " TEXT, " +
            ListingFormEntry.COLUMN_SYNCED + " TEXT, " +
            ListingFormEntry.COLUMN_SYNCED_DATE + " TEXT " +
            " );";

    public FormsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_VERTICES_TABLE);
        db.execSQL(SQL_CREATE_BL_RANDOM);
        db.execSQL(SQL_CREATE_LISTING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*// Simply discard all old data and start over when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + ListingFormEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleTaluka.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EnumBlockTable.TABLE_NAME);*/

    }

    public void syncUsers(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersTable.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                String userName = jsonObjectUser.getString("username");
                String password = jsonObjectUser.getString("password");

                ContentValues values = new ContentValues();
                values.put(UsersTable.ROW_USERNAME, userName);
                values.put(UsersTable.ROW_PASSWORD, password);
                db.insert(UsersTable.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public void syncBLRandom(JSONArray BLlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleRandomHH.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = BLlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                BLRandomContract Vc = new BLRandomContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(singleRandomHH.COLUMN_NAME_ID, Vc.get_ID());
                values.put(singleRandomHH.COLUMN_LUID, Vc.getLUID());
                values.put(singleRandomHH.COLUMN_STRUCTURE_NO, Vc.getStructure());
                values.put(singleRandomHH.COLUMN_FAMILY_EXT_CODE, Vc.getExtension());
                values.put(singleRandomHH.COLUMN_HH, Vc.getHh());
                values.put(singleRandomHH.COLUMN_CLUSTER_CODE, Vc.getClusterCode());
                values.put(singleRandomHH.COLUMN_RANDOMDT, Vc.getRandomDT());
                values.put(singleRandomHH.COLUMN_HH_HEAD, Vc.getHhhead());
                values.put(singleRandomHH.COLUMN_CONTACT, Vc.getContact());
                values.put(singleRandomHH.COLUMN_HH_SELECTED_STRUCT, Vc.getSelStructure());
                values.put(singleRandomHH.COLUMN_SNO_HH, Vc.getSno());

                db.insert(singleRandomHH.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public Long addForm(ListingFormContract lc, String hh, boolean flag) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ListingFormEntry.COLUMN_NAME_UID, lc.getUID());
        values.put(ListingFormEntry.COLUMN_NAME_HHDATETIME, lc.getHhDT());

        values.put(ListingFormEntry.COLUMN_NAME_LUID, lc.getLuid());
        values.put(ListingFormEntry.COLUMN_NAME_CLUSTERCODE, lc.getClusterCode());
        values.put(ListingFormEntry.COLUMN_RANDDT, lc.getRandDT());

        values.put(ListingFormEntry.COLUMN_SNO, lc.getSno());
        values.put(ListingFormEntry.COLUMN_NAME_HH02, lc.getHh02());
        values.put(ListingFormEntry.COLUMN_NAME_HH03, lc.getHh03());
        values.put(ListingFormEntry.COLUMN_NAME_HH07, lc.getHh07());
        values.put(ListingFormEntry.COLUMN_NAME_HH08, lc.getHh08());

        if (flag) {
            AppMain.updatePSU(lc.getHh02(), hh);
            Log.d(TAG, "PSUExist (Test): " + AppMain.sharedPref.getString(lc.getHh02(), "0"));
        }

        values.put(ListingFormEntry.COLUMN_SA, lc.getsA());
        values.put(ListingFormEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingFormEntry.COLUMN_USERNAME, lc.getUsername());
        values.put(ListingFormEntry.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(ListingFormEntry.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(ListingFormEntry.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(ListingFormEntry.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(ListingFormEntry.COLUMN_NAME_GPSAltitude, lc.getGPSAlt());
        values.put(ListingFormEntry.COLUMN_APPVER, lc.getAppVer());
        values.put(ListingFormEntry.COLUMN_TAGID, lc.getTagId());

        long newRowId;
        newRowId = db.insert(
                ListingFormEntry.TABLE_NAME,
                ListingFormEntry.COLUMN_NAME_NULLABLE,
                values);

        return newRowId;
    }

    public Collection<ListingFormContract> getAllFormListings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ListingFormEntry._ID,
                ListingFormEntry.COLUMN_NAME_UID,
                ListingFormEntry.COLUMN_NAME_HHDATETIME,
                ListingFormEntry.COLUMN_NAME_LUID,
                ListingFormEntry.COLUMN_NAME_CLUSTERCODE,
                ListingFormEntry.COLUMN_RANDDT,
                ListingFormEntry.COLUMN_SNO,
                ListingFormEntry.COLUMN_NAME_HH02,
                ListingFormEntry.COLUMN_NAME_HH03,
                ListingFormEntry.COLUMN_NAME_HH07,
                ListingFormEntry.COLUMN_NAME_HH08,
                ListingFormEntry.COLUMN_SA,
                ListingFormEntry.COLUMN_USERNAME,
                ListingFormEntry.COLUMN_NAME_DEVICEID,
                ListingFormEntry.COLUMN_TAGID,
                ListingFormEntry.COLUMN_NAME_GPSLat,
                ListingFormEntry.COLUMN_NAME_GPSLng,
                ListingFormEntry.COLUMN_NAME_GPSTime,
                ListingFormEntry.COLUMN_NAME_GPSAccuracy,
                ListingFormEntry.COLUMN_NAME_GPSAltitude,
                ListingFormEntry.COLUMN_APPVER,
                ListingFormEntry.COLUMN_RANDOMIZED
        };

        String whereClause = ListingFormEntry.COLUMN_SYNCED + " is null OR " + ListingFormEntry.COLUMN_SYNCED + " = ''";
        String[] whereArgs = null;


        String groupBy = null;
        String having = null;

        String orderBy = ListingFormEntry.COLUMN_NAME_CLUSTERCODE + " ASC";

        Collection<ListingFormContract> allLC = new ArrayList<ListingFormContract>();
        try {
            c = db.query(
                    ListingFormEntry.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ListingFormContract listing = new ListingFormContract();
                allLC.add(listing.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allLC;
    }

    public void updateListingUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingFormEntry.COLUMN_NAME_UID, AppMain.lc.getUID());

// Which row to update, based on the title
        String where = ListingFormEntry._ID + " = ?";
        String[] whereArgs = {AppMain.lc.getID()};

        int count = db.update(
                ListingFormEntry.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedListing(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingFormEntry.COLUMN_SYNCED, true);
        values.put(ListingFormEntry.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ListingFormEntry._ID + " LIKE ?";
        String[] whereArgs = {id};

        int count = db.update(
                ListingFormEntry.TABLE_NAME,
                values,
                where,
                whereArgs);
        db.close();
    }

    public Collection<BLRandomContract> getAllBLRandom(String hh) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleRandomHH.COLUMN_COL_ID,
                singleRandomHH.COLUMN_NAME_ID,
                singleRandomHH.COLUMN_LUID,
                singleRandomHH.COLUMN_STRUCTURE_NO,
                singleRandomHH.COLUMN_FAMILY_EXT_CODE,
                singleRandomHH.COLUMN_HH,
                singleRandomHH.COLUMN_CLUSTER_CODE,
                singleRandomHH.COLUMN_RANDOMDT,
                singleRandomHH.COLUMN_HH_SELECTED_STRUCT,
                singleRandomHH.COLUMN_CONTACT,
                singleRandomHH.COLUMN_HH_HEAD,
                singleRandomHH.COLUMN_SNO_HH
        };

        String whereClause = singleRandomHH.COLUMN_HH + "=?";
        String[] whereArgs = new String[]{hh};
        String groupBy = null;
        String having = null;

        String orderBy = null;

        Collection<BLRandomContract> allBL = new ArrayList<>();
        try {
            c = db.query(
                    singleRandomHH.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                BLRandomContract dc = new BLRandomContract();
                allBL.add(dc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allBL;
    }

    public BLRandomContract lastBLRandomRecord(String cluster, String... hh) {
        SQLiteDatabase db = this.getWritableDatabase();

        BLRandomContract blrandom = null;

        String qry;
        String[] where;
        if (hh.length > 0) {
            qry = "SELECT * FROM " + singleRandomHH.TABLE_NAME + " WHERE " + singleRandomHH.COLUMN_COL_ID
                    + " IN (SELECT " + singleRandomHH.COLUMN_COL_ID + "+1 FROM " + singleRandomHH.TABLE_NAME + " WHERE " +
                    singleRandomHH.COLUMN_CLUSTER_CODE + " =? AND " + singleRandomHH.COLUMN_HH + " =?) AND " +
                    singleRandomHH.COLUMN_CLUSTER_CODE + " =?";

            where = new String[]{cluster, hh[0], cluster};

        } else {
            qry = "SELECT * FROM " + singleRandomHH.TABLE_NAME + " WHERE " + singleRandomHH.COLUMN_CLUSTER_CODE + " =? ORDER BY " + singleRandomHH.COLUMN_COL_ID + " ASC";

            where = new String[]{cluster};
        }

        Cursor c = db.rawQuery(qry, where);
        if (c != null && c.moveToFirst()) {
            blrandom = new BLRandomContract().Hydrate(c);
            c.close();
        }

        return blrandom;
    }

    public boolean Login(String username, String password) throws SQLException {

        SQLiteDatabase db = this.getReadableDatabase();

//        New value for one column
        String[] columns = {
                UsersTable._ID
        };

//        Which row to update, based on the ID
        String selection = UsersTable.ROW_USERNAME + " = ?" + " AND " + UsersTable.ROW_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(UsersTable.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }


    }

    public Collection<VerticesContract> getVerticesByCluster(String cluster_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleVertices._ID,
                singleVertices.COLUMN_CLUSTER_CODE,
                singleVertices.COLUMN_POLY_LAT,
                singleVertices.COLUMN_POLY_LANG,
                singleVertices.COLUMN_POLY_SEQ,
                singleVertices.COLUMN_MARKER_HH,
//                singleVertices.COLUMN_GEO_AREA,
                singleVertices.COLUMN_PSCODE,
                singleVertices.COLUMN_MORTALITY
        };

        String whereClause = singleVertices.COLUMN_CLUSTER_CODE + " = ? AND (" + singleVertices.COLUMN_POLY_SEQ + " != '' OR " + singleVertices.COLUMN_POLY_SEQ + " != null)";
        String[] whereArgs = {cluster_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleVertices.COLUMN_POLY_SEQ + " ASC";

        Collection<VerticesContract> allVC = new ArrayList<>();
        try {
            c = db.query(
                    singleVertices.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VerticesContract vc = new VerticesContract();
                allVC.add(vc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public Collection<VerticesContract> getMarkersByCluster(String cluster_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleVertices._ID,
                singleVertices.COLUMN_CLUSTER_CODE,
                singleVertices.COLUMN_POLY_LAT,
                singleVertices.COLUMN_POLY_LANG,
                singleVertices.COLUMN_POLY_SEQ,
                singleVertices.COLUMN_MARKER_HH,
//                singleVertices.COLUMN_GEO_AREA,
                singleVertices.COLUMN_PSCODE,
                singleVertices.COLUMN_MORTALITY

        };

        String whereClause = singleVertices.COLUMN_CLUSTER_CODE + " = ? AND (" + singleVertices.COLUMN_POLY_SEQ + " == '' OR " + singleVertices.COLUMN_POLY_SEQ + " == null)" +
                " AND (" + singleVertices.COLUMN_MORTALITY + " == '' OR " + singleVertices.COLUMN_MORTALITY + " == null) ";
        String[] whereArgs = {cluster_code};
        String groupBy = null;
        String having = null;

        String orderBy = singleVertices.COLUMN_POLY_LAT + " ASC";

        Collection<VerticesContract> allVC = new ArrayList<>();
        try {
            c = db.query(
                    singleVertices.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VerticesContract vc = new VerticesContract();
                allVC.add(vc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public void syncVertices(JSONArray vcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleVertices.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = vcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectVR = jsonArray.getJSONObject(i);

                VerticesContract vc = new VerticesContract();
                vc.sync(jsonObjectVR);

                ContentValues values = new ContentValues();

                values.put(singleVertices.COLUMN_CLUSTER_CODE, vc.getCluster_code());
                values.put(singleVertices.COLUMN_POLY_LAT, vc.getPoly_lat());
                values.put(singleVertices.COLUMN_POLY_LANG, vc.getPoly_lng());
                values.put(singleVertices.COLUMN_POLY_SEQ, vc.getPoly_seq());
                values.put(singleVertices.COLUMN_MARKER_HH, vc.getmarker_hh());
//                values.put(singleVertices.COLUMN_GEO_AREA, vc.getgeoarea());
                values.put(singleVertices.COLUMN_PSCODE, vc.getpcode());
                values.put(singleVertices.COLUMN_MORTALITY, vc.getMortality());

                db.insert(singleVertices.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

}