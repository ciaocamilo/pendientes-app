package com.misiontic.testpendientesapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final  String DB_NAME = "test_task_db.sqlite";
    private static final int DB_VERSION = 1;

    private static final String TASK_TABLE_CREATE = "CREATE TABLE tareas(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                    "descripcion TEXT)";

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void insertData(String sentence) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sentence);
    }

    public Cursor getData(String sentence, String[] params) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cu = db.rawQuery(sentence, params);
        return cu;
    }

    /**** adicional ****/
    public int deleteData(String table, String whereClause, String[] params) {
        SQLiteDatabase db = getWritableDatabase();
        int nRows = db.delete(table, whereClause, params);
        return nRows;
    }
    /********************/

}
