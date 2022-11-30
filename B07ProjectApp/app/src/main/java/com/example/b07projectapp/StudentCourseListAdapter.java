package com.example.b07projectapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class StudentCourseListAdapter extends RecyclerView.Adapter<StudentCourseListAdapter.ViewHolder> {

    Context context;
    ArrayList<Course> student_course_list;

    public StudentCourseListAdapter(Context context, ArrayList<Course> student_course_list) {
        this.context = context;
        this.student_course_list = student_course_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_course_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (student_course_list != null && student_course_list.size()>0){
            Course course = student_course_list.get(position);
            holder.courseName_tv.setText(course.getCourseName());
            holder.courseCode_tv.setText(course.getCourseCode());
            holder.offeringSessions_tv.setText(course.getOfferingSessions());
            holder.prerequisites_tv.setText(course.getPrerequisites());
        }
        else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return student_course_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView courseName_tv, courseCode_tv, offeringSessions_tv, prerequisites_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName_tv = itemView.findViewById(R.id.courseName_tv);
            courseCode_tv = itemView.findViewById(R.id.courseCode_tv);
            offeringSessions_tv = itemView.findViewById(R.id.offeringSessions_tv);
            prerequisites_tv = itemView.findViewById(R.id.prerequisites_tv);
        }
    }
}
