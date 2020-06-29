package com.example.smsapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Save_Number_List extends AppCompatActivity {

    Button back;
    private RecyclerView numberRecyler;
    private RecyclerView.Adapter numAdapter;
    ArrayList<Add_Number_Model> add_number_model;
    private RecyclerView.LayoutManager layoutManager;
    MyHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save__number__list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        myHelper=new MyHelper(this);



        numberRecyler=findViewById(R.id.my_recycler_view_save_number_list);
        numberRecyler.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        back=findViewById(R.id.backBtn);





        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        add_number_model=new ArrayList<>();
        add_number_model= (ArrayList<Add_Number_Model>) myHelper.getAllNumber();
        numAdapter=new Add_Number_Adapter(add_number_model,getApplicationContext());
        numberRecyler.setLayoutManager(layoutManager);
//        numberRecyler.addItemDecoration(dividerItemDecoration);
        numberRecyler.setAdapter(numAdapter);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Save_Number_List.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Save_Number_List.this,Add_Number.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
