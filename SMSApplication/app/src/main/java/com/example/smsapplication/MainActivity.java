package com.example.smsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button moreBtn,destination,preBuiltBtn,shortCodeBtn,messageBtn,listBtn,categoryBtn,refreshBtn,forwardBtn,saveNumbrBtn,functionCallingBtn;
    MyHelper myHelper;





    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Are you sure want to Exit the Application ??");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MyHelper myHelper=new MyHelper(this,null,null,1);
        SQLiteDatabase db = new MyHelper(this).getWritableDatabase();

        myHelper=new MyHelper(this);

        getActiveNumber();


        Log.d("Reading: ", "Reading all contacts..");
        List<DestinationClas> contacts = myHelper.getAllContacts();

        for (DestinationClas cn : contacts) {
            String log = "Number: " + cn.getDestination_number();
            // Writing Contacts to log
            Log.d("Number: ", log);
        }



        destination=findViewById(R.id.destinationBtn);
        preBuiltBtn=findViewById(R.id.sendMessage);
        shortCodeBtn=findViewById(R.id.shortcode);
        messageBtn=findViewById(R.id.messageBtn);
        categoryBtn=findViewById(R.id.category);
        refreshBtn=findViewById(R.id.refresh);
        forwardBtn=findViewById(R.id.forwardBtn);
        saveNumbrBtn=findViewById(R.id.saveNumber);
