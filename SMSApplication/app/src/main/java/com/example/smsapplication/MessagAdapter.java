package com.example.smsapplication;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessagAdapter extends RecyclerView.Adapter<MessagAdapter.MessageViewHolder> {

    Resources resources;
    private ArrayList<MessageModel> messageModels;
    private ArrayList<MessageModel> messageModelsfilter;
    private Context context;

    public MessagAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
        resources = context.getResources();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        MessagAdapter.MessageViewHolder messageViewHolder=new MessagAdapter.MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        final MessageModel messageModel=messageModels.get(position);
        holder.address.setText(messageModel.getMessage());
        holder.body.setText(messageModel.getBody());
        MessageIbox.makeTextViewResizable(holder.body, 3, "See More", true);
        String imag_path=messageModel.getType();
        int res=resources.getIdentifier(imag_path,"drawable","com.example.smsapplication");
        holder.emoji.setImageResource(res);
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        ConstraintLayout row_linearlayout;
        public TextView id;
        public TextView body;
        public TextView date;
        public TextView address;
        public ImageView emoji;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            address=itemView.findViewById(R.id.message);
            body=itemView.findViewById(R.id.body);
            emoji=itemView.findViewById(R.id.typeMessage);

        }
    }
}
