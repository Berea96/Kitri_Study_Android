package com.example4.bereakj.sms;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vo.Message;

public class MainActivity extends AppCompatActivity {

    final static int WRITE = 1;

    private Intent intent;

    private ArrayList<Message> message;

    private msgAdapter ma;

    private ListView msg_list;

    private Message msg;

    private SendReceiver sr;
    private DeliveredReceiver dr;

    private final static String SENT = "ACTION_SENT";
    private final static String DELIVERED = "ACTION_DELIVERED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        registerReceiver(sr, new IntentFilter(SENT));
        registerReceiver(dr, new IntentFilter(DELIVERED));
        msg_list.setAdapter(ma);
        listSet(intent);
        ma.notifyDataSetChanged();
    }

    public void initialize() {
        intent = getIntent();
        message = new ArrayList<>();
        msg_list = (ListView)findViewById(R.id.msg_list);
        ma = new msgAdapter(this, R.layout.message_layout, message);
        sr = new SendReceiver();
        dr = new DeliveredReceiver();
    }

    public void listSet(Intent intent) {
        message.add(0, (Message)intent.getSerializableExtra("msg"));
        ma.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, WRITE, 0, "WRITE");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case WRITE : toast("작성");
                intent = new Intent(this, SendMessage.class);
                intent.putExtra("tel", "");
                startActivityForResult(intent, WRITE);
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toast("도착");
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case WRITE : listSet(data);
                    break;
            }
        }
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
