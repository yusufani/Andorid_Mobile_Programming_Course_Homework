package com.example.andorid_mobile_programming_course_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Mail extends AppCompatActivity {
    EditText target_mail ;
    EditText title ;
    EditText context ;
    Button attachment ;
    Button send_mail ;
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_mail);
        set_definition();
    }
    private void set_theme() {
        Intent intent = getIntent();
        String str_username  = intent.getStringExtra("USERNAME");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(str_username, Context.MODE_PRIVATE);
        int val_mode = sharedPreferences.getInt(str_username+"_mode",1);
        if (val_mode == 1){
            setTheme(R.style.darkTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
    }
    private void set_definition(){
        target_mail = findViewById(R.id.target_mail);
        title = findViewById(R.id.title);
        context = findViewById(R.id.context);
        attachment = findViewById(R.id.attachment);
        send_mail= findViewById(R.id.send_button);
        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseDocuments();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    this.uri = uri;
                    Log.i("uri",String.valueOf(uri));
                    Toast.makeText(getApplicationContext(),"File"+uri +"eklendi",Toast.LENGTH_SHORT ).show();
                }
                break;
        }

    }


    private void browseDocuments()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 100);
    }
    public void sendMail( )
    {
        try {
            String email = target_mail.getText().toString();
            String subject = title.getText().toString();
            String message = context.getText().toString();
            Uri uri = this.uri;
            final Intent emailIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                    new String[] { email });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    subject);
            if (uri != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            }
            emailIntent
                    .putExtra(android.content.Intent.EXTRA_TEXT, message);
            this.startActivity(Intent.createChooser(emailIntent,
                    "Sending email..."));

        } catch (Throwable t) {
            Toast.makeText(this,
                    "Request failed try again: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }
}
