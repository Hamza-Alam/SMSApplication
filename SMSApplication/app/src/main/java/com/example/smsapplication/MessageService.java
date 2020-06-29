package com.example.smsapplication;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MessageService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(MessageService.this,"Service Created !",Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(MessageService.this,"Service Start !",Toast.LENGTH_LONG).show();
        if(ContextCompat.checkSelfPermission(getBaseContext(),"android.permission.READ_SMS")== PackageManager.PERMISSION_GRANTED){

            getAllMessage();
        }
        else{
//            final int REQUEST_CODE_ASK_PERMISSION=123;
//            ActivityCompat.requestPermissions(MessageService.this, new String[]{"android.permission.READ_SMS"},REQUEST_CODE_ASK_PERMISSION);
        }

        return START_STICKY;
    }

    private void getAllMessage() {
        Toast.makeText(MessageService.this, "Get All Message Fuction Here !", Toast.LENGTH_LONG).show();
        final Uri SMS_INBOX = Uri.parse("content://sms/");
        Cursor c = getContentResolver().query(SMS_INBOX, null, "read = 0", null, null);
        while (c.moveToNext()) {
            String read = c.getString(c.getColumnIndex("read"));
            String status = c.getString(c.getColumnIndex("status"));
            String body = c.getString(c.getColumnIndex("body"));
            System.out.println( "READ >> " + read + " Status >> " + status + " Body >> " + body);
        }
    }


    @Override
    public void onDestroy() {
        Toast.makeText(MessageService.this,"Service Stop !",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
