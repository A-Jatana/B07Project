package com.example.b07projectapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.core.Context;

public class AdminCourseListAdapter extends FirebaseRecyclerAdapter<Course, AdminCourseListAdapter.myViewHolder>{

    public AdminCourseListAdapter(@NonNull FirebaseRecyclerOptions<Course> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Course model) {
        holder.courseName.setText(model.getCourseCode() + ": " + model.getCourseName());
        holder.course_offering.setText("Seasonal Offerings: " + model.getOfferingSessions());
        holder.course_prereq.setText("Prerequisites: " +model.getPrerequisites());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_course_item, parent, false);
        return new AdminCourseListAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TableRow title_row, offering_row, prerequisite_row;
        TextView courseName, course_offering, course_prereq;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            title_row = (TableRow)itemView.findViewById(R.id.table_title_row);
            offering_row = (TableRow)itemView.findViewById(R.id.table_offering_row);
            prerequisite_row = (TableRow)itemView.findViewById(R.id.table_prerequisite_row);

            courseName = (TextView)itemView.findViewById(R.id.table_title_name);
            course_offering = (TextView)itemView.findViewById(R.id.table_offering_name);
            course_prereq = (TextView)itemView.findViewById(R.id.table_prerequisite_name);
        }
    }
}
