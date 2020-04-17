package com.example.andorid_mobile_programming_course_homework;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;

public class Login extends Activity {
    EditText username;
    EditText password;
    Button sig_in;
    Button sign_up;
    LinearLayout linearLayout ;
    TextView fail_login_warning;
    private int number_of_wrong_login= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.darkTheme  );
        setContentView(R.layout.login_screen);

        define_and_set_listeners();
    }
    private void define_and_set_listeners(){
        username =  findViewById(R.id.username);
        password = findViewById(R.id.password);
        sig_in  = findViewById(R.id.sign_in);
        sign_up =  findViewById(R.id.sign_up);
        linearLayout = findViewById(R.id.linear_layout);
        sig_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_n = String.valueOf(username.getText());
                String pass = String.valueOf(password.getText());
                if ( !user_n.equals("") &&  !pass.equals("") ){
                    if (check_login(user_n,pass)){
                        Toast.makeText(getApplicationContext(),"Başarıyla giriş yapıldı.",Toast.LENGTH_SHORT).show();
                        Log.i("Basarili giris", "User "+username+"Şifre"+password+" Giriş yaptı" );

                        Intent intent = new Intent(getApplicationContext(),Menu.class);
                        intent.putExtra("USERNAME",user_n);
                        startActivity(intent);

                    }   else{
                        Toast.makeText(getApplicationContext(),"Username ve/veya Şifre hatalı",Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        number_of_wrong_login++;
                        if (number_of_wrong_login==1){
                            Log.i("s","Girdi");
                            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            fail_login_warning = new TextView(getApplicationContext());
                            fail_login_warning.setLayoutParams(lparams);
                            fail_login_warning.setTextColor(Color.parseColor("#fbc490"));
                            fail_login_warning.setGravity(Gravity.CENTER);
                            linearLayout.addView(fail_login_warning);
                        }
                        fail_login_warning.setText("Hatali giriş sayısı " + number_of_wrong_login + "/3");

                        if (number_of_wrong_login == 3 ){
                            Toast.makeText(getApplicationContext(),"3 Kere yanlış giriş yapıldığı için uygulama 5 saniye içinde  kapatılıyor!!!",Toast.LENGTH_LONG).show();
                            Handler h =new Handler() ;
                            h.postDelayed(new Runnable() {
                                public void run() {
                                   finish();
                                }

                            }, 5000);
                        }
                        }
                }

                else{
                    Toast.makeText(getApplicationContext(),"Username veya Şifre boş bırakılamaz!",Toast.LENGTH_SHORT).show();
                }
            }

        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.i("selamlar" , "selam");
                    String user_n = String.valueOf(username.getText());
                    if(!user_n.contains(" ") && !is_Exist(user_n)){
                        user_n.replace("\n","");
                        String pass = String.valueOf(password.getText());
                        User user = new User(user_n,pass,R.drawable.user);
                        FileOutputStream fos = getApplicationContext().openFileOutput(user.username+".user", Context.MODE_PRIVATE);
                        ObjectOutputStream os = new ObjectOutputStream(fos);
                        os.writeObject(user);
                        os.close();
                        fos.close();
                        Toast.makeText(getApplicationContext(),"Başarıyla kullanıcı oluşturuldu.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Menu.class);
                        intent.putExtra("USERNAME",user.username);
                        startActivity(intent);
                    }
                    else if(user_n.contains(" ")){
                        Toast.makeText(getApplicationContext(),"Username boşluk içeremez",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Username daha önce sistemde kayıtlıdır",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean check_login(String username, String password) {
        ArrayList<User> users = User.getAllUserInfo(getApplicationContext());
        for (User user : users) {
            if ( username.equals(user.getUsername()) &&  password.equals(user.getPassword()))
                return true;
        }
        return false;
    }
    private  boolean is_Exist(String username){
        ArrayList<User> users = User.getAllUserInfo(getApplicationContext());
        for (User user : users) {
            if ( username.equals(user.getUsername()))
                return true;
        }
        return false;
    }
}
