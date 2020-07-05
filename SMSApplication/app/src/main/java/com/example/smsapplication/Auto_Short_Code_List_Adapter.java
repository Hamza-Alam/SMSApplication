package com.example.smsapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Auto_Short_Code_List_Adapter extends RecyclerView.Adapter<Auto_Short_Code_List_Adapter.ViewHolder> {


    private ArrayList<Auto_Short_Code_List_Model> auto_short_code_list_models;
    private Context context;
    MyHelper myHelper;

    public Auto_Short_Code_List_Adapter(Context context,ArrayList<Auto_Short_Code_List_Model> auto_short_code_list_models) {
        this.auto_short_code_list_models = auto_short_code_list_models;
        this.context = context;
        myHelper=new MyHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_short_code_list_custom_layout,null,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;

    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView id;
        public TextView name;
        public TextView sampleSring;
        public TextView buildString;
        public TextView number;
        public TextView recivedNumber;
        public TextView simValue;
        public TextView functionality;
        public Button morebtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.codeID);
            name=itemView.findViewById(R.id.name);
            sampleSring=itemView.findViewById(R.id.sampleString);
            buildString=itemView.findViewById(R.id.buildString);
            number=itemView.findViewById(R.id.number);
            recivedNumber=itemView.findViewById(R.id.rcvNumber);
            simValue=itemView.findViewById(R.id.simVal);
            functionality=itemView.findViewById(R.id.func);
            morebtn=itemView.findViewById(R.id.more);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            Auto_Short_Code_List_Model listCode=auto_short_code_list_models.get(position);
            holder.id.setText(listCode.getId());
            holder.name.setText(listCode.getName());
            holder.sampleSring.setText(listCode.getSampleString());
            holder.buildString.setText(listCode.getBuildString());
            holder.number.setText(listCode.getNumber());
            holder.recivedNumber.setText(listCode.getRecivedRecipientNumber());
            holder.simValue.setText(listCode.getSimValue());
            holder.functionality.setText(listCode.getFunctionality());



        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.morebtn);
                popup.getMenuInflater()
                        .inflate(R.menu.edit_delete, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.edit:
                                String id= String.valueOf(holder.id.getText());
                                Intent intent=new Intent(context,Edit_Add_Auto_Short_Code.class);
                                intent.putExtra("ID", holder.id.getText());
                                intent.putExtra("name", holder.name.getText());
                                intent.putExtra("sampleSring", holder.sampleSring.getText());
                                intent.putExtra("buildString", holder.buildString.getText());
                                intent.putExtra("number",holder.number.getText());
                                intent.putExtra("reciveNumber",holder.recivedNumber.getText());
                                intent.putExtra("simValue",holder.simValue.getText());
                                intent.putExtra("functionality",holder.functionality.getText());
                                intent.putExtra("Check","Edit");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                break;

                            case R.id.del:
                                boolean result=myHelper.deleteAutoShortCode(holder.id.getText());

                                if(result==false)
                                {
                                    Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
                                }
                                Intent intent1=new Intent(context,Auto_Short_Codes_List.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent1);
                        }
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return auto_short_code_list_models.size();
    }



}
