package com.example.andorid_mobile_programming_course_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import java.util.Random;

public class Download extends AppCompatActivity {
    ImageView imageView;

    ProgressBar progressBar ;
    Button button;
    int current_progress = 0 ;
    TextView progress_text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_download);
        // Ses için ring manager ile çıkar
        set_definations();
    }

    private void set_definations() {
        imageView = findViewById(R.id.download_image);
        imageView.setVisibility(View.GONE);
        progressBar = findViewById(R.id.download_progress_bar);
        button = findViewById(R.id.download_download_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.GONE);

                new BackgroundTask().execute((Void) null);
            }
        });
        progress_text = findViewById(R.id.download_progress_text);
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

    public class BackgroundTask extends AsyncTask<Void, Integer, Void> {
        Random random = new Random();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
            current_progress =  0;

        }

        @Override
        protected Void doInBackground(Void... params) {
            while ( current_progress < 100) {
                try {
                    publishProgress(random.nextInt(10));
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            current_progress += values[0];
            progressBar.setProgress(current_progress);
            if (current_progress>100)
                current_progress=100;
            progress_text.setText(current_progress+"/100");
            //textView.setText(currentProgress);
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final Ringtone ringtoneSound = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);

            if (ringtoneSound != null) {
                imageView.setVisibility(View.VISIBLE);
                ringtoneSound.play();

                Handler h =new Handler() ;
                h.postDelayed(new Runnable() {
                    public void run() {
                        ringtoneSound.stop();
                    }

                }, 5000);
            }
        }

    }


}

