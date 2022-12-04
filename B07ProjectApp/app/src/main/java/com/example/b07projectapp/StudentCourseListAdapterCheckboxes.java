package com.example.b07projectapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentCourseListAdapterCheckboxes extends RecyclerView.Adapter<StudentCourseListAdapterCheckboxes.courseViewHolder> {
    public static ArrayList<Course> list_adapter;
    ArrayList<Course> checked_list;


    public StudentCourseListAdapterCheckboxes(ArrayList<Course> list_adapter) {
        this.list_adapter = list_adapter;
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
        holder.courseName.setText(list_adapter.get(i).getCourseCode() + ": " + list_adapter.get(i).getCourseName());
        holder.course_offering.setText("Seasonal Offerings: " + list_adapter.get(i).getOfferingSessions());
        holder.course_prereq.setText("Prerequisites: " + list_adapter.get(i).getPrerequisites());

        final Course course = list_adapter.get(i);
        holder.check_box.setOnCheckedChangeListener(null);
        holder.check_box.setChecked(course.getSelected());
        holder.check_box.setTag(course);

        if (course.getSelected()) {
            holder.check_box.setChecked(true);
            holder.check_box.setSelected(true);
        } else {
            holder.check_box.setChecked(false);
            holder.check_box.setSelected(false);
        }

        holder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.check_box.setSelected(true);
                    course.setSelected(true);
                }else {
                    holder.check_box.setSelected(false);
                    course.setSelected(false);
                }
            }
        });




    }



    @Override
    public int getItemCount() {
        return list_adapter.size();
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

            check_box=(CheckBox) itemView.findViewById((R.id.checkbox));

            courseName = (TextView)itemView.findViewById(R.id.table_title_name_ch);
            course_offering = (TextView)itemView.findViewById(R.id.table_offering_name_ch);
            course_prereq = (TextView)itemView.findViewById(R.id.table_prerequisite_name_ch);
        }
    }

}

