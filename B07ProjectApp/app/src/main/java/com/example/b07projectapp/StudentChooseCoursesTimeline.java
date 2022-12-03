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
import android.widget.Toast;

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
    ArrayList<String> coursesToTake;
    StudentCourseListAdapterCheckboxes adapter;
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

                ArrayList<Course> chosenCourses = adapter.getCheckList();
                if (chosenCourses.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Please choose at least one course!", Toast.LENGTH_SHORT);
                    myToast.show();
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i< chosenCourses.size();i++){
                        list.add(chosenCourses.get(i).getCourseCode());
                    }
                    StudentCourses.setCoursesToTake(list);
                    NavHostFragment.findNavController(StudentChooseCoursesTimeline.this)
                            .navigate(R.id.action_studentChooseCoursesTimeline_to_studentTimeline);
                }

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

                        ArrayList<String> studentCourses = StudentCourses.getCourseCodes();
                        for (int i = 0; i< list.size();i++){
                            if (studentCourses.contains(list.get(i).getCourseCode())){
                                list.remove(i);
                            }
                        }
                        Log.i("STATUS", list.get(0).getCourseName());
                        adapter = new StudentCourseListAdapterCheckboxes(list);
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