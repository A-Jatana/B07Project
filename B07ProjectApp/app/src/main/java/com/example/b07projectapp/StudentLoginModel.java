package com.example.b07projectapp;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentLoginModel extends Login implements Control.Model{
    boolean found;
    //private static FirebaseDatabase dm = FirebaseDatabase.getInstance();
    private static FirebaseDatabase dm;
    private static DatabaseReference dRef;
    private String username;
    private String password;
    private Control.View view;
    private boolean valid;
    public StudentLoginModel(){

    }
    public StudentLoginModel(String username, String password, Control.View view, FirebaseDatabase fb) {
        this.dm = fb;
        this.username = username;
        this.password = password;
        this.view = view;
    }

    @Override
    public void isFound(String username, String password, Control.View view) {
        ModelDatabase modelDatabase = new ModelDatabase(username, password, "student");
        modelDatabase.search(new ModelDatabase.modelCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    view.displayMessage("Login successful!");
                    view.loginToProgram();
                    valid = true;
                }
                else {
                    view.displayMessage("Incorrect username or password");
                }
            }
        });
    }

    @Override
    public boolean validLogin(String username, String password, Control.View view) {
        StudentLoginModel studentLoginModel = new StudentLoginModel(username, password, view, FirebaseDatabase.getInstance());
        studentLoginModel.check(username, password, view);
        isFound(username, password, view);
        check(username, password, view);
        return valid;
    }

    void check(String password, String username, Control.View view) {
        ModelDatabase modelDatabase = new ModelDatabase(username, password, "student");
        modelDatabase.search(new ModelDatabase.modelCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    view.displayMessage("Login successful!");
                    view.loginToProgram();
                    valid = true;
                }
                else {
                    view.displayMessage("Incorrect username or password");
                    Log.i("CHECK", "Username: " + username + ", password: " + password);
                }
            }
        });
    }




    @Override
    void login() {

    }
}
