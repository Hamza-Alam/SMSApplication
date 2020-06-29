package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Short_Code_Forward extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button back,saveBtn;
    EditText codename,codestring,codeforward;
    Spinner catSpinner;
    ArrayList<CategoryModel> arrayList;
    CategoryAdapter categoryAdapter;
    String Check;
    String codeID;
    String codeName=null;
    String codeCat=null;
    String codeString=null;
    String codeForward=null;
    String forwardString;
    MyHelper myHelper;
    int catID,count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short__code__forward);
        back=findViewById(R.id.backBtn);
        catSpinner=findViewById(R.id.categorySpinner);
        catSpinner.setOnItemSelectedListener(this);
        myHelper=new MyHelper(this);
        saveBtn=findViewById(R.id.addShortCode);
        codename=findViewById(R.id.nameEdit);
        codestring=findViewById(R.id.codeEdit);
        codeforward=findViewById(R.id.forwardEdit);

        getAllCategory();


        Intent intent=getIntent();
        codeID=intent.getStringExtra("codeId");
        codeName=intent.getStringExtra("codeName");
        codeString=intent.getStringExtra("codeString");
        forwardString=intent.getStringExtra("forwardString");
        Check=intent.getStringExtra("Check");





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Short_Code_Forward.this,List_Forward_Code.class);
                startActivity(intent);
                finish();
            }
        });


        if(Check!=null)
        {
            codename.setText(codeName);
            codestring.setText(codeString);
            codeforward.setText(forwardString);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    codeName=codename.getText().toString();
                    codeString=codestring.getText().toString();
                    codeForward=codeforward.getText().toString();


                    if(!codeName.equals("") && !codeString.equals("") && !codeForward.equals(""))
                    {
                        if(codeString.contains("{{key}}") && codeString.contains("{{number}}") && codeString.contains("{{amount}}"))
                        {
                            if(codeForward.contains("{{key}}") && codeForward.contains("{{number}}") && codeForward.contains("{{amount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if (codeString.contains("{{Key}}") && codeString.contains("{{Number}}") && codeString.contains("{{Amount}}"))
                        {
                            if(codeForward.contains("{{Key}}") && codeForward.contains("{{Number}}") && codeForward.contains("{{Amount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(codeString.contains("{{key}}") && codeString.contains("{{number}}") && codeString.contains("{{ammount}}"))
                        {
                            if(codeForward.contains("{{key}}") && codeForward.contains("{{number}}") && codeForward.contains("{{ammount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if (codeString.contains("{{Key}}") && codeString.contains("{{Number}}") && codeString.contains("{{Ammount}}"))
                        {
                            if(codeForward.contains("{{Key}}") && codeForward.contains("{{Number}}") && codeForward.contains("{{Ammount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Short_Code_Forward.this,"Your code string are Incorrect !",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Your All Requiremnts !", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    codeName=codename.getText().toString();
                    codeString=codestring.getText().toString();
                    codeForward=codeforward.getText().toString();

                    if(!codeName.equals("") && !codeString.equals("") && !codeForward.equals(""))
                    {
                        if(codeString.contains("{{key}}") && codeString.contains("{{number}}") && codeString.contains("{{amount}}"))
                        {
                            if(codeForward.contains("{{key}}") && codeForward.contains("{{number}}") && codeForward.contains("{{amount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if (codeString.contains("{{Key}}") && codeString.contains("{{Number}}") && codeString.contains("{{Amount}}"))
                        {
                            if(codeForward.contains("{{Key}}") && codeForward.contains("{{Number}}") && codeForward.contains("{{Amount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(codeString.contains("{{key}}") && codeString.contains("{{number}}") && codeString.contains("{{ammount}}"))
                        {
                            if(codeForward.contains("{{key}}") && codeForward.contains("{{number}}") && codeForward.contains("{{ammount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if (codeString.contains("{{Key}}") && codeString.contains("{{Number}}") && codeString.contains("{{Ammount}}"))
                        {
                            if(codeForward.contains("{{Key}}") && codeForward.contains("{{Number}}") && codeForward.contains("{{Ammount}}"))
                            {
                                String[] splictstring=codeString.split("\\*");
                                String indexCode=splictstring[1];
                                checkalreadyCodeenter("*"+indexCode);
                            }
                            else {
                                Toast.makeText(Short_Code_Forward.this,"Your Forwarding String are not Match  !",Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Short_Code_Forward.this,"Your code string are Incorrect !",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Your All Requiremnts !", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void checkalreadyCodeenter(String indexCode) {
        if(Check!=null) {

            boolean updateData = myHelper.updateForwardCode(codeName, codeString, codeForward, Integer.parseInt(codeID));
            if (updateData == false) {
                Toast.makeText(Short_Code_Forward.this, "Faield Update Data !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Short_Code_Forward.this, "SucessFully Update Data !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Short_Code_Forward.this, List_Forward_Code.class);
                startActivity(intent);
                finish();
            }
        }
        else
        {
//            boolean result = myHelper.checkString(indexCode);
//            if (result == false) {
//                Toast.makeText(getApplicationContext(), "Your Code String Already Exist !", Toast.LENGTH_LONG).show();
//            } else {
            boolean addResult = myHelper.addForwarShortcode(codeName, codeString, codeForward, catID);
            if (addResult == false) {
                Toast.makeText(Short_Code_Forward.this, "Faield Inserted Data !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Short_Code_Forward.this, "SucessFully Inserted Data !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Short_Code_Forward.this, List_Forward_Code.class);
                startActivity(intent);
                finish();
                //}
            }
        }
    }


    private void getAllCategory() {
        arrayList= myHelper.spinnerAllCategory();
        categoryAdapter=new CategoryAdapter(Short_Code_Forward.this,arrayList);
        catSpinner.setAdapter(categoryAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        catID=arrayList.get(i).id;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
