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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentTimeline#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentTimeline extends Fragment {
    DatabaseReference dRef;
    ArrayList<Course> list;
    RecyclerView recyclerView;
    private View courseView;

    public StudentTimeline() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentTimeline.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentTimeline newInstance(String param1, String param2) {
        StudentTimeline fragment = new StudentTimeline();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.back_to_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StudentTimeline.this)
                        .navigate(R.id.action_studentTimeline_to_studentCourseList);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            courseView = inflater.inflate(R.layout.fragment_student_timeline, container, false);

            recyclerView = (RecyclerView) courseView.findViewById(R.id.rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            dRef = FirebaseDatabase.getInstance().getReference().child("student").child(StudentCourses.getStudentName()).child("course");

//        binding = FragmentAdminAddCourseBinding.inflate(inflater, container, false);
//        return binding.getRoot();
            return courseView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (dRef != null) {
            dRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Timeline.generateTimeline(StudentCourses.getCoursesToTake(),2022);
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