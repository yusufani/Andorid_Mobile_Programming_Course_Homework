package com.example.andorid_mobile_programming_course_homework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class IncomingSms extends BroadcastReceiver {
    private final String FILE_NAME="SMS_logs.txt";
    // Get the object of SmsManager
    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    String log = "Message Sender Number : "+ senderNum + ", Message: " + message +" Date:" + new Date(System.currentTimeMillis()).toString()+"\n";
                    Log.i("SmsReceiver", log);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            log + " Saved to " + context.getFilesDir() + "/" + FILE_NAME, duration );
                    toast.show();

                    FileOutputStream fos = null;
                    try{
                        fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
                        fos.write(log.getBytes());
                    }
                     catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

}
