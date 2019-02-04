package com.example4.bereakj.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Intent Declaration
    private Intent intent;

    private ProgressBar pb;

    //ServiceConnection Declaration
    private ServiceConnection mConnection;

    private MyService.MyBinder mb;

    private boolean bind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variable Initialize Method
        initialize();
        progressBarSet();
    }

    //Variable Initialize Method
    public void initialize() {
        intent = new Intent(this, MyService.class);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        mConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("연결 성공");
                System.out.println("activity에서 num : " +
                        ((MyService.MyBinder)service).getNum());
                mb = (MyService.MyBinder)service;
                System.out.println("activity에서 num 150으로 변경");
                ((MyService.MyBinder)service).setNum(150);
                System.out.println("activity에서 num : " +
                        ((MyService.MyBinder)service).getNum());
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                System.out.println("연결 해제");
            }
        };
    }

    public void progressBarSet() {
        pb.setProgress(50);
        System.out.println(pb.getProgress());
    }

    //button1 handler
    public void onBtn1(View v) {
        startService(intent);
    }

    //button2 handler
    public void onBtn2(View v) {
        bind = true;
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    //button3 handler
    public void onBtn3(View v) {
        if(mb != null) {
            mb = null;
            unbindService(mConnection);
        }
        else toast("Bind 후에 시도하세요.");
    }

    //button4 handler
    public void onBtn4(View v) {
        if(mb != null) mb = null;
        stopService(intent);
    }

    //button5 handler
    public void onUp(View v) {
        if(mb == null) toast("Volume 서비스를 연결하세요.");
        else {
            switch (mb.upVol()) {
                case 10 : pb.setProgress(pb.getProgress() + 10);
                    break;
                default : toast("잘못된 동작!");
                    break;
            }
        }
    }

    //button6 handler
    public void onDown(View v) {
        if(mb == null) toast("Volume 서비스를 연결하세요.");
        else {
            switch (mb.downVol()) {
                case -10 : pb.setProgress(pb.getProgress() - 10);
                    break;
                default : toast("잘못된 동작!");
                    break;
            }
        }
    }

    //Toast~!
    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
