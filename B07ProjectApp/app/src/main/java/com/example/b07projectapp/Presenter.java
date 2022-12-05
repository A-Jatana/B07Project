package com.example.b07projectapp;

public class Presenter implements Control.Presenter{
    private Control.Model model;
    private Control.View view;

    public Presenter (Control.Model model, Control.View view){
        this.model=model;
        this.view=view;
    }

    @Override
    public void checkLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.equals("") || password.equals("")){
            view.displayMessage("Fields cannot be empty!");
        } else if (model.isFound(username, password)){
            view.displayMessage("Login successful!");
        } else {
            view.displayMessage("Incorrect username or password");
        }
    }
}
