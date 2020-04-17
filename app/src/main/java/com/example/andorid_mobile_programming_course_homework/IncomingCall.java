package com.example.andorid_mobile_programming_course_homework;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class IncomingCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            showToast(context,"Call started...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            showToast(context,"Call ended...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            String Number  = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            showToast(context,"Incoming call from "+Number);
            File file = new File("/sdcard/demo/infos.txt");
            try {
                FileOutputStream fileinput = new FileOutputStream(file);
                PrintStream printstream = new PrintStream(fileinput);
                printstream.print(Number+"\n");
                fileinput.close();
            } catch (Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    void showToast(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}