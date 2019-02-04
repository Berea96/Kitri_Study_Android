package com.example4.bereakj.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    private int num;

    private MyBinder mb = new MyBinder();

    public MyService() {
    }

    class MyBinder extends Binder {
        int getNum() {
            return num;
        }

        void setNum(int n) {
            num = n;
        }

        int upVol() {
            num = 0;
            return num += 10;
        }

        int downVol() {
            num = 0;
            return num -= 10;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        toast("onCreate()");
        System.out.println("onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        toast("onStart()");
        System.out.println("onStart()");

        for(int i = 0; i< 10; i++) {
            num++;
//            toast("num : " + num);
            System.out.println("num : " + num);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
//        toast("onBind()");
        System.out.println("onBind()");
        return mb;

    }

    @Override
    public boolean onUnbind(Intent intent) {
//        toast("onUnBind()");
        System.out.println("onUnBind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
//        toast("onReBind()");
        System.out.println("onReBind()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        toast("onDestroy()");
        System.out.println("onDestroy()");
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
