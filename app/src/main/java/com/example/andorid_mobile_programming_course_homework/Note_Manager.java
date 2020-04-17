package com.example.andorid_mobile_programming_course_homework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Note_Manager extends Activity {
    RecyclerView.LayoutManager layoutManager ;
    RecyclerView recyclerView ;
    ArrayList<Note> notes = new ArrayList<>();
    Button new_note ;
    EditText title ;
    public  static Adapter_Note adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.note_menu);


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
    private void set_definations()  {
        recyclerView = findViewById(R.id.recyler_notes_titles);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        new_note = findViewById(R.id.note_new_note);
        title = findViewById(R.id.note_title);
        Intent intent = getIntent();

        final String str_username = intent.getStringExtra("USERNAME");
        new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                if (is_unique_and_valid(str_username,title.getText().toString())){
                    String title_name = title.getText().toString().replace("\n"," ");
                    Note note = new Note(str_username+"_"+title.getText().toString()+".ya",
                            str_username,title_name,"",currentTime.toString());
                    notes.add(note);
                    try {
                        FileOutputStream fos = getApplicationContext().openFileOutput(note.text_name, Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(note);
                        os.close();
                        fos.close();
                        Intent intent = new Intent(getApplicationContext(),Note_edit.class);
                        intent.putExtra("NOTES", notes);
                        intent.putExtra("pos",notes.size()-1);
                        intent.putExtra("USERNAME",str_username);
                        startActivity(intent);
                    }catch (Exception e ){
                        e.printStackTrace();
                    }
                }

            }

            private boolean is_unique_and_valid(String str_username, String title) {
                if (title.equals("")) {
                    Toast.makeText(getApplicationContext(),"Başlık boş bırakılamaz",Toast.LENGTH_SHORT).show();
                    return  false;
                }
                for(Note note : notes){
                    if(note.getOwner() == str_username && note.getTitle() == title){
                        Toast.makeText(getApplicationContext(),"Bu başlık daha önce girilmiş",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return true;
            }
        });



        //Note note = new Note("yusuf_selamlar.ya","yusuf","selamlar canım", "Selammmm",);
        /*
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("yusuf_selamlar.ya", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(note);
            os.close();
            fos.close();
        }catch (Exception e ){
            e.printStackTrace();
        }*/
        File []files= getFilesDir().listFiles();
        for(File file : files ){
            try{
                String filename  =file.getAbsolutePath();
                String [] names = filename.split("/");
                filename = names[names.length-1];
                Log.i("File " , filename);
                if (filename.startsWith(str_username) && filename.endsWith(".ya")) {
                    FileInputStream fis = getApplicationContext().openFileInput(filename);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    Note note1 = (Note) is.readObject();
                    notes.add(note1);
                }
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        Log.i("Büyüklük" , String.valueOf(notes.size()));
        adapter = new Adapter_Note(getApplicationContext(),notes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setData(notes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

