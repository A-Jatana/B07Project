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

import java.util.ArrayList;

public class AdminCourseAdapter extends RecyclerView.Adapter<AdminCourseAdapter.courseViewHolder>{

    ArrayList<Course> list;

    public AdminCourseAdapter(ArrayList<Course> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_course_item, parent, false);
        return new courseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int i) {
        holder.courseName.setText(list.get(i).getCourseCode() + ": " + list.get(i).getCourseName());
        holder.course_offering.setText("Seasonal Offerings: " + list.get(i).getOfferingSessions());
        holder.course_prereq.setText("Prerequisites: " + list.get(i).getPrerequisites());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class courseViewHolder extends RecyclerView.ViewHolder {

        TableRow title_row, offering_row, prerequisite_row;
        TextView courseName, course_offering, course_prereq;

        public courseViewHolder(@NonNull View itemView) {
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
