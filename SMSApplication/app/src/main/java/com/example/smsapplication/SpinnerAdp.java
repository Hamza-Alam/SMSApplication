package com.example.smsapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SpinnerAdp{

    int id;
    String Name;

    public SpinnerAdp(int id, String name) {
        this.id = id;
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}


class spinner extends ArrayAdapter<SpinnerAdp>
{
    private ArrayList<SpinnerAdp> spinnerAdpaters;
    private Context context;

    public spinner(Context context, ArrayList<SpinnerAdp> objects) {
        super(context, 0, objects);

        this.spinnerAdpaters=objects;
        this.context=context;
    }
    private static class spinnerData
    {
        public TextView codeid;
        public TextView code;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        spinnerData data = new spinnerData();

        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.customspinnerlayout, parent, false);
            data.codeid=(TextView) convertView.findViewById(R.id.codeId);
            data.code=(TextView) convertView.findViewById(R.id.codeName);
            convertView.setTag(data);
        }
        else
        {
            data = (spinnerData) convertView.getTag();
        }

        SpinnerAdp listFolder=spinnerAdpaters.get(position);
        data.codeid.setText(String.valueOf(listFolder.getId()));
        data.code.setText(listFolder.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}





