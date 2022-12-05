package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
                else if (!isValidCourse(code)){
                    Toast myToast = Toast.makeText(getActivity(), "Invalid course code", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (sessions.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the offering sessions of the course!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!isValidSession(sessions)){
                    Toast myToast = Toast.makeText(getActivity(), "Invalid session offerings", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!isValidCourse(prereq)){
                    Toast myToast = Toast.makeText(getActivity(), "Prerequisites have an invalid course code!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    if (prereq == null){
                        prereq = "NONE";
                    }
                    code = toValidCourse(code);
                    prereq = toValidCourse(prereq);
                    sessions = toValidCourse(sessions);
                    CourseManager dm = new CourseManager(name, code, sessions, prereq, "course");
                    dm.add(new CourseManager.addCallback() {
                        @Override
                        public void callback(boolean data) {
                            Toast myToast;
                            if (data) {
                                myToast = Toast.makeText(getContext(), "Course Added!", Toast.LENGTH_SHORT);
                                textName.setText("");
                                textCode.setText("");
                                textSessions.setText("");
                                textPrereq.setText("");
                                //add_course();
                                CourseManager.generateCourseList();
                            }
                            else {
                                myToast = Toast.makeText(getContext(), "Course already exists!", Toast.LENGTH_SHORT);
                            }
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

    public static boolean isValidCourse(String text) {
        int i = 0;
        int j = 0;

        char array[] = text.toCharArray();

        while (i<text.length()) {
            char c = array[i];
            if (c == ' ' || c == ',') {
                if (j != 0) {
                    return false;
                }
            } else if (!isNumber(c)&&!isLetter(c)) {
                return false;
            } else {
                if (j >= 0 && j<= 3) { //First three characters of a course code
                    if (isNumber(c)) {
                        return false;
                    }
                } else if (j==4 || j==5) {
                    if (isLetter(c)) {
                        return false;
                    }
                }

                if (j==5) {
                    j=0;
                } else {
                    j++;
                }
            }
            i++;

        }
        return true;
    }

    public static boolean isNumber(char c) {
        if ("1234567890".indexOf(c) == -1) {
            return false;
        }
        return true;
    }

    public static boolean isLetter(char c) {
        if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c) == -1) {
            return false;
        }
        return true;
    }

    public static String toValidCourse(String text) {
        int i = 0;
        int startIdx = -1;
        int endIdx = -1;
        String s = "";
        char array[] = text.toCharArray();

        while (i<text.length()) {
            char c = array[i];
            if (c== ' ' || c == ',') {
                if (startIdx != -1) {
                    endIdx = i;
                }
            } else {
                if (startIdx == -1) {
                    startIdx = i;
                }
                if (i == text.length()-1) {
                    endIdx = i+1;
                }
            }

            if (startIdx != -1 && endIdx != -1) {
                if (!s.equals("")) {
                    s+=",";
                }
                s +=text.substring(startIdx, endIdx);
                startIdx = -1;
                endIdx = -1;
            }
            i++;
        }

        return s;
    }

    public static boolean isValidSession(String text) {
        int i = 0;
        char array[] = text.toCharArray();
        while (i< text.length()) {
            char c = array[i];
            if (("FSW".indexOf(c) == -1) && !(c==' ') && !(c==',')) { //If c is none of the valid letter
                return false;
            } else if (c == 'F'){
                if (!isFall(text,i)) {
                    return false;
                } else {
                    i += 4;
                }
            } else if (c == 'W') {
                if (!isWinter(text,i)) {
                    return false;
                } else {
                    i += 6;
                }
            } else if (c=='S') {
                if (!isSummer(text,i)) {
                    return false;
                } else {
                    i += 6;
                }
            } else {
                i++;
            }
        }
        return true;
    }

    public static boolean isFall(String text, int i) {
        String sub = text.substring(i);
        if (sub.length()<4) {
            return false;
        } else {
            String fallSub = sub.substring(0, 4).toUpperCase();
            if (!fallSub.equals("FALL")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWinter(String text, int i) {
        String sub = text.substring(i);
        if (sub.length()<6) {
            return false;
        } else {
            String winterSub = sub.substring(0, 6).toUpperCase();
            if (!winterSub.equals("WINTER")) {
                return false;
            }
        }
        return true;
    }
    public static boolean isSummer(String text, int i) {
        String sub = text.substring(i);
        if (sub.length()<6) {
            return false;
        } else {
            String summerSub = sub.substring(0, 6).toUpperCase();
            if (!summerSub.equals("SUMMER")) {
                return false;
            }
        }
        return true;
    }
    public static String toValidSession(String text) {
        //MAX THREE SESSIONS in order: [F,W,S]
        boolean sessions[] = {false, false, false};
        String sessionNames [] = {"Fall","Winter","Summer"};
        int i = 0;
        char array[] = text.toCharArray();
        while (i< text.length()) {
            char c = array[i];

            if (c == 'F'){
                i += 4;
                sessions[0] = true;
            }
            if (c == 'W') {
                i+=6;
                sessions[1] = true;
            }
            if (c =='S') {
                i+= 6;
                sessions[2]=true;
            }

            if ("FSW".indexOf(c) == -1) {
                i++;
            }
        }

        String s = "";
        for (int j = 0; j< 3; j++) {
            if (sessions[j] == true) {
                if (!s.equals("")) {
                    s+=",";
                }
                s+= sessionNames[j];
            }
        }
        return s;
    }
}