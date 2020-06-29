package com.example.smsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingScreen extends AppCompatActivity {


    Button back;
    Switch service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);


        back=findViewById(R.id.backBtn);
        service=findViewById(R.id.serviceSwitch);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(isMyServiceRunning(SettingScreen.this))
        {
            service.setChecked(true);
        }
        else {
            service.setChecked(false);
        }

        service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                                != PackageManager.PERMISSION_DENIED) {
                            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
                            startService(new Intent(SettingScreen.this, SmsService.class));
                        }
                    }
                    else {
                        Intent intent = new Intent();
                        intent.setAction(SmsService.ACTION);
                        intent.putExtra(SmsService.STOP_SERVICE_BROADCAST_KEY, SmsService.RQS_STOP_SERVICE);
                        stopService(new Intent(SettingScreen.this, SmsService.class));
                        Toast.makeText(SettingScreen.this, "Service End !", Toast.LENGTH_LONG).show();
                    }
            }
        });
    }

    private boolean isMyServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SmsService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1000)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted !",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Permission Denied !",Toast.LENGTH_LONG).show();
            }
        }
    }
}
