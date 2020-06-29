package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Function_Calling extends AppCompatActivity {

    String incoming_String="*45*123*921*500*2334*03119301092#";
    int countStar=0;
    MyHelper myHelper;
    ArrayList<ShortCode_Model> returnResult;
    String[] star;
    String splict_star;
    String finalString,finalID;
    ArrayList<String> forwardString;
    String forward_String=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function__calling);
        myHelper = new MyHelper(this);
        forwardString = new ArrayList<>();



        String sub_string = incoming_String.substring(0, 4);
        System.out.println("First four Character :" + sub_string);

        for (int i=0;i<incoming_String.length();i++)
        {
            if(incoming_String.charAt(i)=='*')
            {
                countStar++;
            }
        }
        System.out.println("Number of * Incoming String :" + countStar);


        System.out.println("Incoming String :" + incoming_String);
        returnResult = new ArrayList<ShortCode_Model>();
        returnResult = myHelper.getAllCodeIfExist(sub_string);

        /************** Count Number Of Star to Fetching Record *******************/

        for (int i=0;i<returnResult.size();i++)
        {
            splict_star=returnResult.get(i).codeString;
            char c = '*';
            int countData = countChar(splict_star, c);
            if(countData==countStar)
            {
               finalString=returnResult.get(i).codeString;
               finalID=returnResult.get(i).id;
               forwardString = myHelper.getForwardString(finalID);
                if (forwardString.size() > 0) {
                    forward_String = forwardString.get(0);

                    System.out.println("Final String : " + forward_String);

                }

            }

        }


    }
    public int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }
}
