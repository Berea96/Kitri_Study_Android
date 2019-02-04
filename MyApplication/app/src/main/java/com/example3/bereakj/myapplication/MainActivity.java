package com.example3.bereakj.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vo.Member;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyReceiver();
        registerReceiver(receiver, new IntentFilter("com.example3.bereakj.myapplication.myAction2"));
    }

    public void onBtn1(View v) {
        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+12345));
        startActivity(intent);
    }

    public void onBtn2(View v) {
        intent = new Intent("com.example3.bereakj.myapplication.myAction2");
        sendBroadcast(intent);
    }

    public void onBtn3(View v) {
        ComponentName cn = new ComponentName("com.example.bereakj.intenttest",
                "com.example.bereakj.intenttest.Main3Activity");
        intent = new Intent("com.example.bereakj.intenttest.activity3");
        intent.setComponent(cn);
        startActivity(intent);
    }
    public void onBtn4(View v) {
        ComponentName cn = new ComponentName("com.example.bereakj.intenttest",
                "com.example.bereakj.intenttest.Main2Activity");
        intent = new Intent("com.example.bereakj.intenttest.activity2");
        intent.setComponent(cn);
//        intent.putExtra("name", "kkj");
//        intent.putExtra("age", 24);
        intent.putExtra("member", new Member("kkj", 30));
        startActivity(intent);
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}