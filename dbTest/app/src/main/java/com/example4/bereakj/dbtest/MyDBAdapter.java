package com.example4.bereakj.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBAdapter {

    //DB name
    private static final String DB = "MyDB.db";

    //Table name
    private static final String DB_TABLE = "MyTable";

    //Column name
    private static final String ID = "_id";

    //Column name
    private static final String NAME = "name";

    //DB version
    private static final int DB_VERS = 1;

    //DB api
    private SQLiteDatabase mdb;

    private final Context context;

    private MyHelper mHelper;

    public MyDBAdapter(Context context) {
        this.context = context;
        mHelper = new MyHelper(context, DB, null, DB_VERS);
    }

    //DataBase open
    public void open() throws SQLiteException {
        try {
            //read and write
            mdb = mHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            //read only
            mdb = mHelper.getReadableDatabase();
        }
    }

    //DataBase close
    public void close() {
        mdb.close();
    }

    //Insert data
    public long insertData(String name) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);

        return mdb.insert(DB_TABLE, null, cv);
    }

    //Remove data
    public int removeData(long index) {
        return mdb.delete(DB_TABLE, ID + "=" + index, null);
    }

    //Update data
    public int updateData(long index, String name) {
        String where = ID + " = " + index;
        ContentValues cv = new ContentValues();
        cv.put("name", name);

        return mdb.update(DB_TABLE, cv, where, null);
    }

    //Select data only one
    public String getOne(long id) {
        Cursor cursor =  mdb.query(DB_TABLE, new String[] {ID, NAME}, ID + "=" + id,
                null,null,null,null);
        if(cursor.moveToFirst()) {
            return cursor.getString(1);
        }
        return null;
    }

    //Select data all
    public Cursor getAll() {
        return mdb.query(DB_TABLE, new String[] { ID, NAME}, null,
                null, null, null, null);
    }

    //DB open version helper
    private static class MyHelper extends SQLiteOpenHelper {

        private static final String DB_CREATE =
                "create table " + DB_TABLE + " (" + ID +
                " integer primary key autoincrement, " + NAME + " text not null );";

        public MyHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oVers, int nVers) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
