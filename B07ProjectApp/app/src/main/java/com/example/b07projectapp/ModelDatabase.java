package com.example.b07projectapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModelDatabase {
    private String username;
    private String password;
    private String type;
    private boolean valid;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef;

    public ModelDatabase(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    void search(modelCallback finishedCallback) {

        dRef = database.getReference().child(type);

        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    if (ds.child("username").getValue().toString().equals(username) && ds.child("password").getValue().toString().equals(password)) {
                        Log.i("inside", "true");
                        finishedCallback.callback(true);
                        valid = true;
                        return;
                    }
                }
                finishedCallback.callback(false);
                valid = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean inDatabase(){
        return valid;
    }
    public interface modelCallback {
        void callback(boolean data);
    }
}
