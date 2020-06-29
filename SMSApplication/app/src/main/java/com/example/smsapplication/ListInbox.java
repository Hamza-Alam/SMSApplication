package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListInbox extends AppCompatActivity {


    ListView inboxList;
    String id;

    int MY_PERMISSION_REQUEST_SEND=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inbox);

//        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED){
//            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
//            ActivityCompat.requestPermissions(ListInbox.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
//        }else{
            inboxList = findViewById(R.id.inboxlist);

            if (fetchInbox() != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchInbox());
                inboxList.setAdapter(adapter);
            }
//        }

    }
    public ArrayList<String> fetchInbox()
    {
        ArrayList<String> arrayList=new ArrayList<>();
        Uri uriSms = Uri.parse("content://sms/sent/");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "thread_id", "address", "date", "body"},null,null,null);
        Uri deleteUri = Uri.parse("content://sms/conversations/91");
//        String threadID = "91";
//        Uri thread = Uri.parse( "content://sms/" + threadID);
//        int deleted = getApplicationContext().getContentResolver().delete( thread, null, null );


//        System.out.println("Result :" + deleted);
        System.out.println("Musa Raza >> " + "Total Messages >>" + cursor.getCount());
        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
           id= cursor.getString(0);
            String address = cursor.getString(1) + " >> " +  cursor.getString(2);
            String body = cursor.getString(4);

            arrayList.add("id: "+id+ "Address=&gt; "+address+"n SMS =&gt; "+body);
        }
        deleteMessage("86");
        return arrayList;
    }




    private int deleteMessage( String msg) {
        int count = 0;
        String uri = "content://sms/";
        count = this.getContentResolver().delete(Uri.parse("content://sms/inbox"),null,null);
        Log.i("RESULT: ", String.valueOf(count));
        return count;
    }
}
