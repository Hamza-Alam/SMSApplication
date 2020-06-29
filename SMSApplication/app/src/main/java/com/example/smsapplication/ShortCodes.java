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

public class ShortCodes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<ShortCode_Model> codeModels;
    private RecyclerView.LayoutManager layoutManager;
    MyHelper myHelper;
    Button back;


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ShortCodes.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_codes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        myHelper=new MyHelper(this);


        recyclerView=findViewById(R.id.my_recycler_view_short_code);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        back=findViewById(R.id.backBtn);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShortCodes.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        codeModels=new ArrayList<>();
        codeModels= (ArrayList<ShortCode_Model>) myHelper.getAllCodes();
        mAdapter=new ShortCode_Adapter(codeModels,getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mAdapter);





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShortCodes.this,Edit_Add_Short_Codes.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
