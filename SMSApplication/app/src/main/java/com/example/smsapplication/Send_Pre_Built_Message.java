package com.example.smsapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Send_Pre_Built_Message extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String activeNumber,selected_code,messageBody="Hello !",codeString;
    Spinner codeSpinner;
    Spinner catSpinner;
    EditText number,ammount;
    MyHelper myHelper;
    ArrayList<ShortCode_Model> codeModels;
    ArrayList<CategoryModel> arrayList;
    Intent intent;
    private Spinner mAdapter;
    Button send,back;
    int MY_PERMISSION_REQUEST_SEND=1;
    CategoryAdapter categoryAdapter;
    ArrayList<SpinnerAdp> ListData;
    String SENT="SMS_SEND";
    String DELIVERD="SMD_DELIVERED";
    spinner spinClass;
    PendingIntent sentPI,deleiveredPI;
    BroadcastReceiver sentsmsBroadCast,smsdeliveredBroadCast;
    StringBuilder stringBuilder=new StringBuilder();
    ArrayList<SpinnerAdp> codes;
    BroadcastReceiver broadcastReceiver;
    IntentFilter filter;
    int catID;
    String user_number=null,send_amm=null;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Send_Pre_Built_Message.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                       number.setText("");
                       ammount.setText("");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__pre__built__message);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Destination_Number", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        myHelper=new MyHelper(this);
        codeSpinner=findViewById(R.id.codeSpinner);
        codeSpinner.setOnItemSelectedListener(this);
        number=findViewById(R.id.desNumber);
        ammount=findViewById(R.id.ammountEdit);
        activeNumber=pref.getString("Destination_Number", null); // getting String
        send=findViewById(R.id.sendMessageDestination);
        back=findViewById(R.id.backBtn);
        catSpinner=findViewById(R.id.categorySpinner);
        catSpinner.setOnItemSelectedListener(this);

        getAllCategory();





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Send_Pre_Built_Message.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });






        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_number=number.getText().toString();
                send_amm=ammount.getText().toString();


                if (ActivityCompat.checkSelfPermission(Send_Pre_Built_Message.this,
                        Manifest.permission.SEND_SMS) !=
                        PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Send_Pre_Built_Message.this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSION_REQUEST_SEND);
                    ActivityCompat.requestPermissions(Send_Pre_Built_Message.this,new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISSION_REQUEST_SEND);
                    ActivityCompat.requestPermissions(Send_Pre_Built_Message.this,new String[]{Manifest.permission.READ_SMS},MY_PERMISSION_REQUEST_SEND);
                }
                else
                {
                    SmsManager sms=SmsManager.getDefault();


                    if(!user_number.equals("") && !send_amm.equals("")) {

                        if (codeString != null) {
                            messageBody = codeString.replace("{{number}}", user_number);
                            messageBody = messageBody.replace("{{amount}}", send_amm);
                            sentPI = PendingIntent.getBroadcast(Send_Pre_Built_Message.this, 0, new Intent(SENT), 0);
                            deleiveredPI = PendingIntent.getBroadcast(Send_Pre_Built_Message.this, 0, new Intent(DELIVERD), 0);
                            sms.sendTextMessage(activeNumber, null, messageBody, sentPI, null);
                            saveMessage(activeNumber, messageBody);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Number and Ammount !", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }

    private void getAllCategory() {
        arrayList= myHelper.spinnerAllCategory();
        categoryAdapter=new CategoryAdapter(Send_Pre_Built_Message.this,arrayList);
        catSpinner.setAdapter(categoryAdapter);
    }


    private void saveMessage(String activeNumber, String messageBody) {
            boolean result=myHelper.saveMessage(activeNumber,messageBody);
            if(result==false)
            {
                Toast.makeText(getApplicationContext(), "Message Not Save!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Message Save Successfully !", Toast.LENGTH_LONG).show();
//                myHelper.getAllMessage();
                number.setText("");
                ammount.setText("");


            }
    }


    private void getAllCodeRequest(int catID) {

       codes= myHelper.spinnerAllCodes(catID);
           //ArrayAdapter<SpinnerAdp> dataAdapter = new ArrayAdapter<SpinnerAdp>(this, android.R.layout.simple_spinner_item, codes);
           //

        for(SpinnerAdp item : codes){
            System.out.println("Log >> " + item.getId() + " >> " + item.getName());
        }

        spinClass = new spinner(this,codes);
        //spinClass.setDropDownViewResource(R.layout.customspinnerlayout);
        codeSpinner.setAdapter(spinClass);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin = (Spinner)adapterView;
        Spinner spin2 = (Spinner)adapterView;

        if(spin.getId() == R.id.categorySpinner)
        {
            catID=arrayList.get(i).id;
            getAllCodeRequest(catID);
        }
        if(spin2.getId() == R.id.codeSpinner)
        {
            int codeID=codes.get(i).id;
            codeString=myHelper.getStringCode(codeID);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//       int codeID=codes.get(i).id;
//       codeString=myHelper.getStringCode(codeID);
//       Log.i("Code_String: ",codeString);
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}
