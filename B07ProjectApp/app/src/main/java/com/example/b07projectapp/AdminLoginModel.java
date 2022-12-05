package com.example.b07projectapp;

import android.widget.Toast;

public class AdminLoginModel extends Login implements Control.Model{
    boolean found = false;

    @Override
    public boolean isFound(String username, String password) {
        DatabaseManager dm = new DatabaseManager(username, password, "admin");

        dm.search(new DatabaseManager.searchCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    found = true;
                }
                else {
                    Toast myToast = Toast.makeText(getContext(), "Incorrect username or password. Please try again.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

        return found;
    }

    @Override
    void login() {

    }
}
