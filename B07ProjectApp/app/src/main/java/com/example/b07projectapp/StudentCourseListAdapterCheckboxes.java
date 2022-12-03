package com.example.b07projectapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentCourseListAdapterCheckboxes extends RecyclerView.Adapter<StudentCourseListAdapterCheckboxes.courseViewHolder> {
    ArrayList<Course> list;
    ArrayList<Course> checked_list;


    public StudentCourseListAdapterCheckboxes(ArrayList<Course> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StudentCourseListAdapterCheckboxes.courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_course_item_checkbox, parent, false);
        return new StudentCourseListAdapterCheckboxes.courseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseListAdapterCheckboxes.courseViewHolder holder, int i) {
        holder.courseName.setText(list.get(i).getCourseCode() + ": " + list.get(i).getCourseName());
        holder.course_offering.setText("Seasonal Offerings: " + list.get(i).getOfferingSessions());
        holder.course_prereq.setText("Prerequisites: " + list.get(i).getPrerequisites());

        holder.check_box.setText("");
        if (holder.check_box.isChecked()){
            checked_list.add(list.get(i));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class courseViewHolder extends RecyclerView.ViewHolder {
        CheckBox check_box;
        TableRow title_row, offering_row, prerequisite_row;
        TextView courseName, course_offering, course_prereq;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);

            title_row = (TableRow)itemView.findViewById(R.id.table_title_row_ch);
            offering_row = (TableRow)itemView.findViewById(R.id.table_offering_row_ch);
            prerequisite_row = (TableRow)itemView.findViewById(R.id.table_prerequisite_row_ch);

            courseName = (TextView)itemView.findViewById(R.id.table_title_name_ch);
            course_offering = (TextView)itemView.findViewById(R.id.table_offering_name_ch);
            course_prereq = (TextView)itemView.findViewById(R.id.table_prerequisite_name_ch);
        }
    }

    public ArrayList<Course> getCheckList(){
        return checked_list;
    }
}

