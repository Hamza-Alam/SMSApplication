package com.example.smsapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class dialUssd extends AppCompatActivity {

    EditText ussdInput;
    Button dialUSSD;
    private TelephonyManager telephonyManager;

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_ussd);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        ussdInput = findViewById(R.id.ussdInput);
        dialUSSD = findViewById(R.id.dialUSSD);

        final HashMap map = new HashMap<>();
        map.put("KEY_LOGIN",new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR",new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));

        dialUSSD.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                String s = ussdInput.getText().toString();

                if ((s.startsWith("*")) && (s.endsWith("#"))) {

                    final USSDApi ussdApi = USSDController.getInstance(dialUssd.this);
                    ussdApi.callUSSDInvoke(s, 0, map, new USSDController.CallbackInvoke() {
                        @Override
                        public void responseInvoke(String message) {
                            ((TextView)findViewById(R.id.usddOutput)).setText(message);
                            // message has the response string data
//                            String dataToSend = "2";// <- send "data" into USSD's input text
//                            ussdApi.send(dataToSend,new USSDController.CallbackMessage(){
//                                @Override
//                                public void responseMessage(String message) {
//                                    ((TextView)findViewById(R.id.usddOutput)).setText(message);
//                                    // message has the response string data from USSD
//                                }
//                            });
                        }

                        @Override
                        public void over(String message) {
                            ((TextView)findViewById(R.id.usddOutput)).setText(message);
                        }
                    });

                } else {
                    ((TextView)findViewById(R.id.usddOutput)).setText("Not a Valid String");
                }

            }
        });
    }

}
