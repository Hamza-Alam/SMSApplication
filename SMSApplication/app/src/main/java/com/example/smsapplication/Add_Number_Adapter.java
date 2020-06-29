package com.example.smsapplication;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Add_Number_Adapter extends RecyclerView.Adapter<Add_Number_Adapter.ViewHolder> {


    private ArrayList<Add_Number_Model> listItems;
    private Context context;
    MyHelper myHelper;

    public Add_Number_Adapter(ArrayList<Add_Number_Model> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        myHelper=new MyHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_number_list_layout,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        Add_Number_Adapter.ViewHolder viewHolder=new Add_Number_Adapter.ViewHolder(view);

       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Add_Number_Model add_number_model=listItems.get(position);

        holder.numberID.setText(add_number_model.getNumberID());
        holder.numName.setText(add_number_model.getNumberName());
        holder.number.setText(add_number_model.getNumber());

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
                                String id= String.valueOf(holder.numberID.getText());
                                Intent intent=new Intent(context,Add_Number.class);
                                intent.putExtra("numberID", holder.numberID.getText());
                                intent.putExtra("numName", holder.numName.getText());
                                intent.putExtra("number",holder.number.getText());
                                intent.putExtra("Check","Edit");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                break;


                            case R.id.del:
                                boolean result=myHelper.deleteRecipientNumber(holder.numberID.getText());

                                if(result==false)
                                {
                                    Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
                                }
                                Intent intent1=new Intent(context,Save_Number_List.class);
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
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView numberID;
        public TextView numName;
        public TextView number;
        public Button morebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numberID=(TextView) itemView.findViewById(R.id.numID);
            numName=(TextView) itemView.findViewById(R.id.numname);
            number=(TextView) itemView.findViewById(R.id.number);
            morebtn=(Button) itemView.findViewById(R.id.more);

            Log.d("Number Name ID :",numName.toString());
            Log.d("Number Id :",number.toString());
        }
    }
}
