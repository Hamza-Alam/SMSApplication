package com.example.smsapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Sms_Listerner extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Intent myIntent = new Intent(context, SmsService.class);
                context.startService(myIntent);
            }
    }
}
