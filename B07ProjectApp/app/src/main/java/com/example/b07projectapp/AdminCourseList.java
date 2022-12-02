package com.example.b07projectapp;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07projectapp.databinding.FragmentAdminAddCourseBinding;
import com.example.b07projectapp.databinding.FragmentAdminCourseListBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCourseList extends Fragment{

    DatabaseReference dRef;
    ArrayList<Course> list;
    RecyclerView recyclerView;
    SearchView searchView;
    private View courseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_add = getView().findViewById(R.id.button_add_course);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminCourseList.this)
                        .navigate(R.id.action_adminCourseList_to_adminAddCourse);
            }
        });

        Button btn_edit_delete = getView().findViewById(R.id.button_delete);
        btn_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminCourseList.this)
                        .navigate(R.id.action_adminCourseList_to_deleteCourse);
            }
        });

        Button update_course = getView().findViewById(R.id.button_update);
        update_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AdminCourseList.this)
                        .navigate(R.id.action_adminCourseList_to_adminUpdateCourse);
            }
        });
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        courseView = inflater.inflate(R.layout.fragment_admin_course_list, container, false);

        recyclerView = (RecyclerView) courseView.findViewById(R.id.rv);
        searchView = (SearchView) courseView.findViewById(R.id.search_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dRef = FirebaseDatabase.getInstance().getReference().child("course");

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
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String s) {
        ArrayList<Course> myList = new ArrayList<>();
        for (Course course : list) {
            if (course.getCourseName().toLowerCase().contains(s.toLowerCase())
                    || course.getCourseCode().toLowerCase().contains(s.toLowerCase())) {
                myList.add(course);
            }
        }
        AdminCourseAdapter adapter = new AdminCourseAdapter(myList);
        recyclerView.setAdapter(adapter);
    }
































}
