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

    public class CourseManager{
        private static FirebaseDatabase database = FirebaseDatabase.getInstance();
        private static DatabaseReference dRef;

        private String name, code, sessions, prereq, type;

        public CourseManager() {

        }

        public CourseManager(String name, String code, String sessions, String prereq, String type) {
            this.name = name;
            this.code = code;
            this.sessions = sessions;
            this.prereq = prereq;
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

//        protected void search(com.example.b07projectapp.DatabaseManager.SimpleCallback finishedCallback) {
//
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(type);
//
//            Query query_user = reference.orderByChild("username").equalTo(username);
//            query_user.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    // Checks if username exists
//                    if (snapshot.getValue() != null) {
//
//                        String DBpass = snapshot.child(username).child("password").getValue().toString();
//
//                        finishedCallback.callback(DBpass.equals(password));
//                    }
//                    else {
//                        finishedCallback.callback(false);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

        public interface SimpleCallback {
            void callback(boolean data);
        }
        protected void add(com.example.b07projectapp.CourseManager.addCallback finishedCallback) {

            // Points dRef to "student"
            dRef = database.getReference().child("course");

            // Notice how it says SingleValueEvent - yeah it will loop infinitely without it
            dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue().toString().equals(name)) {
                            finishedCallback.callback(false);
                            return;
                        }
                    }

                    // We should eventually depend on User objects
                    Course course = new Course(name, code, sessions, prereq);

                    dRef.child(name).setValue(course);
                    finishedCallback.callback(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("DATABASE ERROR");
                }
            });
        }
        protected void addStudentCourse(com.example.b07projectapp.CourseManager.addCallback finishedCallback) {

            // Points dRef to "student"
            dRef = database.getReference().child("student").child(StudentCourses.getStudentName()).child("course");

            // Notice how it says SingleValueEvent - yeah it will loop infinitely without it
            dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getValue().toString().equals(name)) {
                            finishedCallback.callback(false);
                            return;
                        }
                    }

                    // We should eventually depend on User objects
                    Course course = new Course(name, code, sessions, prereq);

                    dRef.child(name).setValue(course);
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

        protected static void generateCourseList (){
            dRef = database.getReference().child("course");
            //ArrayList of the form < <"Course Code", <"Session 1", "Session 2",...>, <"Prereq1","Prereq2",...>>, <...>, <...>>
            //ArrayList<ArrayList<ArrayList<String>>> finalCourseList = new ArrayList<ArrayList<ArrayList<String>>>();
            int index = 0;
            if (dRef!=null) {
                dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String course = ds.child("courseCode").getValue().toString();
                            String sessionsOffered = ds.child("offeringSessions").getValue().toString();
                            String prerequisites = ds.child("prerequisites").getValue().toString();
                            CourseList.addCourse(course, sessionsOffered, prerequisites);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        protected void generateStudentCourseList (String studentName) {
            dRef = database.getReference().child("student").child(studentName).child("course");
            //ArrayList of the form < <"Course Code", <"Session 1", "Session 2",...>, <"Prereq1","Prereq2",...>>, <...>, <...>>
            //ArrayList<ArrayList<ArrayList<String>>> finalCourseList = new ArrayList<ArrayList<ArrayList<String>>>();
            int index = 0;
            if (dRef != null){
                dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String course = ds.child("courseCode").getValue().toString();
                            String sessionsOffered = ds.child("offeringSessions").getValue().toString();
                            String prerequisites = ds.child("prerequisites").getValue().toString();
                            StudentCourses.addCourse(course, sessionsOffered, prerequisites);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        protected void getStudentName(String username, String password){
            dRef = database.getReference().child("student");
            String name;
            //ArrayList of the form < <"Course Code", <"Session 1", "Session 2",...>, <"Prereq1","Prereq2",...>>, <...>, <...>>
            //ArrayList<ArrayList<ArrayList<String>>> finalCourseList = new ArrayList<ArrayList<ArrayList<String>>>();
            int index = 0;

            if (dRef!=null) {
                dRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.child("username").equals(username) && ds.child("password").equals(password)) {
                                StudentCourses.setStudentName(ds.getKey());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }


}
