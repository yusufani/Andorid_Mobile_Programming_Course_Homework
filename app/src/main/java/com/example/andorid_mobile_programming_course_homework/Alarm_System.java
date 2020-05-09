package com.example.andorid_mobile_programming_course_homework;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alarm_System extends AppCompatActivity {
    Boolean is_active = false;
    TimePicker timePicker;
    Button toggleButton;
    //BackgroundTask backgroundTask ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_alarm__manager);
        setDefinations();
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
    private void setDefinations() {
        timePicker = findViewById(R.id.Alarm_Manager_time_picker);
        toggleButton = findViewById(R.id.Alarm_Manager_button);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_active) {

                    /*
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int mSec = calendar.get(Calendar.MILLISECOND);
                    mSec -= timePicker.getHour() * 60 * 60 * 1000 + timePicker.getMinute() * 60 * 1000;
                    //backgroundTask = (BackgroundTask) new BackgroundTask(mSec).execute();
                    */
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    c.set(Calendar.MINUTE, timePicker.getMinute());
                    c.set(Calendar.SECOND, 0);
                    if (c.getTimeInMillis() < System.currentTimeMillis()){
                        Toast.makeText(getApplicationContext(), "Alarm sadece ileri zamana ayarlanabilir" , Toast.LENGTH_SHORT).show();
                    }else{
                        updateTimeText(c);
                        startAlarm(c);
                        is_active=true;
                    }

                } else {
                    is_active=false;
                    cancelAlarm();
                }
            }
        });
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Aktif , zamanı : ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        toggleButton.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance()))
            c.add(Calendar.DATE, 1);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        toggleButton.setText("Alarm Kapalı");
    }

    /*
    public class BackgroundTask extends AsyncTask<Void, Integer, Void> {
        int alarm_time;
        public BackgroundTask(int  alarm_time) {
            this.alarm_time = alarm_time;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Handler h =new Handler() ;
            h.postDelayed(new Runnable() {
                public void run() {
                }

            }, alarm_time);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //textView.setText(currentProgress);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final Ringtone ringtoneSound = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);

            if (ringtoneSound != null) {
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
*/




}
