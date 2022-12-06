package com.example.b07projectapp;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

public class AdminPresenter implements Control.Presenter{
    private Control.Model model;
    private Control.View view;
    public static boolean valid;

    public AdminPresenter(Control.Model model, Control.View view){
        this.model=model;
        this.view=view;
    }

    @Override
    public void checkLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.equals("") || password.equals("")){
            view.displayMessage("Fields cannot be empty!");
        }
        else {
            AdminLoginModel adminLoginModel = new AdminLoginModel(username, password, view, FirebaseDatabase.getInstance());
            adminLoginModel.check(username, password, view);

            if (valid){
                view.displayMessage("Login successful!");
                view.loginToProgram();
            } else {
                view.displayMessage("Incorrect username or password");
            }

        }

    }
}
