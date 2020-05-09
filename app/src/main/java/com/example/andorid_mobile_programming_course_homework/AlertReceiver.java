package com.example.andorid_mobile_programming_course_homework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Çalıyor", Toast.LENGTH_SHORT).show();
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtoneSound = RingtoneManager.getRingtone(context, ringtoneUri);
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