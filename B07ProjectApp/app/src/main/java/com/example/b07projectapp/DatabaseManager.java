package com.example.b07projectapp;

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

public class DatabaseManager extends Fragment {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef;

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

    /**
     * WANT TO ADD:
     * Let search() return 1 or 0 depending whether it found the user
     *
     * @param username user username
     * @param password user password
     * @param type type of user
     */
    protected static void search(Context context, Class c, String username, String password, String type) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(type);

        Query query_user = reference.orderByChild("username").equalTo(username);
        query_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String DBpass = snapshot.child(username).child("password").getValue(String.class);

                    if (DBpass.equals(password)) {

                        String DBUser = snapshot.child(username).child("username").getValue(String.class);

                        Intent intent = new Intent(context, c);
                        Log.i("STATUS", "BEFORE ACTIVITY");
                        context.startActivity(intent);
                        Log.i("STATUS", "AFTER ACTIVITY");


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//        // Points dRef to the "type" (admin, student, or course) (look in Firebase)
//        dRef = database.getReference().child(type);
//
//        dRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.i("Status", "Sign Up Success");
//                // snapshot.getChildren points towards the stuff inside STUDENTHASHCODE
//                if (snapshot.exists()) {
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//
//                        // Get the value of username & password
//                        if (ds.child("password").getValue().toString().equals(password) &&
//                                ds.child("username").getValue().toString().equals(username)) {
//                            AdminLogin.test = true;
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println("DATABASE ERROR");
//            }
//        });
    }




    /**
     * WANT TO ADD:
     * Use search() to verify if user already exists
     *
     * @param username user username
     * @param password user password
     */
    protected static void add(String username, String password) {

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

                dRef.child(username).setValue(student);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });
    }
}
