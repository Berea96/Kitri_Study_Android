package com.example4.bereakj.contentprovidertest;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentValues values = new ContentValues();

        String contactId = "bbb";
        values.put(ContactsContract.RawContacts.CONTACT_ID, contactId);
        Uri contactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);

        long contactId_l = ContentUris.parseId(contactUri);

        values.clear();

        values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, contactId_l);

        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Phone.NUMBER, "010-5656-7878");
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        values.put(Phone.LABEL, "bbb");
        Uri dataUri = getContentResolver().insert(Data.CONTENT_URI, values);

        Cursor c = getContentResolver().query(Data.CONTENT_URI,
                new String[] { Data._ID, Phone.NUMBER, Phone.TYPE,
                        Phone.LABEL }, null, null, null);
        String id = null, number = null, type = null, label = null;
        int num;
        if (c.moveToFirst()) {
            do {
                number = c.getString(c.getColumnIndex(Phone.NUMBER));

                num = c.getShort((c.getColumnIndex(Phone.TYPE)));

                switch (num) {
                    case 1:
                        type = "HOME";
                        break;
                    case 2:
                        type = "MOBILE";
                        break;
                    case 3:
                        type = "WORK";
                        break;
                    default:
                        type = "ETC";
                }
                label = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL));
                String str = "label:" + label + ", number:" + number+ ", type:" + type;
                Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
                toast.show();
            } while (c.moveToNext());
        }
    }
}
