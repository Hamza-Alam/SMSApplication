package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {


    Button categoryBtn;
    Button back;
    TextView categoryName;
    EditText networkNumber;
    MyHelper myHelper;
    String id;
    String check=null;
    String text=null,number=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        categoryName=findViewById(R.id.nameCategory);
        networkNumber=findViewById(R.id.possibleNetworkNumber);
        categoryBtn=findViewById(R.id.addShortCodeCategory);
        myHelper=new MyHelper(this);
        back=findViewById(R.id.backBtn);


        Intent intent=getIntent();
        String name=intent.getStringExtra("CategoryName");
        String codeNum=intent.getStringExtra("NetworkNumber");
        id=intent.getStringExtra("CatID");
        check=intent.getStringExtra("Check");

        if(check!=null)
        {
            categoryName.setText(name);
            networkNumber.setText(codeNum);
            categoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!categoryName.getText().equals("") && !networkNumber.getText().equals(""))
                    {
                        boolean result=myHelper.updateCategory(categoryName.getText().toString(),id,networkNumber.getText().toString());
                        if(result==false)
                        {
                            Toast.makeText(AddCategory.this,"Faield !",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AddCategory.this,"Sucessfully Update !",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(AddCategory.this,CategoryList.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });

        }
        else
        {
            categoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text=categoryName.getText().toString();
                    number=networkNumber.getText().toString();
                    if(!text.equals("") && !number.equals(""))
                    {
                        boolean result=myHelper.addCategory(categoryName.getText().toString(),number);
                        if(result==false)
                        {
                            Toast.makeText(AddCategory.this,"Faield !",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AddCategory.this,"Sucessfully Add !",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(AddCategory.this,CategoryList.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                    else
                    {
                        Toast.makeText(AddCategory.this,"Please Enter Category First !",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddCategory.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
