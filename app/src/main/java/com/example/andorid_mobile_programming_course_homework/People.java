package com.example.andorid_mobile_programming_course_homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class People extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager ;
    RecyclerView recyclerView ;
    ArrayList users ;
    Adapter_People adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_people);
        set_definations();

    }
    private void set_theme() {
        Intent intent = getIntent();
        String str_username  = intent.getStringExtra("USERNAME");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(str_username, Context.MODE_PRIVATE);
        int val_mode = sharedPreferences.getInt(str_username+"_mode",1);
        if (val_mode == 1 ){
            setTheme(R.style.darkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
    }
    private void set_definations() {
        recyclerView = findViewById(R.id.recylerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        users = User.getAllUserInfo(getApplicationContext());
        adapter = new Adapter_People(getApplicationContext(),users);
        recyclerView.setAdapter(adapter);
    }


}
