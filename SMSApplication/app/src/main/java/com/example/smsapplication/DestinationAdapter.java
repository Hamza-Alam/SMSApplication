package com.example.smsapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {
    int row_index=-1;
    private ArrayList<DestinationClas> destinationClas;
    private Context context;
    MyHelper myHelper;

    public DestinationAdapter(ArrayList<DestinationClas> destinationClas1, Context context) {
        this.context = context;
        destinationClas=destinationClas1;
        myHelper=new MyHelper(context);
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout row_linearlayout;
        public TextView numberDestination;
        public TextView numberID;
        public TextView status;
        public Button moreBtn;


        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            numberDestination=itemView.findViewById(R.id.number);
            row_linearlayout=(ConstraintLayout) itemView.findViewById(R.id.listData);
            numberID=itemView.findViewById(R.id.numberID);
            status=itemView.findViewById(R.id.status);
            moreBtn=itemView.findViewById(R.id.more);
        }
    }


    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_lyout,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        DestinationViewHolder destinationViewHolder=new DestinationViewHolder(view);
        return destinationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DestinationViewHolder holder, final int position) {

        final DestinationClas currentItem=destinationClas.get(position);
        holder.numberID.setText(currentItem.getNumberID());
        holder.numberDestination.setText(currentItem.getDestination_number());
        holder.status.setText(currentItem.getStatus());
        String value= (String) holder.status.getText();

        //Active Number BackGround Color Change

        if(value.equals("1"))
        {
            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ff2d9a59"));
            holder.numberID.setTextColor(Color.WHITE);
            holder.numberDestination.setTextColor(Color.WHITE);
            holder.status.setTextColor(Color.WHITE);
        }
        //ListView Set On click Listerner
        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(context,EditDestination.class);
                editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                editIntent.putExtra("destinationNumber",currentItem.getDestination_number());
                editIntent.putExtra("numberId",holder.numberID.getText());
                editIntent.putExtra("Check","Edit");
                context.startActivity(editIntent);
            }
        });


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

                                boolean result=myHelper.deleteNumber(holder.numberID.getText());

                                if(result==false)
                                {
                                    Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
                                }
                                Intent intent1=new Intent(context,Destination_Number.class);
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
        return destinationClas.size();
    }
}
