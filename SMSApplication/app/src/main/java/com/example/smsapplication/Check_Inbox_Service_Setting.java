package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Check_Inbox_Service_Setting extends AppCompatActivity {


    Switch s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__inbox__service__setting);

        s=findViewById(R.id.simpleSwitch);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    startService(new Intent(Check_Inbox_Service_Setting.this, MessageService.class));
                }
                else{
                    Intent intent = new Intent();
                    intent.setAction(SmsService.ACTION);
                    intent.putExtra(SmsService.STOP_SERVICE_BROADCAST_KEY, SmsService.RQS_STOP_SERVICE);
                    stopService(new Intent(Check_Inbox_Service_Setting.this, MessageService.class));
//                    Toast.makeText(Check_Inbox_Service_Setting.this, "Service End !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