//        <?xml version="1.0" encoding="utf-8"?>
//<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    tools:context=".Edit_Add_Short_Codes"
//    android:background="#dbf9db">
//
//
//    <androidx.constraintlayout.widget.ConstraintLayout
//        android:id="@+id/topHeader"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:background="@drawable/background_color"
//        android:padding="10dp"
//        app:layout_constraintTop_toTopOf="parent"
//        tools:ignore="MissingConstraints">
//
//        <Button
//            android:id="@+id/backBtn"
//            android:layout_width="30dp"
//            android:layout_height="30dp"
//            android:layout_alignParentLeft="true"
//            android:background="@drawable/ic_keyboard_arrow_left_black_24dp"
//            app:layout_constraintTop_toTopOf="parent"
//            tools:layout_editor_absoluteX="10dp" />
//
//
//        <TextView
//            android:id="@+id/textView"
//            android:layout_width="match_parent"
//            android:layout_height="wrap_content"
//            android:layout_alignStart="@+id/backBtn"
//            android:fontFamily="sans-serif-smallcaps"
//            android:text="Add Code Category"
//            android:textAlignment="center"
//            android:textColor="#ffff"
//            android:textSize="25sp"
//            android:textStyle="bold"
//            tools:ignore="MissingConstraints" />
//
//    </androidx.constraintlayout.widget.ConstraintLayout>
//
//    <TextView
//        android:textStyle="bold"
//        android:textColor="#ff23729a"
//        android:id="@+id/txtNumber"
//        android:textSize="15sp"
//        android:padding="10dp"
//        android:text="Short Code Category Name"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="16dp"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toBottomOf="@+id/topHeader"
//        tools:ignore="MissingConstraints" />
//
//    <EditText
//        android:textColor="#000"
//        android:id="@+id/nameCategory"
//        android:textSize="15sp"
//        android:padding="10dp"
//        android:layout_marginStart="8dp"
//        android:layout_marginEnd="8dp"
//        app:layout_constraintTop_toBottomOf="@+id/txtNumber"
//        android:hint="Enter your Short Code Category Name"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        tools:ignore="MissingConstraints" />
//
//    <TextView
//        android:textStyle="bold"
//        android:textColor="#ff23729a"
//        android:id="@+id/possibleNetwork"
//        android:textSize="15sp"
//        android:padding="10dp"
//        android:text="Short Code Category Name"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="16dp"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toBottomOf="@+id/nameCategory"
//        tools:ignore="MissingConstraints" />
//
//    <EditText
//        android:textColor="#000"
//        android:id="@+id/possibleNetworkText"
//        android:textSize="15sp"
//        android:padding="10dp"
//        android:layout_marginStart="8dp"
//        android:layout_marginEnd="8dp"
//        app:layout_constraintTop_toBottomOf="@+id/possibleNetwork"
//        android:hint="Enter your Short Code Category Name"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        tools:ignore="MissingConstraints" />
//
//    <TextView
//        android:hint="Hint: 0311, 0312, 0313, 0314, 0315, 0316, 0317, 0318, 0319"
//        android:id="@+id/hintPossibleNetwork"
//        android:textSize="15sp"
//        android:padding="10dp"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="8dp"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toBottomOf="@+id/possibleNetworkText"
//        tools:ignore="MissingConstraints" />
//
//
//    <Button
//        android:textColor="#fff"
//        android:id="@+id/addShortCodeCategory"
//        android:layout_width="0dp"
//        android:layout_height="wrap_content"
//        android:layout_marginTop="16dp"
//        android:background="@drawable/button_bg"
//        android:text="Save"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toBottomOf="@+id/hintPossibleNetwork"
//        app:layout_constraintWidth_percent=".50"
//        tools:ignore="MissingConstraints" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>=findViewById(R.id.funCall);
        moreBtn=findViewById(R.id.moreBtn);

        destination.setText(Html.fromHtml("<b><big>" + "Destination Numbers" + "</big></b>" +  "<br />" +
                "<small style>" + "Where it will send Manually Created Short Code messages " + "</small>" + "<br />"));

        shortCodeBtn.setText(Html.fromHtml("<b><big>" + "Short Codes For Messeging" + "</big></b>" +  "<br />" +
                "<small>" + "Codes which will create a custom message in Short COde String Format after taking inputs and going to be send from \"Send Pre Built Message\" Screen" + "</small>" + "<br />"));

        categoryBtn.setText(Html.fromHtml("<b><big>" + "Categories Of Short Codes For Messeging" + "</big></b>" +  "<br />" +
                "<small>" + "Categories of Short Codes which will create a custom message in Short COde String Format after taking inputs and going to be send from \"Send Pre Built Message\" Screen" + "</small>" + "<br />"));

        forwardBtn.setText(Html.fromHtml("<b><big>" + "Auto Short Codes For Messeging/Dialing " + "</big></b>" +  "<br />" +
                "<small>" + "Short Codes which will create a custom message in Short Code String Format after taking inputs and going to be forward it as message or going to dial in Messeging Service" + "</small>" + "<br />"));

        saveNumbrBtn.setText(Html.fromHtml("<b><big>" + "Receipent Of Auto Short Code For Messaging/Dialing" + "</big></b>" + "<br />" +
                "<small>" + "After Dialing the Auto Short Code in service, Receipent Numbers are those where it will send it's response." + "</small>" + "<br />"));

        messageBtn.setText(Html.fromHtml("<b><big>" + "Mesages" + "</big></b>" +  "<br />" +
                "<small>" + "See All Sent and Received Messages" + "</small>" + "<br />"));

        preBuiltBtn.setText(Html.fromHtml("<b><big>" + "Send Pre Built Message" + "</big></b>" +  "<br />" +
                "<small>" + "Here you can send custom created message in Short Code Format after giving it inputs" + "</small>" + "<br />"));

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SettingScreen.class);
                startActivity(intent);
                finish();
            }
        });


//        functionCallingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,Function_Calling.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        saveNumbrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Save_Number_List.class);
                startActivity(intent);
                finish();
            }
        });



        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Auto_Short_Codes_List.class);
                startActivity(intent);
                finish();
            }
        });



        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Are you sure want to Refresh Your Database ??");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myHelper.setAllDatabase();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CategoryList.class);
                startActivity(intent);
                finish();
            }
        });


        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MessageIbox.class);
                startActivity(intent);
                finish();
            }
        });

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Destination_Number.class);
                startActivity(intent);
                finish();
            }
        });

//        preBuiltBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,Incoming_String.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        shortCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ShortCodes.class);
                startActivity(intent);
                finish();
            }
        });





    }



    private void getActiveNumber() {
        SQLiteDatabase database=myHelper.getReadableDatabase();

        String query="Select PHONE_NUMBER from DESTINATION_NUMBER where status=1";
        Cursor c = database.rawQuery( query,null);
        if (c.moveToFirst()) {
            String activeNumber= c.getString(0);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("Destination_Number", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Destination_Number",activeNumber);
            editor.commit();
        }

    }
}
