package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Number extends AppCompatActivity {


    EditText numberName;
    EditText numberTxt;
    Button addBtn,back;
    MyHelper myHelper;
    String editFlag=null,numName=null,number=null,numid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__number);
        myHelper=new MyHelper(this);


        numberName=findViewById(R.id.editnumberNumber);
        numberTxt=findViewById(R.id.editNumber);
        addBtn=findViewById(R.id.addSaveNumber);
        back=findViewById(R.id.backBtn);

        Intent intent=getIntent();
        numid=intent.getStringExtra("numberID");
        numName=intent.getStringExtra("numName");
        number=intent.getStringExtra("number");
        editFlag=intent.getStringExtra("Check");

        if(numid!=null && numName!=null && number!=null){
            numberName.setText(numName.toString());
            numberTxt.setText(number.toString());
        }



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=numberName.getText().toString();
                String number=numberTxt.getText().toString();

                if(editFlag!=null)
                {
                    if(name.length()>0 && number.length()>0)
                    {
                        if(checkNumberDB(number,numid))
                        {
                            Toast.makeText(Add_Number.this, "Your Recipient Number Already Exist !", Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean result = myHelper.updateRecipientNumber(numid, name, number);
                            if (result == false) {
                                Toast.makeText(Add_Number.this, "Faield To Update Recipient Number !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Add_Number.this, "SucessFully Update Recipient Number !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Add_Number.this, Save_Number_List.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                    else {
                        Toast.makeText(Add_Number.this,"CareFully Recipient Number & Name",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    if (name.length()>0 && number.length()>0) {
                        boolean numberExist = myHelper.numberExist(number);
                        if (numberExist) {
                            Toast.makeText(Add_Number.this, "Your Recipient Number Already Exist !", Toast.LENGTH_LONG).show();
                        } else {
                            boolean result = myHelper.addNumber(name, number);
                            if (result == false) {
                                Toast.makeText(Add_Number.this, "Faield To Add Number !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Add_Number.this, "Sucess Add Number !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Add_Number.this, Save_Number_List.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(Add_Number.this, "CareFully Recipient Number & Name", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Add_Number.this,Save_Number_List.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private boolean checkNumberDB(String number, String numid) {
        boolean submitResult=myHelper.checkUpdateNumberDB(numid,number);
        if(submitResult){
            return true;
        }
        else{
            return false;
        }
    }
}
