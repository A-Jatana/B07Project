package com.example.b07projectapp;

public interface Control{
    public interface Model {
        public boolean isFound(String username, String password);

        void isFound2(String username, String password, Control.View view);
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
