package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentAdminAddCourseBinding;
import com.example.b07projectapp.databinding.FragmentEntryScreenBinding;
import com.example.b07projectapp.databinding.FragmentStudentSignupBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAddCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAddCourse extends AddCourse {

    private FragmentAdminAddCourseBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String name, code, sessions, prereq;

    public AdminAddCourse(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @param param4 Parameter 4.
     * @return A new instance of fragment StudentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAddCourse newInstance(String param1, String param2, String param3, String param4) {
        AdminAddCourse fragment = new AdminAddCourse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.adminAddCourseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textName = binding.inputCourseName;
                name = textName.getText().toString();
                EditText textCode = binding.inputCourseCode;
                code = textCode.getText().toString().toUpperCase();
                EditText textSessions = binding.inputOfferingSessions;
                sessions = textSessions.getText().toString().toUpperCase();
                EditText textPrereq= binding.inputPrerequisites;
                prereq = textPrereq.getText().toString().toUpperCase();
                /* TODO implement if statement
                 * If (Database already has that username) {
                 * pop up message saying username is already taken
                 * }*/

                if (name.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the name of the course!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (code.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the course code!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (sessions.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the offering sessions of the course!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!SyntaxCheck.isValidCourse(code)){
                    Toast myToast = Toast.makeText(getActivity(), "Invalid course code", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!SyntaxCheck.isValidSession(sessions)){
                    Toast myToast = Toast.makeText(getActivity(), "Invalid session offerings", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!SyntaxCheck.isValidCourse(prereq)){
                    Toast myToast = Toast.makeText(getActivity(), "Prerequisites have an invalid course code!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    if (prereq.isEmpty()){
                        prereq = "NONE";
                    } else {
                        prereq = SyntaxCheck.toValidCourse(prereq);
                    }
                    code = SyntaxCheck.toValidCourse(code);
                    sessions = SyntaxCheck.toValidSession(sessions);
                    CourseManager dm = new CourseManager(name, code, sessions, prereq, "course");
                    dm.add(new CourseManager.addCallback() {
                        @Override
                        public void callback(boolean data) {
                            Toast myToast;
                            if (data) {
                                myToast = Toast.makeText(getContext(), "Course Added!", Toast.LENGTH_SHORT);

                                //add_course();
                                CourseManager.generateCourseList();
                            }
                            else {
                                myToast = Toast.makeText(getContext(), "Course already exists!", Toast.LENGTH_SHORT);
                            }
                            textName.setText("");
                            textCode.setText("");
                            textSessions.setText("");
                            textPrereq.setText("");
                            myToast.show();
                        }
                    });
                }
            }
        });
    }

    @Override
    void add_course() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_adminAddCourse_to_adminCourseList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            //mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminAddCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}