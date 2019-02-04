package com.example4.bereakj.sms;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vo.Message;

public class SendMessage extends AppCompatActivity {

    private EditText tel;
    private EditText msg;

    private Intent intent;

    private Message message;


    private SmsManager sm;

    private final static String SENT = "ACTION_SENT";
    private final static String DELIVERED = "ACTION_DELIVERED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        initialize();
        etSet(intent.getStringExtra("tel"));
    }

    public void initialize() {
        tel = (EditText)findViewById(R.id.editText);
        msg = (EditText)findViewById(R.id.editText2);
        intent = getIntent();
        message = new Message();
    }

    public void etSet(String t) {
        tel.setText(t);
    }

    public void onSend(View v) {
        String t = tel.getText().toString();
        String m = msg.getText().toString();

        if(t.equals("") || m.equals("")) {
            toast("빈칸없이");
        }
        else {
            if(intent.getStringExtra("tel").equals("")){
                setting(t, m);
                intent.putExtra("msg", message);
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                setting(t, m);
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("msg", message);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }
        }
    }

    public void setting(String t, String m) {
        messageSet(t, m);
        sendSMS(t, m);
    }

    public void sendSMS(String t, String m) {
        PendingIntent sent = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);
        PendingIntent delivered = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);
        sm.sendTextMessage(t, null, m, sent, delivered);
    }

    public void messageSet(String t, String m) {
        message.setTel(t);
        message.setMsg(m);
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
