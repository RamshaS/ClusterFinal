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

import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract;
import edu.aku.ramshasaeed.clusterfinal.Contracts.VerticesContract.singleVertices;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // The name of database.
    public static final String DATABASE_NAME = "Clusterfinal.db";
    public static final String DB_NAME = DATABASE_NAME.replace(".db", "-copy.db");
    public static final String FOLDER_NAME = "DMU-Clusterfinal";
    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 1;
    public static String TAG = "FormsDBHelper";
    public static String DB_FORM_ID;

    // Create a table to hold Listings.

    final String SQL_CREATE_VERTICES_TABLE = "CREATE TABLE " + singleVertices.TABLE_NAME + " (" +
            singleVertices._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleVertices.COLUMN_CLUSTER_CODE + " TEXT," +
            singleVertices.COLUMN_POLY_LAT + " TEXT, " +
            singleVertices.COLUMN_POLY_LANG + " TEXT, " +
            singleVertices.COLUMN_POLY_SEQ + " TEXT ," +
            singleVertices.COLUMN_MARKER_HH + " TEXT ," +
            singleVertices.COLUMN_GEO_AREA + " TEXT, " +
            singleVertices.COLUMN_PSCODE + " TEXT " +
            ");";



    public FormsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_VERTICES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*// Simply discard all old data and start over when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleTaluka.TABLE_NAME);
        *//*db.execSQL("DROP TABLE IF EXISTS " + singlePSU.TABLE_NAME);*//*
        db.execSQL("DROP TABLE IF EXISTS " + EnumBlockTable.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + singleUser.TABLE_NAME);
        onCreate(db);*/

       /* switch (oldVersion) {
            case 1:
                db.execSQL(SQL_CREATE_VERTICES_TABLE);
                db.execSQL(SQL_CREATE_MARKER_TABLE);

        }*/

    }

    public Long lastInsertId() {
        Long id = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT last_insert_rowid()";
        Cursor c = db.rawQuery(qry, null);
        if (c != null && c.moveToFirst()) {
            id = Long.valueOf(c.getString(0));
            c.close();
        } else {
            id = Long.valueOf(-1);
        }

        return id;
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
                singleVertices.COLUMN_GEO_AREA,
                singleVertices.COLUMN_PSCODE
        };

        String whereClause = singleVertices.COLUMN_CLUSTER_CODE + " = ? AND ("+singleVertices.COLUMN_POLY_SEQ +" != '' OR "+singleVertices.COLUMN_POLY_SEQ+" != null)";
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
                singleVertices.COLUMN_GEO_AREA,
                singleVertices.COLUMN_PSCODE
        };

        String whereClause = singleVertices.COLUMN_CLUSTER_CODE + " = ? AND ("+singleVertices.COLUMN_POLY_SEQ +" == '' OR "+singleVertices.COLUMN_POLY_SEQ+" == null)";
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
                values.put(singleVertices.COLUMN_GEO_AREA, vc.getgeoarea());
                values.put(singleVertices.COLUMN_PSCODE, vc.getpcode());

                db.insert(singleVertices.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }


}