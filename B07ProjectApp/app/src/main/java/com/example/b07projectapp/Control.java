package com.example.b07projectapp;

import com.google.firebase.database.FirebaseDatabase;

public interface Control{
    public interface Model {
        void isFound(String username, String password, Control.View view);
    }

    public interface View{
        public String getUsername();
        public String getPassword();
        public void displayMessage(String msg);
        public void loginToProgram();
    }

    public interface Presenter {
        public void checkLogin();
    }

}
