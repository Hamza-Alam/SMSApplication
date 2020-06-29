package com.example.smsapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter {
    private ArrayList<CategoryModel> spinnerAdpaters;
    private Context context;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> objects) {
        super(context, 0, objects);

        this.spinnerAdpaters=objects;
        this.context=context;
    }

    private static class CatspinnerData
    {
        public TextView id;
        public TextView catName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        CatspinnerData catspinnerData=new CatspinnerData();

        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.customspinnerlayout, parent, false);
            catspinnerData.id=(TextView) convertView.findViewById(R.id.codeId);
            catspinnerData.catName=(TextView) convertView.findViewById(R.id.codeName);
            convertView.setTag(catspinnerData);
        }

        else
        {
            catspinnerData = (CatspinnerData) convertView.getTag();
        }
        CategoryModel listFolder=spinnerAdpaters.get(position);
        catspinnerData.id.setText(String.valueOf(listFolder.getId()));
        catspinnerData.catName.setText(listFolder.getCategoryName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
