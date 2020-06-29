package com.example.smsapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Categort_RecyclerView_Adapter extends RecyclerView.Adapter<Categort_RecyclerView_Adapter.CategoryViewHolder> {

    private ArrayList<CategoryModel> categoryModels;
    private Context context;
    MyHelper myHelper;

    public Categort_RecyclerView_Adapter(ArrayList<CategoryModel> shortCodeModels, Context context) {
        this.categoryModels = shortCodeModels;
        this.context = context;
        myHelper=new MyHelper(context);
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        public TextView id;
        public TextView name;
        public Button moreBtn;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

           id=itemView.findViewById(R.id.catID);
           name=itemView.findViewById(R.id.catName);
           moreBtn=itemView.findViewById(R.id.more);
        }
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        Categort_RecyclerView_Adapter.CategoryViewHolder viewHolder=new Categort_RecyclerView_Adapter.CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {

        CategoryModel currentItem=categoryModels.get(position);
        holder.id.setText(String.valueOf(currentItem.getId()));
        holder.name.setText(currentItem.getCategoryName());

        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(context, holder.moreBtn);
                popup.getMenuInflater()
                        .inflate(R.menu.edit_delete, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.edit:

                                Intent intent=new Intent(context,AddCategory.class);
                                intent.putExtra("CategoryName", holder.name.getText());
                                intent.putExtra("CatID",holder.id.getText());
                                intent.putExtra("Check","Edit");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                break;
                            case R.id.del:
                               boolean result=myHelper.deleteCategory(holder.id.getText());
                                if(result==false)
                                {
                                    Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
                                }
                                Intent intent1=new Intent(context,CategoryList.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent1);

                        }
                        return true;
                    }
                });
            }
        });
    }

    private void deleteCategory(CharSequence text, View view, DialogInterface dialog) {
        boolean result=myHelper.deleteCategory(text);
        if(result==false)
        {
            Toast.makeText(context,"Faield !",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,"Sucessfully Delete !",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(context,CategoryList.class);
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }



}
