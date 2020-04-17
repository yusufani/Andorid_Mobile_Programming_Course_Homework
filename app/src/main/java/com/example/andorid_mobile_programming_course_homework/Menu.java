package com.example.andorid_mobile_programming_course_homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.util.Calendar;


public class Menu extends AppCompatActivity {
    ImageView mail ;
    ImageView people ;
    ImageView settings ;
    ImageView sensor ;
    ImageView notes ;
    IncomingSms incomingSms = new IncomingSms();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_menu);




        set_definition();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_PHONE_STATE},1);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECEIVE_SMS},1);
        }

    }

    private void set_theme() {
        Intent intent = getIntent();
        String str_username  = intent.getStringExtra("USERNAME");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(str_username, Context.MODE_PRIVATE);
        int val_mode = sharedPreferences.getInt(str_username+"_mode",1);
        Log.i("val_mode",String.valueOf(val_mode));
        if (val_mode == 1 ){
            setTheme(R.style.darkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
    }

    private void set_definition(){
        mail = findViewById(R.id.image_mail);
        people = findViewById(R.id.image_people);
        settings = findViewById(R.id.image_settings);
        sensor = findViewById(R.id.image_sensor);
        notes = findViewById(R.id.image_notes);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Mail.class);
                Intent oldintent = getIntent();
                String username  = oldintent.getStringExtra("USERNAME");
                intent.putExtra("USERNAME",username);
                startActivity(intent);
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),People.class);
                Intent oldintent = getIntent();
                String username  = oldintent.getStringExtra("USERNAME");
                intent.putExtra("USERNAME",username);
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                Intent oldintent = getIntent();
                String username  = oldintent.getStringExtra("USERNAME");
                Log.i("Mneu ", username);
                intent.putExtra("USERNAME",username);
                startActivity(intent );

            }
        });
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SensorActivity.class);
                Intent oldintent = getIntent();
                String username  = oldintent.getStringExtra("USERNAME");
                intent .putExtra("USERNAME",username);
                startActivity(intent);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Note_Manager.class);
                Intent oldintent = getIntent();
                String username  = oldintent.getStringExtra("USERNAME");
                intent .putExtra("USERNAME",username);
                startActivity(intent);
            }
        });
    }
}
