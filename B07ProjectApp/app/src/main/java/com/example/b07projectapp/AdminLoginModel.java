package com.example.b07projectapp;

import android.util.Log;
import android.os.Handler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLoginModel extends Login implements Control.Model{
    boolean found;
    //private static FirebaseDatabase dm = FirebaseDatabase.getInstance();
    private static FirebaseDatabase dm;
    private static DatabaseReference dRef;
    private String username;
    private String password;
    private Control.View view;
    private boolean valid;
    public AdminLoginModel(){

    }
    public AdminLoginModel(String username, String password, Control.View view, FirebaseDatabase fb) {
        this.dm = fb;
        this.username = username;
        this.password = password;
        this.view = view;
    }

    @Override
    public void isFound(String username, String password, Control.View view) {
        ModelDatabase modelDatabase = new ModelDatabase(username, password, "admin");
        modelDatabase.search(new ModelDatabase.modelCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    //view.displayMessage("Login successful!");
                    //view.loginToProgram();
                    //valid = true;
                }
                else {
                    //view.displayMessage("Incorrect username or password");
                }
            }
        });
    }

    public boolean validLogin(String username, String password, Control.View view){

        AdminLoginModel adminLoginModel = new AdminLoginModel(username, password, view, FirebaseDatabase.getInstance());
        adminLoginModel.check(username, password, view);

        isFound(username, password, view);
        //check(username, password, view);
        ModelDatabase modelDatabase = new ModelDatabase(username, password, "admin");
        valid = modelDatabase.inDatabase();
        return valid;
    }

    void check(String password, String username, Control.View view) {
        ModelDatabase modelDatabase = new ModelDatabase(username, password, "admin");
        modelDatabase.search(new ModelDatabase.modelCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    Control.View.setAdminLogin();
                }

            }
        });
    }




    @Override
    void login() {

    }
}
