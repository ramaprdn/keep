package com.example.ramapradana.keep;

/**
 * Created by Evan Saragih on 11/19/2018.
 */

public class Friend {

    private String Name;
    private String Email;
    private int Photo;

    public Friend() {
    }

    public Friend(String name, String email, int photo) {
        Name = name;
        Email = email;
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public int getPhoto() {
        return Photo;
    }


    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String email) {
        Email = email;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }
}
