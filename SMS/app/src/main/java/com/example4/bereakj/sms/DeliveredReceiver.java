package com.example4.bereakj.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class DeliveredReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int result = getResultCode();
        switch (result) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "receive : success", Toast.LENGTH_SHORT).show();
                break;
            case Activity.RESULT_CANCELED :
                Toast.makeText(context, "receive : fail", Toast.LENGTH_SHORT).show();
        }
    }
}
