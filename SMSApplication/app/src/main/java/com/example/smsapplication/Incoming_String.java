package com.example.smsapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Incoming_String extends AppCompatActivity {
    MyHelper myHelper;
    ArrayList<ShortCode_Model> returnResult;
    int countDatabase = 0, countReceive = 0;
    String[] reutrnsplict;
    String[] receiveSplict;
    String[] return_receving_splict;
    String[] countIndex;
    ArrayList<String> hashResult;
    String[] star;
    ArrayList<String> forwardString;
    String finalString, finalId;
    String[] finalString_without_star;
    String number, ammount, key;
    String forward_String;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming__string);
        myHelper = new MyHelper(this);
        context = Incoming_String.this;

        //-------------------------------- RECEIVE VALUE COUNT AND SHOW OUTPUT ----------------------------------//
        String receivingString = "*98*03331234567*56985*1000*65*001*98#";
        String sub_string = receivingString.substring(0, 3);
        System.out.println("First four Character :" + receivingString.substring(0, 3));
        receivingString.split("[*]");

        char rc = '*';
        int receiveCount = countChar(receivingString, rc);
        System.out.println("Receiving String Count :" + countChar(receivingString, rc));


        receiveSplict = receivingString.split("[*#]");
        String[] splictString = receivingString.split("[*]");

        returnResult = new ArrayList<ShortCode_Model>();

        forwardString = new ArrayList<>();

        returnResult = myHelper.getAllCodeIfExist(sub_string);

        for (int i = 0; i < returnResult.size(); i++) {
            String splict_star = returnResult.get(i).codeString;
            star = splict_star.split("[*]");
        }

        for (String s : star) {
            System.out.println("Return Splict" + s);
        }

        if (returnResult.size() > 0) {

            //-------------------------------- DATABASE VALUE SHOW ----------------------------------//
            for (int i = 0; i < returnResult.size(); i++) {
                System.out.println("Database String :" + returnResult.get(i).codeString);
            }
            //-------------------------------- DATABASE VALUE COUNT ----------------------------------//
            int countString = 0;
            for (int i = 0; i < returnResult.size(); i++) {

                String arrayIndex = returnResult.get(i).codeString;
                char c = '*';
                int countData = countChar(arrayIndex, c);
                if (countData == receiveCount) {
                    finalString = returnResult.get(i).codeString;
                    finalString_without_star = finalString.split("[*]");
                    finalId = returnResult.get(i).id;

                    forwardString = myHelper.getForwardString(finalId);
                    if (forwardString.size() > 0) {
                        forward_String = forwardString.get(i);
                        System.out.println("Final String : " + forward_String);
                    }

                }
                System.out.println("Final String Count :" + countChar(forward_String, c));
            }


            for (int i = 0; i < receiveSplict.length; i++) {

            }

            //-------------------------------- RECEVING VALUE SHOW ---------------------------------//
            System.out.println("............................... Receiving String ..................");
            for (String s : receiveSplict) {
                System.out.println("Receiving String >>>>>>>>>>" + s);
            }
            //-------------------------------- EXTRACTING VALUE ----------------------------------//
            for (int i = 0; i < finalString_without_star.length; i++) {

                for (int j = 0; j < receiveSplict.length; j++) {
                    if (finalString_without_star[j].equals("") && receiveSplict.equals("")) {

                    } else if (finalString_without_star[j].equals("{{number}}")) {
                        number = receiveSplict[j].toString();
                    } else if (finalString_without_star[j].equals("{{ammount}}") || finalString_without_star[j].equals("{{amount}}") || finalString_without_star[j].equals("{{Ammount}}") || finalString_without_star[j].equals("{{Amount}}")) {
                        ammount = receiveSplict[j].toString();
                    } else if (finalString_without_star[j].equals("{{key}}") || finalString_without_star[j].equals("{{Key}}")) {
                        key = receiveSplict[j].toString();
                    }

                }

            }
            System.out.println("Your number is : " + number);
            System.out.println("Your ammount is : " + ammount);
            System.out.println("Your key is : " + key);


            forward_String = forward_String.replace("{{number}}", number);
            forward_String = forward_String.replace("{{amount}}", ammount);
            forward_String = forward_String.replace("{{key}}", key);


            System.out.println("Your Final String Update  : " + forward_String);
            String ussdCode = "*" + "123" + Uri.encode("#");
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No Result Found",Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d("Code: ", String.valueOf(resultCode));
            if(data!=null) {
                if (resultCode == RESULT_OK) {
                    String dd = data.toString();
                    Log.d("Response: ", data.toString());
                } else {
                    Log.i("Error :", data.toString());
                }
            }
        }
    }

}
