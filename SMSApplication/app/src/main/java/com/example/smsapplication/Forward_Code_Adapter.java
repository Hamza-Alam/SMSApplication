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

public class Forward_Code_Adapter extends RecyclerView.Adapter<Forward_Code_Adapter.ForwardCode> {

    private ArrayList<Forward_Short_code_Model> shortCodeModels;
    private Context context;
    MyHelper myHelper;

    public Forward_Code_Adapter(ArrayList<Forward_Short_code_Model> shortCodeModels, Context context) {
        this.shortCodeModels = shortCodeModels;
        this.context = context;
        myHelper=new MyHelper(context);
    }


    public static class ForwardCode extends RecyclerView.ViewHolder {
        public TextView codeId,codeName,codeString,forwardString;
        public Button moreBtn;
        ConstraintLayout row_linearlayout;
        public ForwardCode(@NonNull View itemView) {
            super(itemView);
            row_linearlayout=(ConstraintLayout)itemView.findViewById(R.id.forwarcodeList);
            codeId=itemView.findViewById(R.id.codeID);
            codeName=itemView.findViewById(R.id.codeName);
            codeString=itemView.findViewById(R.id.codeString);
            forwardString=itemView.findViewById(R.id.forwardString);
            moreBtn=itemView.findViewById(R.id.more);


        }
    }

    @NonNull
    @Override
    public ForwardCode onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.forward_code_row,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        Forward_Code_Adapter.ForwardCode viewHolder=new Forward_Code_Adapter.ForwardCode(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ForwardCode holder, int position) {

        final Forward_Short_code_Model current_item=shortCodeModels.get(position);
        holder.codeId.setText(current_item.getId());
        holder.codeName.setText(current_item.getCodeName());
        holder.codeString.setText(current_item.getCodeString());
        holder.forwardString.setText(current_item.getForwardString());

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.moreBtn);
                popup.getMenuInflater()
                        .inflate(R.menu.edit, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.del:

                                boolean result=myHelper.deleteForwardCode(holder.codeId.getText());

                                if(result==false)
                                {
                                    Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
                                }
                                Intent intent1=new Intent(context,List_Forward_Code.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent1);
                        }
                        return true;
                    }
                });
            }
        });



        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(context,Short_Code_Forward.class);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editIntent.putExtra("codeName",current_item.getCodeName());
                editIntent.putExtra("codeId",holder.codeId.getText());
                editIntent.putExtra("codeString",holder.codeString.getText());
                editIntent.putExtra("forwardString",holder.forwardString.getText());
                editIntent.putExtra("Check","Update");
                context.startActivity(editIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return shortCodeModels.size();
    }


}
