package com.example4.bereakj.sms;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vo.SMS;

public class Main2Activity extends AppCompatActivity {

    private Intent intent;

    private ListView smsList;

    public static ArrayList<SMS> list;

    private static ArrayAdapter<SMS> adapter;

    final static int WRITE = 1;
    final static int REPLY = 2;

    private SMS sms;

    private SendReceiver sr;
    private DeliveredReceiver dr;

    private final static String SENT = "ACTION_SENT";
    private final static String DELIVERED = "ACTION_DELIVERED";

    static{
        list = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initialize();
        listSet();
        receiverSet();
        if(intent != null)
            writeResult(intent);
    }

    public void initialize() {
        intent = getIntent();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        smsList = (ListView)findViewById(R.id.sms_list);
        sr = new SendReceiver();
        dr = new DeliveredReceiver();
        intent = getIntent();
    }

    public void receiverSet() {
        registerReceiver(sr, new IntentFilter(SENT));
        registerReceiver(dr, new IntentFilter(DELIVERED));
    }

    public void listSet() {
        smsList.setAdapter(adapter);
        smsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sms = list.get(position);
                String m = sms.getMsg();
                String n = sms.getNumber();
                String t = sms.getTime();
                AlertDialog.Builder ab = new AlertDialog.Builder(Main2Activity.this);
                ab.setTitle("수신한 메시지")
                        .setMessage(n + "\n" + m + "\n" + t)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("답장", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toast("답장");
                                reply(sms);
                            }
                        });
                AlertDialog ad = ab.create();
                ad.show();
            }
        });
    }

    public void reply(SMS sms) {
        intent = new Intent(this, SendActivity.class);
        intent.putExtra("tel", sms.getNumber());
        intent.putExtra("type", "reply");
        startActivityForResult(intent, REPLY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, WRITE, 0, "WRITE");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case WRITE :
                toast("작성");
                write();
                break;
        }
        return true;
    }

    public void write() {
        intent = new Intent(this, SendActivity.class);
        intent.putExtra("tel", "");
        intent.putExtra("type", "write");
        startActivityForResult(intent, WRITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case WRITE :
                    toast("성공");
                    writeResult(data);
                    break;
                case REPLY :
                    toast("성공2");
                    writeResult(data);
                    break;
                default : toast("실패");
                    break;
            }
        }
    }

    public void writeResult(Intent data) {
        if(data != null) {
            SMS s = (SMS) data.getSerializableExtra("sms");
            list.add(0, s);
            changeList();
        }
        else {
            toast("하이");
        }
    }

    public static void changeList() {
        adapter.notifyDataSetChanged();
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
