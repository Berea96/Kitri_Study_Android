package com.example4.bereakj.providertest;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import vo.Member;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private EditText name;
    private EditText email;
    private ArrayAdapter s;
    private ArrayList<Member> data;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) findViewById(R.id.listView);
        name = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText2);

        data = new ArrayList<Member>();

        s = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

        myListView.setAdapter(s);

        listPrint();

        registerForContextMenu(myListView);
    }

    public void clear(View view) {
        ((EditText)view).setText("");
    }

    public void save(View view) {
        String str_name = name.getText().toString();
        String str_email = email.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(MyContentProvider.KEY_NAME, str_name);
        cv.put(MyContentProvider.KEY_EMAIL, str_email);

        getContentResolver().insert(MyContentProvider.CONTENT_URI, cv);

        listPrint();

        name.setText("");
        email.setText("");
    }
    public void listPrint() {
        c = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
        s.clear();
        if(c.moveToFirst()) {
            do{
                Member m = new Member();
                m.setId(c.getInt(0));
                m.setName(c.getString(1));
                m.setEmail(c.getString(2));
                s.add(m);
            }while (c.moveToNext());
        }
        s.notifyDataSetChanged();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

//        getMenuInflater().inflate(R.menu.menu_main, menu);

//        menu.setHeaderTitle("Delete Data");

        menu.setHeaderTitle("Delete Data");
        menu.add(0, 1, 0, "DEL");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = 0;
        int index = menuInfo.position;

        Member m = data.get(index);

        id = m.getId();

        switch (item.getItemId()) {

//            case R.id.del:
            case 1:

                if (id != 0) {

                    Uri dataURI =
                            ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, id);

                    getContentResolver().delete(dataURI, null, null);
                }
                listPrint();
                break;
        }
        return true;
    }
}
