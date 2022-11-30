package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StudentCourseList extends AppCompatActivity {

    CourseList student_course_list; //using a function, we need to store the student's list of courses from the database here
    RecyclerView recyclerView;
    //DatabaseReference database;
    StudentCourseListAdapter studentCourseListAdapter;
    ArrayList<Course> list;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_course_list);

        recyclerView = findViewById(R.id.studentCourseList);
        setRecylerView();

    }
    private void setRecylerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentCourseListAdapter = new StudentCourseListAdapter(this,getList());
        recyclerView.setAdapter(studentCourseListAdapter);
    }

    private ArrayList<Course> getList(){

        ArrayList<Course> course_list = new ArrayList<>();
        /*for(int i = 0; i<student_course_list.courseCode.size(); i++){

            String offering_sessions = "";
            String prereqs = "";
            for(int j = 0; j<student_course_list.sessions.get(i).size(); i++){
                offering_sessions += student_course_list.sessions.get(i).get(j);
            }
            for(int j = 0; j<student_course_list.prerequisites.get(i).size(); i++){
                prereqs += student_course_list.prerequisites.get(i).get(j);
            }
            course_list.add(new Course("", student_course_list.courseCode.get(i), offering_sessions,prereqs));

        }*/

        course_list.add(new Course("Intro", "CSCB07", "Winter", "A48"));
        return course_list;
    }

     /*   database = FirebaseDatabase.getInstance().getReference("Users"); //??
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        studentCourseListAdapter = new StudentCourseListAdapter(this,list);
        recyclerView.setAdapter(studentCourseListAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                     Course course = dataSnapshot.getValue(Course.class);
                     list.add(course);
                 }
                 studentCourseListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }*/



}