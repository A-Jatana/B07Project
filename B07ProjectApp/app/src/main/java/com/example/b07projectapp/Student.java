package com.example.b07projectapp;

public class Student extends User{

    public Student() {
    }
    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUser() {
        return username;
    }
    public String getUser(String username) {
        return this.username;
    }

    public String getPass() {
        return password;
    }
    public String getPass(String password) {
        return this.password;
    }
}
