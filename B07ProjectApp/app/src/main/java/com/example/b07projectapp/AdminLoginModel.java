package com.example.b07projectapp;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLoginModel extends Login implements Control.Model{
    boolean found;
    private static FirebaseDatabase dm = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef;
    private String username;
    private String password;
    private Control.View view;

    public AdminLoginModel(){

    }
    public AdminLoginModel(String username, String password, Control.View view) {
        this.username = username;
        this.password = password;
        this.view = view;
    }

    @Override
    public void validLogin(String username, String password) {

        // Points dRef to "student"
        dRef = dm.getReference().child("admin");
/*
        // Notice how it says SingleValueEvent - yeah it will loop infinitely without it
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dm.search(new DatabaseManager.searchCallback()
                {
                    @Override
                    public void callback ( boolean data){
                        if (data) {
                            found = true;
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }

 */


        AdminModelDatabase adminModelDatabase = new AdminModelDatabase(username, password, "admin");
        adminModelDatabase.search(new AdminModelDatabase.adminModelCallback() {

            @Override
            public void callback(boolean data) {
                /*
                if (data) {

                }

                 */
            }
        });

    }

    @Override
    public void isFound2(String username, String password, Control.View view) {
        AdminModelDatabase adminModelDatabase = new AdminModelDatabase(username, password, "admin");
        adminModelDatabase.search(new AdminModelDatabase.adminModelCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    view.displayMessage("Login successful!");
                    view.loginToProgram();
                }
                else {
                    view.displayMessage("Incorrect username or password");
                }
            }
        });
    }

    void check(String password, String username, Control.View view) {
        AdminModelDatabase adminModelDatabase = new AdminModelDatabase(username, password, "admin");
        adminModelDatabase.search(new AdminModelDatabase.adminModelCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    Presenter.valid=true;
                    view.displayMessage("Login successful!");
                    view.loginToProgram();
                }
                else {
                    view.displayMessage("Incorrect username or password");
                }
            }
        });
    }




    @Override
    void login() {

    }
}
