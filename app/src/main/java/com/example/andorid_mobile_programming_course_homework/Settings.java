package com.example.andorid_mobile_programming_course_homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class Settings extends AppCompatActivity {
    TextView username;
    TextView age;
    TextView height;
    TextView weight ;
    RadioGroup sex ;
    RadioGroup mode;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        set_theme();
        setContentView(R.layout.activity_settings);
        set_definations();
        get_and_set_user_prefences();

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
    private void get_and_set_user_prefences() {
        Intent intent = getIntent();
        String str_username = intent.getStringExtra("USERNAME");
        Log.w("Username","sa_> " + str_username);
        username.setText(str_username);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(str_username, Context.MODE_PRIVATE);
        Log.i("Shar",String.valueOf(sharedPreferences));
        int val_age= sharedPreferences.getInt(str_username+"_age",-1);
        Float val_weight = sharedPreferences.getFloat(str_username+"_weight" , -1);
        Float val_height = sharedPreferences.getFloat(str_username+"_height", -1);
        int val_sex= sharedPreferences.getInt(str_username+"_sex",-1);
        int val_mode = sharedPreferences.getInt(str_username+"_mode",1);

        if (val_age != -1 ) {
            age.setText(String.valueOf(val_age));
        }
        if (val_weight != -1 ) {
            weight.setText(String.valueOf(val_weight));
        }
        if (val_height != -1 ) {
            height.setText(String.valueOf(val_height));
        }
        Log.i("DeğerLer",String.valueOf(val_sex +" " +val_mode ));
        if (val_sex != -1 && val_sex<2) {
            sex.getChildAt(val_sex).performClick();
        }
        mode.getChildAt(val_mode).performClick();


    }

    private void set_definations() {
        username = findViewById(R.id.settings_username);
        age = findViewById(R.id.settings_age);
        height =findViewById(R.id.settings_height);
        weight = findViewById(R.id.settings_weight);
        sex = findViewById(R.id.settings_sex);
        mode = findViewById(R.id.setting_mode);
        save = findViewById(R.id.settings_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i=0;
                try{
                    Intent intent = getIntent();
                    String str_username = intent.getStringExtra("USERNAME");
                    username.setText(str_username);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(str_username, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int val_age;
                    String a = age.getText().toString();
                    if (age.getText().toString().equals( "" )){
                        val_age=0;
                    }else{
                        val_age = Integer.valueOf(String.valueOf(age.getText().toString()));
                    }
                    editor.putInt(str_username+"_age", val_age);
                    i++;
                    float val_weight;
                    if(weight.getText().toString().equals( "" )){
                        val_weight  = Float.valueOf("0.0");
                    }
                    else{
                        val_weight = Float.valueOf(String.valueOf(weight.getText().toString()));
                    }
                    editor.putFloat(str_username+"_weight",val_weight);
                    i++;
                    float val_height ;
                    if(height.getText().toString().equals( "" ) ){
                        val_height = Float.valueOf("0.0");
                    }else{
                        val_height =  Float.valueOf(String.valueOf(height.getText().toString()));
                    }

                    editor.putFloat(str_username+"_height",val_height);
                    i++;
                    int val_sex = 0;
                    if ( sex.getChildAt(1).getId() == sex.getCheckedRadioButtonId() )
                        val_sex = 1;
                    editor.putInt(str_username+"_sex", val_sex);
                    i++;

                    int val_mode =0;
                    if ( mode.getChildAt(1).getId() == mode.getCheckedRadioButtonId() )
                        val_mode = 1;
                    Log.d("Vla mode " , String.valueOf(val_mode));
                    int old_val_mode = sharedPreferences.getInt(str_username+"_mode",1);
                    if (old_val_mode != val_mode || val_mode == 1 ) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        Toast.makeText(getApplicationContext(),"Mode değiştirildi.",Toast.LENGTH_SHORT).show();
                    }else if (old_val_mode != val_mode && val_mode==0 ){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        Toast.makeText(getApplicationContext(),"Mode değiştirildi.",Toast.LENGTH_SHORT).show();
                    }
                    editor.putInt(str_username+"_mode", val_mode);
                    i++;
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Ayarlar Kaydedildi",Toast.LENGTH_SHORT).show();
                    Intent newIntent = new Intent(getApplicationContext(),Menu.class);
                    Intent oldintent = getIntent();
                    String username  = oldintent.getStringExtra("USERNAME");
                    Log.i("Geçi",username);
                    newIntent.putExtra("USERNAME",username);
                    startActivity(newIntent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Tüm değerler doldurulmalıdır "+ i+". girdide hata var " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
