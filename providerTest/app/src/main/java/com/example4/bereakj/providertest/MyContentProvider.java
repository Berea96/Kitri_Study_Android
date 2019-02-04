package com.example4.bereakj.providertest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {
    public static final String DB_NAME = "memberdata.db";
    public static final String DB_TABLE = "member";
    public static final int DB_VERS = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    public static final int ID_COLUMN = 0;
    public static final int NAME_COLUMN = 1;
    public static final int EMAIL_COLUMN = 2;

    private SQLiteDatabase db;
    private Context context;
    private myDBHelper dbHelper;

    public static final Uri CONTENT_URI = Uri
            .parse("content://com.example4.bereakj.providertest.myProvider/email");

    private static final int DATAS = 1;
    private static final int DATA_ID = 2;

    private static final UriMatcher myURIMatcher;

    static {
        myURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        myURIMatcher.addURI("com.example4.bereakj.providertest.myProvider",
                "email", DATAS);
        myURIMatcher.addURI("com.example4.bereakj.providertest.myProvider",
                "email/#", DATA_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        dbHelper = new myDBHelper(context, DB_NAME, null, DB_VERS);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
        return (db == null) ? false : true;
    }

    @Override
    public String getType(Uri uri) {
        switch (myURIMatcher.match(uri)) {
            case DATAS:
                return "com.example4.bereakj.providertest.myProvider.dir";
            case DATA_ID:
                return "com.example4.bereakj.providertest.myProvider.item";
            default:
                return null;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DB_TABLE);

        if (myURIMatcher.match(uri) == DATA_ID) {
            String id = uri.getPathSegments().get(1);
            qb.appendWhere(KEY_ID + "=" + id);
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(DB_TABLE, null, values);

        if (rowID > 0) {

            Uri uri_id = ContentUris.withAppendedId(CONTENT_URI, rowID);

            getContext().getContentResolver().notifyChange(uri_id, null);
            return uri;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int cnt;
        switch (myURIMatcher.match(uri)) {

            case DATAS:
                cnt = db.update(DB_TABLE, values, selection, selectionArgs);
                break;

            case DATA_ID:
                String id = uri.getPathSegments().get(1);
                cnt = db.update(DB_TABLE, values, KEY_ID + "="	+ id
                        + (!TextUtils.isEmpty(selection) ? " AND ("
                        + selection + ')' : ""), selectionArgs);
                break;
            default:
                return 0;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        int cnt;

        switch (myURIMatcher.match(arg0)) {

            case DATAS:
                cnt = db.delete(DB_TABLE, arg1, arg2);
                break;

            case DATA_ID:
                String id = arg0.getPathSegments().get(1);
                cnt = db.delete(DB_TABLE, KEY_ID + "="	+ id
                        + (!TextUtils.isEmpty(arg1) ? " AND (" + arg1 + ')' : ""), arg2);

                break;
            default:
                return 0;
        }
        getContext().getContentResolver().notifyChange(arg0, null);

        return cnt;
    }

    public static class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " + DB_TABLE
                + " (" + KEY_ID + " integer primary key autoincrement, "
                + KEY_NAME + " text not null," + KEY_EMAIL
                + " text not null );";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
                              int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }
    }

}
