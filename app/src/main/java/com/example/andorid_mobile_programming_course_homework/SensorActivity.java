package com.example.andorid_mobile_programming_course_homework;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;


public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mLight;
    private Sensor accelator ;
    private TextView textView ;
    private TextView sensorlist;
    private TextView timer_text;
    private LinearLayout linearLayout;
    private SeekBar seekBar ;
    private Timer timer ;
    private TextView textView2;
    private final int epsilon = 70;
    private TextView textAcc;
    private int treshold = 30 ;
    private boolean is_active = false;
    private long starttime = 0;
    private final Handler h = new Handler(new Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;
            timer_text.setText(String.format("Sayaç : %d:%02d", minutes, seconds));
            if (seconds>5){
                Toast.makeText(getApplicationContext(),"Telefon yatay konumda 5 saniye hareketsiz durduğu için uygulama kapatılıyor",Toast.LENGTH_SHORT).show();
                timer.cancel();
                timer.purge();
                timer_text.setText("#EVDEKAL");
                Handler h =new Handler() ;
                h.postDelayed(new Runnable() {
                    public void run() {
                        finishAffinity();
                        System.exit(1);
                    }

                }, 2500);


            }


            return false;
        }
    });

    //tells handler to send a message
    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    };



    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor2);
        set_definations();

    }
    private void set_definations(){
        sensorlist = findViewById(R.id.sensorlist);
        timer_text = findViewById(R.id.timer);
        starttime = System.currentTimeMillis();
        textView = findViewById(R.id.sensor_text);
        textView2 = findViewById(R.id.sensor_treshold);
        textView2.setText("Işık Eşik Değeri: 30");
        textAcc = findViewById(R.id.sensor_accelator);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> all_sensors;
        all_sensors =sensorManager.getSensorList(Sensor.TYPE_ALL);
        int i = 1;
        sensorlist.setText("Sensor Listesi ("+all_sensors.size()+" Adet )\n");
        for(Sensor sensor : all_sensors){
            sensorlist.append(i+"-"+sensor.getName()+"\n");
            i++;
        }
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelator  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelator==null){
            Toast.makeText(getApplicationContext(),"İvme Ölçer sensörünüzde problem var ",Toast.LENGTH_SHORT).show();
        }
        linearLayout = findViewById(R.id.sensor_linear);
        seekBar = findViewById(R.id.sensor_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                treshold = progress;
                textView2.setText("Işık Eşik Değeri: "+treshold);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        Log.i("Ac",String.valueOf(accuracy));
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            Log.i("Ac",String.valueOf(lux ));
            textView.setText("Işık Sensor Değeri : " + String.valueOf(lux));
            if (lux<treshold){
                linearLayout.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
                textView2.setTextColor(Color.WHITE);
                textAcc.setTextColor(Color.WHITE);
                timer_text.setTextColor(Color.WHITE);
                sensorlist.setTextColor(Color.WHITE);
            }else{
                linearLayout.setBackgroundColor(Color.WHITE);
                timer_text.setTextColor(Color.BLACK);
                textView.setTextColor(Color.BLACK);
                textView2.setTextColor(Color.BLACK);
                textAcc.setTextColor(Color.BLACK);
                sensorlist.setTextColor(Color.BLACK);
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            int new_x = (int)(event.values[0]*(100.0)) , new_y =(int)(event.values[1]*(100.0) ), new_z = (int)(event.values[2]*(100.0)) ;
            textAcc.setText("X: "+ String.valueOf(new_x) + "  Y: " + String.valueOf(new_y) + "Z: "+String.valueOf(new_z));
            if (is_in_range(new_x,new_y,new_z)){
                //Toast.makeText(getApplicationContext(),"Range içinde",Toast.LENGTH_SHORT).show();
                if(! is_active) {
                    starttime = System.currentTimeMillis();
                    timer = new Timer();
                    //Toast.makeText(getApplicationContext(),"Yeni Timer Oluşturuluyor",Toast.LENGTH_SHORT).show();
                    timer.schedule(new firstTask(), 0, 500);
                    is_active = true;
                }
            }else {
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                    timer_text.setText("");
                    is_active=false;
                }
            }

        }
        // Do something with this sensor data.
    }

    private boolean is_in_range(int x, int y, int z) {
        x = Math.abs(x);
        y = Math.abs(y);
        z = Math.abs(z);
        return (x<epsilon && y<epsilon && z<epsilon+980 );
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelator, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
