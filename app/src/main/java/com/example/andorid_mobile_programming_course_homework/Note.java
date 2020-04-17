package com.example.andorid_mobile_programming_course_homework;

import java.io.Serializable;

public  class Note implements Serializable {
    String text_name;
    String title;
    String owner;
    String Context;
String time ;
    public Note(String text_name, String owner ,String title, String context,String time) {
        this.text_name = text_name;
        this.title = title;
        this.Context = context;
        this.owner = owner;
        this.time= time;
    }

    public String getOwner() {
        return owner;
    }

    public String getTime() {
        return time;
    }

    public String getText_name() {
        return text_name;
    }

    public void setText_name(String text_name) {
        this.text_name = text_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }
}

