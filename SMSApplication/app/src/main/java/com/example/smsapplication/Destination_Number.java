package com.example.smsapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Destination_Number extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    ArrayList<DestinationClas> destinationClas;
    private RecyclerView.LayoutManager layoutManager;
    MyHelper myHelper;
    Button back;




    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Destination_Number.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination__number);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        myHelper=new MyHelper(this);

        recyclerView=findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        back=findViewById(R.id.backBtn);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Destination_Number.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        Intent intent=getIntent();
        String check=intent.getStringExtra("Check");
        if(check=="UpdateValues")
        {
            againRequestToDataBase();
        }
        else
        {
            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
            destinationClas=new ArrayList<>();
            destinationClas= (ArrayList<DestinationClas>) myHelper.getAllContacts();
            mAdapter=new DestinationAdapter(destinationClas,getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(mAdapter);
        }








        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Destination_Number.this,EditDestination.class);
                intent.putExtra("Check_Operation","Add");
                intent.putExtra("View", String.valueOf(view));
                startActivity(intent);

            }
        });
    }

    private void againRequestToDataBase() {
        destinationClas=new ArrayList<>();
        destinationClas= (ArrayList<DestinationClas>) myHelper.getAllContacts();
        mAdapter=new DestinationAdapter(destinationClas,getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

//

}
