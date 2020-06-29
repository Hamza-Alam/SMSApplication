package com.example.smsapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsService extends Service {

//
    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE_BROADCAST_KEY="StopServiceBroadcastKey";
    final static int RQS_STOP_SERVICE = 1;

    Sms_Listerner smsListerner;

    @Override
    public void onCreate() {

        smsListerner=new Sms_Listerner();
        super.onCreate();
    }
    public SmsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsListerner, mIntentFilter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        unregisterReceiver(smsListerner);
//        stopSelf();
        super.onDestroy();

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class Sms_Listerner extends BroadcastReceiver {

//        private SharedPreferences preferences;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

                Log.d(" New Message 1: ", "get item number ");
                Log.d(" New Message 2: ", "get item number ");
                Log.d(" New Message 3: ", "get item number ");
                Log.d(" New Message 4: ", "get item number ");
                Log.d(" New Message 5: ", "get item number ");
                Log.d(" New Message 6: ", "get item number ");
                Log.d(" New Message 7: ", "get item number ");
                Log.d(" New Message 8: ", "get item number ");
                Log.d(" New Message 9: ", "get item number ");
                Log.d(" New Message 0: ", "get item number ");

                Toast.makeText(context,"SMS Recived !",Toast.LENGTH_LONG).show();
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                String msg_from;
                if (bundle != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            msg_from = msgs[i].getOriginatingAddress();
                            String msgBody = msgs[i].getMessageBody();
                            Toast.makeText(context,"From : " + msg_from + " , Body : " + msgBody,Toast.LENGTH_LONG).show();

                        }
                    }catch(Exception e){
                            Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }
    }
}
