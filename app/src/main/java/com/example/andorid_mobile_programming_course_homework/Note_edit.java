package com.example.andorid_mobile_programming_course_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Note_edit extends AppCompatActivity {
    EditText title;
    EditText context ;
    ArrayList<Note> notes;
    Button save  ;
    int pos ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_note_edit);
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
        title = findViewById(R.id.note_edit_title);
        context = findViewById(R.id.note_edit_context);
        Bundle extras = getIntent().getExtras();
        this.notes = (ArrayList<Note>) extras.getSerializable("NOTES");

        this.pos = extras.getInt("pos");
        title.setText(notes.get(pos).getTitle());
        context.setText(notes.get(pos).getContext());
        save = findViewById(R.id.note_edit_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("as",context.getText().toString());
                    notes.get(pos).setContext(context.getText().toString());
                    notes.get(pos).setTitle(title.getText().toString());
                    Note_Manager.adapter.setData(notes) ;
                    FileOutputStream fos = getApplicationContext().openFileOutput(notes.get(pos).getText_name(), Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(notes.get(pos));
                    os.close();
                    fos.close();
                    Intent i = new Intent(getApplicationContext(),Note_Manager.class);
                    i.putExtra("USERNAME",notes.get(pos).getOwner());
                    Toast.makeText(getApplicationContext(),"Not Kaydedildi",Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }catch (Exception e ){
                    e.printStackTrace();
                }


            }
        });
    }
}
