package com.example.bereakj.intenttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vo.Member;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private MyReceiver2 mr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mr2 = new MyReceiver2(this);
        //메니페스트 파일에 리시버를 등록하는 것과 동일. param1:리시버객체, param2:인텐트 필터객체
        registerReceiver(mr2, new IntentFilter("myAction"));
    }

    public void intentInit() {
        intent = new Intent(this, Main2Activity.class);
    }


    public void onStart(View v) {
        toast("시작");
        intentInit();
        intent.putExtra("name", "aaa");
        intent.putExtra("age", 24);
        startActivity(intent);
    }

    public void onResult(View v) {
        toast("결과");
        intentInit();
        intent.putExtra("member", new Member("bbb", 24));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 1:
                    Member member = (Member)data.getSerializableExtra("member");
                    toast(member.getName() + " : " + member.getAge());
                    break;
            }
        }
    }

    public void onBtn3(View v) {
        intent = new Intent(this, MyReceiver.class);
        intent.putExtra("x", 10);
        intent.putExtra("y", 20);
        sendBroadcast(intent);
    }

    public void onRec2(View v) {
        intent = new Intent("myAction");
        sendBroadcast(intent);
    }

    public void onBtnOn(View v) {
        intent = new Intent("com.example3.bereakj.myapplication.myAction2");
        sendBroadcast(intent);
    }

    public void toast(String s) {
        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
    }

    static class MyReceiver2 extends BroadcastReceiver {

        //AlertDialog를 위해
        private Activity a;

        public MyReceiver2(Activity a) {
            this.a = a;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(a);
            builder.setMessage("리시버 객체 직접 생성")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
