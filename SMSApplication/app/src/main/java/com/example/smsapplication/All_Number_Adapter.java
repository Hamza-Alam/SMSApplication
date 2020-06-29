package com.example.smsapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class All_Number_Adapter extends ArrayAdapter {
    private ArrayList<All_Number_Model> spinnerAdpaters;
    private Context context;

    public All_Number_Adapter(Context context, ArrayList<All_Number_Model> objects) {
        super(context, 0, objects);

        this.spinnerAdpaters=objects;
        this.context=context;
    }

    private static class NumberSpinerData
    {
        public TextView id;
        public TextView number;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NumberSpinerData numberSpinerData=new NumberSpinerData();
        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.number_spinner_layout, parent, false);
            numberSpinerData.id=(TextView) convertView.findViewById(R.id.numberID);
            numberSpinerData.number=(TextView) convertView.findViewById(R.id.number);
            convertView.setTag(numberSpinerData);

        }
        else
        {
            numberSpinerData = (NumberSpinerData) convertView.getTag();
        }
        All_Number_Model listFolder=spinnerAdpaters.get(position);
        numberSpinerData.id.setText(listFolder.getId());
        numberSpinerData.number.setText(listFolder.getNumber());
        return convertView;
    }
}
