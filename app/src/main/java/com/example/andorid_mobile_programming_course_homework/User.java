package com.example.andorid_mobile_programming_course_homework;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    final String username;
        private String password ;
        private int imageID ;
    public User(String user_name,String password,int imageID) {
        this.username = user_name ;
        this.password = password;
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public static ArrayList<User> getAllUserInfo(Context context){
        ArrayList<User> users= new ArrayList<>();
        File[]files= context.getFilesDir().listFiles();
        for(File file : files ){
            try{
                String filename  =file.getAbsolutePath();
                String [] names = filename.split("/");
                filename = names[names.length-1];
                Log.i("User: " , filename);
                if (filename.endsWith(".user")) {
                    filename.replace("user","");
                    FileInputStream fis = context.openFileInput(filename);
                    ObjectInputStream is = new ObjectInputStream(fis);
                    User user = (User) is.readObject();
                    users.add(user);
                }
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        return users;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
