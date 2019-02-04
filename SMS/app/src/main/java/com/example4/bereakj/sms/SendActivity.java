package com.example4.bereakj.sms;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vo.SMS;

public class SendActivity extends AppCompatActivity {

    private EditText tel;
    private EditText msg;

    private Intent intent;

    private SMS sms;

    private SmsManager sm;

    private final static String SENT = "ACTION_SENT";
    private final static String DELIVERED = "ACTION_DELIVERED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        initialize();
        etSet(intent.getStringExtra("tel"), "");
    }

    public void initialize() {
        tel = (EditText)findViewById(R.id.editText3);
        msg = (EditText)findViewById(R.id.editText4);
        intent = getIntent();
        sms = new SMS();
        sm = SmsManager.getDefault();
    }

    public void etSet(String t, String m) {
        tel.setText(t);
        msg.setText(m);
    }

    public void onSend(View v) {
        toast("누름");
        String t = tel.getText().toString();
        String m = msg.getText().toString();
        String type = intent.getStringExtra("type");

        if(t.equals("") || m.equals("")) {
            toast("빈칸 없이");
        }
        else {
            smsSet(t, m, type);
            sendSMS(t, m);
            if(type.equals("phone")) {
                intent = new Intent(this, Main2Activity.class);
                intent.putExtra("sms", sms);
                startActivity(intent);
            }
            else {
                intent.putExtra("sms", sms);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    public void sendSMS(String t, String m) {
        PendingIntent sent = PendingIntent.getBroadcast(this, 0,
                                                        new Intent(SENT), 0);
        PendingIntent delivered = PendingIntent.getBroadcast(this, 0,
                                                        new Intent(DELIVERED), 0);
        sm.sendTextMessage(t, null, m, sent, delivered);
    }

    public void smsSet(String n, String m, String type) {
        sms.setNumber(n);
        sms.setMsg(m);
        sms.setTime();
        sms.setType(type);
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
