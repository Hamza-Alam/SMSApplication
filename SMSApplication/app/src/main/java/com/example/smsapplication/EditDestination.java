package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditDestination extends AppCompatActivity {

    String numberId,destinationNumber,Check,Check_Operation;
    EditText numberEdit;
    CheckBox checkNumber;
    Button addBtn;
    boolean checkValue=false;
    MyHelper myHelper;
    ArrayList<String>result;
    String resultquery="";
    View view;
    Button back;
    String text=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_destination);
        numberEdit=findViewById(R.id.numberEdit);
        checkNumber=findViewById(R.id.activecheck);
        addBtn=findViewById(R.id.addNumber);
        back=findViewById(R.id.backBtn);
        myHelper=new MyHelper(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditDestination.this,Destination_Number.class);
                startActivity(intent);
                finish();
            }
        });




        Intent intent=getIntent();
        numberId=intent.getStringExtra("numberId");
        destinationNumber=intent.getStringExtra("destinationNumber");
        Check=intent.getStringExtra("Check");
        Check_Operation=intent.getStringExtra("Check_Operation");
        if(Check_Operation!=null)
        {
            checkNumber.setVisibility(View.INVISIBLE);
        }
        else
        {
            checkNumber.setVisibility(View.VISIBLE);
        }
        if(Check!=null)
        {
            updateData();
        }
        else
        {
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   text=numberEdit.getText().toString();

                    if(!text.equals("")) {
                        boolean result = myHelper.insertData(text, checkValue);
                        if (result == false) {
                            Toast.makeText(EditDestination.this, "Faield Inserted Data !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditDestination.this, "SucessFul Inserted Data !", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(EditDestination.this, Destination_Number.class);
                        intent.putExtra("Check", "UpdateValues");
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditDestination.this, "Please Enter Your Destination Number !", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        checkNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SQLiteDatabase database=myHelper.getWritableDatabase();
                    String update_query="UPDATE DESTINATION_NUMBER SET status="+0+" WHERE id!="+numberId+"";
                    database.execSQL(update_query);
                    checkValue=true;


                } else {
                    checkValue=false;
                }
            }
        });


    }


    private void updateData() {

        SQLiteDatabase database=myHelper.getReadableDatabase();

        String query="Select status from DESTINATION_NUMBER where id="+numberId+"";
        Cursor c = database.rawQuery( query,null);
        if (c.moveToFirst()) {
            numberEdit.setText(destinationNumber);
            String checkValue1= c.getString(0);
            if(checkValue1.equals("1"))
            {
                checkNumber.setChecked(true);
            }
            c.close();
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Destination_Number", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
               boolean result=myHelper.updateNumber(numberId,numberEdit.getText().toString(),checkValue);
               if(result==false)
               {
                   Toast.makeText(EditDestination.this,"Faield Update Data !",Toast.LENGTH_LONG).show();
               }
               else
               {
                   Toast.makeText(EditDestination.this,"Sucess Update Data !",Toast.LENGTH_LONG).show();
                   editor.putString("Destination_Number", numberEdit.getText().toString()); // Storing string
                   editor.commit();
               }

               Intent intent=new Intent(EditDestination.this,Destination_Number.class);
               intent.putExtra("Check","UpdateValues");
               startActivity(intent);
               finish();
            }
        });


    }


}
