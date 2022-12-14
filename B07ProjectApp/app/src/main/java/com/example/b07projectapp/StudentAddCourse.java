package com.example.b07projectapp;

import static com.example.b07projectapp.StudentCourseListAdapterCheckboxes.list_adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import java.util.Arrays;


public class StudentAddCourse extends Fragment {

    DatabaseReference dRef;
    ArrayList<Course> list;
    RecyclerView recyclerView;
    StudentCourseListAdapterCheckboxes adapter;
    ArrayList<Course> coursesToAdd = new ArrayList<Course>();
    private View courseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        courseView = inflater.inflate(R.layout.fragment_student_add_course, container, false);

        recyclerView = courseView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dRef = FirebaseDatabase.getInstance().getReference().child("course");
        return courseView;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_add = requireView().findViewById(R.id.studentAddCourse);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0;i<list_adapter.size();i++){
                    if (list_adapter.get(i).getSelected()==true){
                        coursesToAdd.add(list_adapter.get(i));
                    }
                }
                if (coursesToAdd != null){
                    if (coursesToAdd.isEmpty()){
                        Toast myToast = Toast.makeText(getActivity(), "Please choose at least one course!", Toast.LENGTH_SHORT);
                        myToast.show();
                    } else if(needPrereq(coursesToAdd)){
                        Toast myToast = Toast.makeText(getActivity(), "You do not have all prerequisites for the(se) course(s)!", Toast.LENGTH_SHORT);
                        myToast.show();
                    }else {
                        for (int i = 0; i< coursesToAdd.size();i++){
                            StudentCourses.addCourse(coursesToAdd.get(i).getCourseCode(),coursesToAdd.get(i).getOfferingSessions(),coursesToAdd.get(i).getPrerequisites());
                            CourseManager dm = new CourseManager(coursesToAdd.get(i).getCourseName(), coursesToAdd.get(i).getCourseCode(), coursesToAdd.get(i).getOfferingSessions(), coursesToAdd.get(i).getPrerequisites(), "course");
                            dm.addStudentCourse(new CourseManager.addCallback() {
                                @Override
                                public void callback(boolean data) {
                                }
                            });
                        }

                        NavHostFragment.findNavController(StudentAddCourse.this)
                                .navigate(R.id.action_studentAddCourse_to_studentCourseList);
                    }
                } else{
                    Toast myToast = Toast.makeText(getActivity(), "Hehe somethings fucked", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }
    public boolean needPrereq(ArrayList<Course> list){
        ArrayList<String> coursesTaken = StudentCourses.getCourseCodes();
        ArrayList<String> prereqsNeeded = new ArrayList<>();
        //Go through all prerequisites needed for the courses to add
        for (int i =0; i< list.size();i++){
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(list.get(i).getPrerequisites().split(",")));
            prereqsNeeded.addAll(temp);
        }

        //Compare prereqs needed to courses already taken
        for (int i = 0; i< prereqsNeeded.size();i++){
            if (!coursesTaken.contains(prereqsNeeded.get(i)) && !prereqsNeeded.get(i).equals("NONE")){
                return true;
            }
        }
        return false;
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
                            if (!StudentCourses.getCourseCodes().contains(ds.child("courseCode").getValue().toString())){
                                list.add(new Course(ds.child("courseName").getValue().toString(),
                                        ds.child("courseCode").getValue().toString(),
                                        ds.child("offeringSessions").getValue().toString(),
                                        ds.child("prerequisites").getValue().toString()));
                            }

                        }
                        /*
                        ArrayList<String> studentCourses = StudentCourses.getCourseCodes();
                        for (int i = 0; i< list.size();i++){
                            if (studentCourses.contains(list.get(i).getCourseCode())){
                                list.remove(i);
                            }
                        }

                         */
                        adapter = new StudentCourseListAdapterCheckboxes(list);
                        recyclerView.setAdapter(adapter);
                        /*
                        StudentCourseListAdapter adapter2;
                        adapter2 = new StudentCourseListAdapter(list);
                        recyclerView.setAdapter(adapter2);

                         */
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}