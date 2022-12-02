package com.example.b07projectapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentTimelineAdapter extends RecyclerView.Adapter<StudentTimelineAdapter.courseViewHolder> {

    ArrayList<Course> list;

    public StudentTimelineAdapter(ArrayList<Course> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StudentTimelineAdapter.courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_course_item, parent, false);
        return new StudentTimelineAdapter.courseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentTimelineAdapter.courseViewHolder holder, int position) {
        holder.courseName.setText(list.get(i).getCourseCode() + ": " + list.get(i).getCourseName());
        holder.course_offering.setText("Seasonal Offerings: " + list.get(i).getOfferingSessions());
        holder.course_prereq.setText("Prerequisites: " + list.get(i).getPrerequisites());
    }


/*
    public void onBindViewHolder(@NonNull StudentCourseListAdapter.courseViewHolder holder, int i) {
        holder.courseName.setText(list.get(i).getCourseCode() + ": " + list.get(i).getCourseName());
        holder.course_offering.setText("Seasonal Offerings: " + list.get(i).getOfferingSessions());
        holder.course_prereq.setText("Prerequisites: " + list.get(i).getPrerequisites());


    }
    */


    @Override
    public int getItemCount() {
        return list.size();
    }

    class courseViewHolder extends RecyclerView.ViewHolder {

        TableRow session_row, courses_row;
        TextView courseName, course_offering, course_prereq;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);

            session_row = (TableRow)itemView.findViewById(R.id.table_title_row);
            courses_row = (TableRow)itemView.findViewById(R.id.table_offering_row);
            //prerequisite_row = (TableRow)itemView.findViewById(R.id.table_prerequisite_row);

            courseName = (TextView)itemView.findViewById(R.id.table_title_name);
            course_offering = (TextView)itemView.findViewById(R.id.table_offering_name);
            course_prereq = (TextView)itemView.findViewById(R.id.table_prerequisite_name);
        }
    }
}
