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


    ArrayList<String> sessionList = Timeline.getSessionList();
    ArrayList<String> coursesList = Timeline.getCourseList();

    public StudentTimelineAdapter() {
    }

    @NonNull
    @Override
    public StudentTimelineAdapter.courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_timeline_item, parent, false);
        return new StudentTimelineAdapter.courseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentTimelineAdapter.courseViewHolder holder, int i) {
        holder.session_offering.setText(sessionList.get(i));
        holder.course_offering.setText(coursesList.get(i));
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    class courseViewHolder extends RecyclerView.ViewHolder {

        TableRow session_row, courses_row;
        TextView session_offering, course_offering;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);

            session_row = (TableRow)itemView.findViewById(R.id.table_offering_session);
            courses_row = (TableRow)itemView.findViewById(R.id.table_courses);
            //prerequisite_row = (TableRow)itemView.findViewById(R.id.table_prerequisite_row);

            session_offering= (TextView)itemView.findViewById(R.id.table_session_name);
            course_offering = (TextView)itemView.findViewById(R.id.table_course_name);
        }
    }
}
