package com.example.b07projectapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager{
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef;

    private String username;
    private String password;
    private String type;

    public DatabaseManager() {}

    public DatabaseManager(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

//    public void test() {
//        dRef = database.getReference().child("student");
//        dRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    String name = task.getResult().getValue(String.class);
//                    callback.onResponse(name);
//                } else {
//                    Log.d(TAG, task.getException().getMessage());
//                }
//            }
//        });
//    }

//    protected void search(Context context, String username, String password, String type) {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(type);
//
//        Query query_user = reference.orderByChild("username").equalTo(username);
//        query_user.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//
//                    String DBpass = snapshot.child(username).child("password").getValue(String.class);
//
//                    if (DBpass.equals(password)) {
//
//                        String DBUser = snapshot.child(username).child("username").getValue(String.class);
//
//                        Log.i("STATUS", "BEFORE LOGIN");
//                        //Intent intent = new Intent(getApplicationContext(), AdminManagement.class);
//                        //startActivity(intent);
//                        Log.i("STATUS", "AFTER LOGIN");
//
//                        AdminLogin.check = 1;
//
//
//                    } else {
//                        Toast myToast = Toast.makeText(context, "Incorrect username or password. Please try again.", Toast.LENGTH_SHORT);
//                        myToast.show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    protected void search(searchCallback finishedCallback) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(type);

        Query query_user = reference.orderByChild("username").equalTo(username);
        query_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Checks if username exists
                if (snapshot.getValue() != null) {

                    String DBpass = snapshot.child(username).child("password").getValue().toString();

                    finishedCallback.callback(DBpass.equals(password));
                }
                else {
                    finishedCallback.callback(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface searchCallback {
        void callback(boolean data);
    }
    protected void add(addCallback finishedCallback) {

        // Points dRef to "student"
        dRef = database.getReference().child("student");

        // Notice how it says SingleValueEvent - yeah it will loop infinitely without it
        dRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getValue().toString().equals(username)) {
                        finishedCallback.callback(false);
                        return;
                    }
                }

                // We should eventually depend on User objects
                Student student = new Student(username, password);

                // why does this happen - the stuff below sets the keys to "pass" & "user"
                // instead of password & username
                // dRef.child(username).setValue(student)
                dRef.child(username).child("username").setValue(student.username);
                dRef.child(username).child("password").setValue(student.password);
                finishedCallback.callback(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });
    }
    public interface addCallback {
        void callback(boolean data);
    }
}
