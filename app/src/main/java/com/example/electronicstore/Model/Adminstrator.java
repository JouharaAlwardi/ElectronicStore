package com.example.electronicstore.Model;

import android.text.TextUtils;

public class Adminstrator implements IUser {
    public String name, username, email, phoneNo, password;


    public Adminstrator() {
    }
    public Adminstrator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Adminstrator(String name, String username, String email, String phoneNo, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isValid() {
        if (getPassword().length() < 6) {

            return 0;

        }
        if (validatePasswrod(getPassword()) == true) {

            return 1;

        }
        if (TextUtils.isEmpty(getEmail())) {

            return 2;


        }
        if (TextUtils.isEmpty(getPassword())) {

            return 3;

        }

        else
            return -1;
    }



    public boolean validatePasswrod(String pas) {
        boolean valid = true;

        for (int i = 0; i < pas.length(); i++) {

            char ch = pas.charAt(i);
            if (ch == ' ') {

                valid = true;
                break;

            } else {

                valid = false;

            }

        }
        return valid;

    }

    public void setPassword(String password) {
        this.password = password;
    }
}
