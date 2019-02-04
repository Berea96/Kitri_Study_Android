package com.example4.bereakj.resolver;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import vo.Member;

public class MainActivity extends Activity {
    private EditText et1;
    private EditText et2;
    private ListView myListView;
    private ArrayAdapter<Member> s;
    private ArrayList<Member> data;
    private Cursor c;
    public static final Uri CONTENT_URI;

    static {
        CONTENT_URI = Uri
                .parse("content://com.example4.bereakj.providertest.myProvider/email");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        myListView.setAdapter(s);
        show();
    }

    public void initialize() {
        myListView = (ListView) findViewById(R.id.listView);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        data = new ArrayList<Member>();
        s = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
    }

    public void searchById(View v) {

        Uri idUri = Uri.withAppendedPath(CONTENT_URI, et1.getText()+ "");

        Cursor cursor = getContentResolver().query(idUri, null, null, null, null);

        int emailIdx = cursor.getColumnIndexOrThrow("email");

        if (cursor.moveToFirst())
            do {
                String email = cursor.getString(emailIdx);
                Toast.makeText(getApplicationContext(),
                        email, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
    }

    public void searchByName(View v) {

        String name = et2.getText() + "";

        String[] col1 = new String[] { "email" };

        String[] col2 = new String[] { name };

        String where = "name=?";

        Cursor cursor = getContentResolver().query(CONTENT_URI, col1,
                where, col2, null);

        int emailIdx = cursor.getColumnIndexOrThrow("email");

        if (cursor.moveToFirst())
            do {
                String email = cursor.getString(emailIdx);
                Toast.makeText(getApplicationContext(),
                        email, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
    }
    public void clear(View view) {
        ((EditText)view).setText("");
    }
    public void show() {
        c = getContentResolver().query(CONTENT_URI, null, null, null, null);
        s.clear();
        if(c.moveToFirst()) {
            do{
                Member m = new Member();
                m.setName(c.getString(1));
                m.setEmail(c.getString(2));
                s.add(m);
            }while (c.moveToNext());
        }
        s.notifyDataSetChanged();
    }
}
