package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StudentChooseCoursesTimeline extends Fragment {

    DatabaseReference dRef;
    ArrayList<Course> list;
    RecyclerView recyclerView;
    private View courseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        courseView = inflater.inflate(R.layout.fragment_student_choose_courses_timeline, container, false);

        recyclerView = (RecyclerView) courseView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dRef = FirebaseDatabase.getInstance().getReference().child("student").child(StudentCourses.getStudentName()).child("course");
        return courseView;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_add = getView().findViewById(R.id.generate_timeline);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(StudentChooseCoursesTimeline.this)
                        .navigate(R.id.action_adminCourseList_to_adminAddCourse);
            }
        });

        Button btn_edit_delete = getView().findViewById(R.id.button_delete);
        btn_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(StudentChooseCoursesTimeline.this)
                        .navigate(R.id.action_adminCourseList_to_deleteCourse);
            }
        });

        Button update_course = getView().findViewById(R.id.button_update);
        update_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(StudentChooseCoursesTimeline.this)
                        .navigate(R.id.action_adminCourseList_to_adminUpdateCourse);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (dRef != null) {
            dRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            list.add(new Course(ds.child("courseName").getValue().toString(),
                                    ds.child("courseCode").getValue().toString(),
                                    ds.child("offeringSessions").getValue().toString(),
                                    ds.child("prerequisites").getValue().toString()));
                        }
                        Log.i("STATUS", list.get(0).getCourseName());
                        AdminCourseAdapter adapter = new AdminCourseAdapter(list);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}