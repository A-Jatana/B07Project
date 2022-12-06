package com.example.b07projectapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginModel extends Login implements Control.Model{
    boolean found = false;
    private static FirebaseDatabase dm = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef;

    @Override
    public boolean isFound(String username, String password) {



        // Points dRef to "student"
        dRef = dm.getReference().child("student");
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


        // Notice how it says SingleValueEvent - yeah it will loop infinitely without it
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getValue().toString().equals(username) && ds.child("password").getValue().toString().equals(password)) {
                        found = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return found;
    }

    @Override
    void login() {

    }
}
