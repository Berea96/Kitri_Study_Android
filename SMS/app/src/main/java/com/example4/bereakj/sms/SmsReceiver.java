package com.example4.bereakj.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import vo.SMS;

public class SmsReceiver extends BroadcastReceiver {

    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        for (int n = 0; n < messages.length; n++) {
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
        }

        String t = smsMessage[0].getOriginatingAddress();
        String m = smsMessage[0].getMessageBody();

        SMS sms = new SMS(t, m);
        sms.setType("send");

        Main2Activity.list.add(0, sms);
        Main2Activity.changeList();

        Toast toast = Toast.makeText(context, "Received SMS: " + t + "\nfrom : " + m, Toast.LENGTH_LONG);
        toast.show();
    }
}
