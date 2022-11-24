package com.example.b07projectapp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager {

    private static FirebaseDatabase database;
    private static DatabaseReference dRef;

    /**
     * WANT TO ADD:
     * Let search() return 1 or 0 depending whether it found the user
     *
     * @param username user username
     * @param password user password
     * @param type type of user
     */
    protected static void search(String username, String password, String type) {
        database = FirebaseDatabase.getInstance();

        // Points dRef to the "type" (admin, student, or course) (look in Firebase)
        dRef = database.getReference().child(type);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Status", "Sign Up Success");
                // snapshot.getChildren points towards the stuff inside STUDENTHASHCODE
                for(DataSnapshot ds : snapshot.getChildren()) {

                    // Get the value of username & password
                    if (ds.child("password").getValue().toString().equals(password) &&
                        ds.child("username").getValue().toString().equals(username)) {
                    } else { //Incorrect username or password

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });
    }

    /**
     * WANT TO ADD:
     * Use search() to verify if user already exists
     *
     * @param username user username
     * @param password user password
     */
    protected static void add(String username, String password) {
        database = FirebaseDatabase.getInstance();

        // Points dRef to "student"
        dRef = database.getReference().child("student");

        // Notice how it says SingleValueEvent - yeah it will loop infinitely without it
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Status", "Log In Success");

                // This is where search() should be used to check if something already exists or not

                // We should eventually depend on User objects
                Student student = new Student(username, password);

                HashMap<String, String> studentMap = new HashMap<>();

                studentMap.put("username", username);
                studentMap.put("password", password);

                dRef.push().setValue(studentMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });
    }
}
