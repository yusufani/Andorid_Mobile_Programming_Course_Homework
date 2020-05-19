package com.example.andorid_mobile_programming_course_homework;
import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.app.PendingIntent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class Sit_Counter extends AppCompatActivity {
    ActivityRecognitionClient mActivityRecognitionClient;
    private int old_state =3;
    public static final String INTENT_ACTION = "com.example.andorid_mobile_programming_course_homework.ACTION_PROCESS_ACTIVITY_TRANSITIONS";
    TextView text ;
    private static final String TAG = "TransitionTest";
//Define an ActivityRecognitionClient//
    BroadcastReceiver broadcastReceiver;
    private  int STILL_COUNTER;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        super.onCreate(savedInstanceState);
        set_theme();
        setContentView(R.layout.activity_sit__counter);
        text = findViewById(R.id.still_counter);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (ActivityRecognitionResult.hasResult(intent)) {
//If data is available, then extract the ActivityRecognitionResult from the Intent//
                    ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
                    DetectedActivity mostProbableActivity
                            = result.getMostProbableActivity();
                    //Get the confidence percentage//
                    int confidence = mostProbableActivity.getConfidence();
                    //Get the activity type/
                    int activityType = mostProbableActivity.getType();
                    String acti ="HAREKETSIZ DURUMDA";
                    if (activityType != 3 ){
                        acti ="HAREKETLI DURUMDA";
                        if (old_state!=activityType)
                            STILL_COUNTER++;
                    }
                    old_state = activityType;
                    //Toast.makeText(context,"Activity Type " + activityType + "Confidence"+ confidence , Toast.LENGTH_SHORT).show();
                    text.setText("Aktivite Tipi: " +acti + "\nGüvenilirlik: "+confidence +"\n"+"SAYAC : "+STILL_COUNTER);
                }
            }
        };
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACTIVITY_RECOGNITION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                            1);
                }

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            requestForUpdates();
        }
        //mActivityRecognitionClient = new ActivityRecognitionClient(this);
        //Toast.makeText(getApplicationContext(),"sa,",Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkIfAlreadyHavePermission();
        }else{
            requestForUpdates();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(INTENT_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }
    private void checkIfAlreadyHavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            requestForUpdates();
        } else {
            requestPermissions();
        }
    }
    private void requestPermissions(){
        ActivityCompat.requestPermissions(Sit_Counter.this,
                new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                1);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //requestForUpdates();
            } else {
                Toast.makeText(Sit_Counter.this, "Recognition permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestForUpdates(){

        //Intent intent = new Intent(this, broadcastReceiver.getClass());
        Intent intent = new Intent();
        intent.setAction(Sit_Counter.INTENT_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Task<Void> task = mActivityRecognitionClient
                .requestActivityUpdates(1, pendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        // Handle success
                        //Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_LONG).show();
                        Log.i(TAG, "\n\nTransitions API was successfully registered.\n\n");
                    }
                }
        );
        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Handle error
                        Toast.makeText(getApplicationContext(),"Fail Because"+e.getMessage() , Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Transitions API could not be registered: " + e);
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
