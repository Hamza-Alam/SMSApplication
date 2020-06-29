package com.example.smsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShortCode_Adapter extends RecyclerView.Adapter<ShortCode_Adapter.shortViewHolder> {

    private ArrayList<ShortCode_Model> shortCodeModels;
    private Context context;
    MyHelper myHelper;


    public ShortCode_Adapter(ArrayList<ShortCode_Model> shortCodeModels, Context context) {
        this.shortCodeModels = shortCodeModels;
        this.context = context;
        myHelper=new MyHelper(context);
    }

    public static class shortViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout row_linearlayout;
        public TextView codeId;
        public TextView codeName;
        public TextView codeString;
        public Button morebtn;



        public shortViewHolder(@NonNull View itemView) {
            super(itemView);
            row_linearlayout=(ConstraintLayout)itemView.findViewById(R.id.codelistData);
            codeId=itemView.findViewById(R.id.codeID);
            codeName=itemView.findViewById(R.id.codeName);
            codeString=itemView.findViewById(R.id.codeString);
            morebtn=itemView.findViewById(R.id.more);
        }
    }

    @NonNull
    @Override
    public shortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.short_code_row,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        shortViewHolder viewHolder=new shortViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final shortViewHolder holder, int position) {

        final ShortCode_Model current_item=shortCodeModels.get(position);
        holder.codeId.setText(current_item.getId());
        holder.codeName.setText(current_item.getCodeName());
        holder.codeString.setText(current_item.getCodeString());

        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(context,Edit_Add_Short_Codes.class);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editIntent.putExtra("codeName",current_item.getCodeName());
                editIntent.putExtra("codeId",holder.codeId.getText());
                editIntent.putExtra("codeString",holder.codeString.getText());
                editIntent.putExtra("Check","Update");
                context.startActivity(editIntent);

            }
        });




        holder.morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.morebtn);
                popup.getMenuInflater()
                        .inflate(R.menu.edit, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.del:

                                boolean result=myHelper.deleteCode(holder.codeId.getText());

                                if(result==false)
                                {
                                    Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
                                }
                                Intent intent1=new Intent(context,ShortCodes.class);
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
        return shortCodeModels.size();
    }
}
