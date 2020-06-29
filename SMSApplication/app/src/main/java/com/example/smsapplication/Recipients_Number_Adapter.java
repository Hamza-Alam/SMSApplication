package com.example.smsapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Recipients_Number_Adapter extends ArrayAdapter {

    private ArrayList<Recipients_Number_Model> spinnerAdpaters;
    private Context context;


    public Recipients_Number_Adapter(Context context, ArrayList<Recipients_Number_Model> objects) {
        super(context, 0, objects);

        this.spinnerAdpaters=objects;
        this.context=context;
    }

    private static class NumberSpinData
    {
        public TextView id;
        public TextView number;
        public Button morebtn;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NumberSpinData numberSpinData=new NumberSpinData();
        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.customspinnerlayout, parent, false);
            numberSpinData.id=(TextView) convertView.findViewById(R.id.codeId);
            numberSpinData.number=(TextView) convertView.findViewById(R.id.codeName);

            convertView.setTag(numberSpinData);
        }
        else
        {
            numberSpinData = (NumberSpinData) convertView.getTag();
        }
        Recipients_Number_Model listNumber=spinnerAdpaters.get(position);
        numberSpinData.id.setText(String.valueOf(listNumber.getId()));
        numberSpinData.number.setText(String.valueOf(listNumber.getRecipNumber()));


        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
