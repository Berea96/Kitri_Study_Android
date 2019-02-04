package com.example4.bereakj.dbtest;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText et;

    private ListView lv;

    private Button btn;

    private ArrayList<Member> list;

    private ArrayAdapter<Member> adapter;

    private MyDBAdapter db;

    private int idx;

//    private int edit_id;
//    private boolean flag = true;

    private static final int EDIT = 1;
    private static final int DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        lvSet();
        showList();
    }

    public void initialize() {
        et = (EditText)findViewById(R.id.editText);
        lv = (ListView)findViewById(R.id.list_view);
        btn = (Button)findViewById(R.id.button);
        db = new MyDBAdapter(this);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
    }

    public void lvSet() {
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
    }

    public void showList() {
        list.clear();
        db.open();
        Cursor cursor = db.getAll();
        if(cursor.moveToFirst()) {
           do {
               int id = cursor.getInt(0);
               String name = cursor.getString(cursor.getColumnIndex("name"));
               list.add(new Member(id, name));
           }while(cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
        db.close();
    }

    public void onSave(View v) {
        String n = et.getText().toString();

        if(n.equals("")) toast("빈칸없이");
        else {
            db.open();
//            if(flag) {
            if(btn.getText().toString().equals("save")) {
                toast("save");
                toast(db.insertData(n) + "번째 행 추가 완료");
            }
            else {
                toast("edit");
                toast(db.updateData(list.get(idx).getId(), n) + "행 수정 완료");
            }
            clear();
            db.close();
            showList();
        }
    }

    public void clear() {
        et.setText("");
        btn.setText("save");
//        flag = false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Menu");
        menu.add(0, EDIT, 0, "EDIT");
        menu.add(0, DEL, 0, "DEL");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        idx = info.position;

        switch(item.getItemId()) {
            case EDIT :
                edit();
                break;
            case DEL :
                del();
                break;
            default :
                toast("잘못");
                break;
        }

        return true;
    }

    public void edit() {
        toast("EDIT");
        btn.setText("edit");
        et.setText(list.get(idx).getName());

//        Member m = list.get(idx);
//        edit_id = m.get_id();
//        db.open();
//        String name = db.getOne(list.get(idx).getId());
//        if(name == null) toast("잘못");
//        else {
//            et.setText(name);
//            flag = true;
//        }
//        db.close();
    }

    public void del() {
        toast("DEL");
        db.open();
        toast(db.removeData(list.get(idx).getId()) + "행 삭제 완료");
        db.close();
        showList();
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}