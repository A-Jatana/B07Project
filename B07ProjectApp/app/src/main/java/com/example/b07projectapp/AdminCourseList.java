package com.example.b07projectapp;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07projectapp.databinding.FragmentAdminAddCourseBinding;
import com.example.b07projectapp.databinding.FragmentAdminCourseListBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCourseList extends Fragment {

    private FragmentAdminCourseListBinding binding;
    private RecyclerView recyclerView;
    private View courseView;
    AdminCourseListAdapter adapter;
    private DatabaseReference dRef;

    public void AdminCourseListAdapter() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        courseView = inflater.inflate(R.layout.fragment_admin_course_list, container, false);

        recyclerView = (RecyclerView) courseView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dRef = FirebaseDatabase.getInstance().getReference().child("course");

//        binding = FragmentAdminAddCourseBinding.inflate(inflater, container, false);
//        return binding.getRoot();
        return courseView;
    }

//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = (RecyclerView)getView().findViewById(R.id.rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // activity or context???
//
//        FirebaseRecyclerOptions<Course> options =
//                new FirebaseRecyclerOptions.Builder<Course>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("course"), Course.class )
//                        .build();
//
//        adapter = new AdminCourseListAdapter(options);
//        recyclerView.setAdapter(adapter);
//    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Course> options =
                new FirebaseRecyclerOptions.Builder<Course>()
                        .setQuery(dRef, Course.class )
                        .build();

        adapter = new AdminCourseListAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




























}
