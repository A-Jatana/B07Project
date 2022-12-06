package com.example.b07projectapp;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

public class Presenter implements Control.Presenter{
    private Control.Model model;
    private Control.View view;
    public static boolean valid;


    public Presenter(Control.Model model, Control.View view){
        this.model=model;
        this.view=view;
    }

    @Override
    public void checkLogin(){
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.equals("") || password.equals("")){
            view.displayMessage("Fields cannot be empty!");
        }
        else {
            if (model.validLogin(username, password, view)){
                view.displayMessage("Login successful!");
                view.loginToProgram();
            } else {
                view.displayMessage("Incorrect username or password");
            }
        }
//        else if (model.isFound(username, password)){
//            view.displayMessage("Login successful!");
//            view.loginToProgram();
//        } else {
//            view.displayMessage("Incorrect username or password");
//        }
    }
    public void checkLoginStudent(){
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.equals("") || password.equals("")){
            view.displayMessage("Fields cannot be empty!");
        }
        else {
            if (model.validLogin(username, password, view)){
                view.displayMessage("Login successful!");
                view.loginToProgram();
            } else {
                view.displayMessage("Incorrect username or password");
            }
        }
//        else if (model.isFound(username, password)){
//            view.displayMessage("Login successful!");
//            view.loginToProgram();
//        } else {
//            view.displayMessage("Incorrect username or password");
//        }
    }
}
