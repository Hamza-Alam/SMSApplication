package com.example.smsapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class SmsReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
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

                        if(msgBody.startsWith("*") && msgBody.endsWith("#")){

                            String [] newArray = msgBody.split(Pattern.quote("*"));

                            String recordNumber = "*"+ newArray[1];

                        }else{
                            //write code here to make it's status read
                        }

                    }
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }

}
