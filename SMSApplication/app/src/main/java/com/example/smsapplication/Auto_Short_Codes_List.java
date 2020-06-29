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

public class Auto_Short_Codes_List extends AppCompatActivity {

    Button back;
    RecyclerView codeRecycler;
    ArrayList<Auto_Short_Code_List_Model> auto_short_code;
    private Auto_Short_Code_List_Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    MyHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto__short__codes__list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        myHelper=new MyHelper(this);


        back=findViewById(R.id.backBtn);
        codeRecycler=findViewById(R.id.my_code_recycler_view);
        codeRecycler.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);


        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        auto_short_code=new ArrayList<>();
        auto_short_code= (ArrayList<Auto_Short_Code_List_Model>) myHelper.getAllAutoShortCode();
        mAdapter=new Auto_Short_Code_List_Adapter(getApplicationContext(),auto_short_code);
        codeRecycler.setLayoutManager(layoutManager);
        codeRecycler.addItemDecoration(dividerItemDecoration);
        codeRecycler.setAdapter(mAdapter);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Auto_Short_Codes_List.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Auto_Short_Codes_List.this,Edit_Add_Auto_Short_Code.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
