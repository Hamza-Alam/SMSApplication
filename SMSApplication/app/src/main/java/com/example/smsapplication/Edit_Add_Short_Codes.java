package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit_Add_Short_Codes extends AppCompatActivity {

    String codeID,codeName,codeString,Check;
    Button addBtn;
    EditText shortName,shortCode;
    MyHelper myHelper;
    Button back;
    ArrayList<CategoryModel> arrayList;
    CategoryAdapter categoryAdapter;
    Spinner catSpinner;
    int catID;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__add__short__codes);
        myHelper=new MyHelper(this);
        back=findViewById(R.id.backBtn);
//        catSpinner=findViewById(R.id.categorySpinner);
//        catSpinner.setOnItemSelectedListener(this);

//        getAllCategory();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                startActivity(intent);
                finish();
            }
        });




        addBtn=findViewById(R.id.addShortCode);
        shortName=findViewById(R.id.nameEdit);
        shortCode=findViewById(R.id.codeEdit);


        Intent intent=getIntent();
        codeID=intent.getStringExtra("codeId");
        codeName=intent.getStringExtra("codeName");
        codeString=intent.getStringExtra("codeString");
        Check=intent.getStringExtra("Check");


        if(Check!=null)
        {
            shortName.setText(codeName);
            shortCode.setText(codeString);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String codename=shortName.getText().toString();
                    String codestring=shortCode.getText().toString();

                    if(codestring.contains("{{number}}")) {
                        if (codestring.contains("{{amount}}"))
                        {
                            boolean result=myHelper.updateCode(codeID,shortName.getText().toString(),shortCode.getText().toString());
                            if(result==false)
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Faield Update Data !",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Sucess Update Data !",Toast.LENGTH_LONG).show();
                            }

                            Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                            intent.putExtra("Check","UpdateValues");
                            startActivity(intent);
                            finish();
                        }
                        else if(codestring.contains("{{ammount}}"))
                        {
                            boolean result=myHelper.updateCode(codeID,shortName.getText().toString(),shortCode.getText().toString());
                            if(result==false)
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Faield Update Data !",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Sucess Update Data !",Toast.LENGTH_LONG).show();
                            }

                            Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                            intent.putExtra("Check","UpdateValues");
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Your code String Incorrect !",Toast.LENGTH_LONG).show();
                        }
                    }

                    else if(codestring.contains("{{Number}}"))
                    {
                        if (codestring.contains("{{Amount}}"))
                        {
                            boolean result=myHelper.updateCode(codeID,shortName.getText().toString(),shortCode.getText().toString());
                            if(result==false)
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Faield Update Data !",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Sucess Update Data !",Toast.LENGTH_LONG).show();
                            }

                            Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                            intent.putExtra("Check","UpdateValues");
                            startActivity(intent);
                            finish();
                        }
                        else if(codestring.contains("{{Ammount}}"))
                        {
                            boolean result=myHelper.updateCode(codeID,shortName.getText().toString(),shortCode.getText().toString());
                            if(result==false)
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Faield Update Data !",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Edit_Add_Short_Codes.this,"Sucess Update Data !",Toast.LENGTH_LONG).show();
                            }

                            Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                            intent.putExtra("Check","UpdateValues");
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Your code String Incorrect !",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Your code String Incorrect !",Toast.LENGTH_LONG).show();
                    }


                }
            });
        }
        else
        {
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name=shortName.getText().toString();
                    String code=shortCode.getText().toString();

                    if(name!=null && code!=null)
                    {
                        addShortCode(name,code,catID);
                    }
                }
            });
        }

    }

//    private void getAllCategory() {
//        arrayList= myHelper.spinnerAllCategory();
//        categoryAdapter=new CategoryAdapter(Edit_Add_Short_Codes.this,arrayList);
//        catSpinner.setAdapter(categoryAdapter);
//
//    }

    private void updateCode() {


    }

    private void addShortCode(String name, String code, int catID) {

        if(code.contains("{{number}}"))
        {
            if(code.contains("{{amount}}"))
            {
                boolean result=myHelper.insertCode(name,code,catID);
                if(result==false)
                {
                    Toast.makeText(Edit_Add_Short_Codes.this,"Faield Inserted Code !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Edit_Add_Short_Codes.this,"SucessFul Inserted Code !",Toast.LENGTH_LONG).show();
                }
                Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Your code String Incorrect !",Toast.LENGTH_LONG).show();
            }
        }
        else if(code.contains("{{Number}}"))
        {
            if(code.contains("{{Amount}}"))
            {
                boolean result=myHelper.insertCode(name,code,catID);
                if(result==false)
                {
                    Toast.makeText(Edit_Add_Short_Codes.this,"Faield Inserted Code !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Edit_Add_Short_Codes.this,"SucessFul Inserted Code !",Toast.LENGTH_LONG).show();
                }
                Intent intent=new Intent(Edit_Add_Short_Codes.this,ShortCodes.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Your code String Incorrect !",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Your code String Incorrect !",Toast.LENGTH_LONG).show();
        }


    }


//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        catID=arrayList.get(i).id;
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}
