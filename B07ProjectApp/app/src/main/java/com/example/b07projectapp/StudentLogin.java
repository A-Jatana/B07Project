package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentAdminLoginBinding;
import com.example.b07projectapp.databinding.FragmentStudentLoginBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentLogin extends Login {
    private FragmentStudentLoginBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String username;
    private String password;

    public StudentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentLogin newInstance(String param1, String param2) {
        StudentLogin fragment = new StudentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.donthaveanaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StudentLogin.this)
                        .navigate(R.id.action_studentLogin_to_studentSignup);
            }
        });

        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textUser = binding.inputUsername;
                username = textUser.getText().toString();
                EditText textPassword = binding.inputPassword;
                password = textPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast myToast = Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT);
                    myToast.show();
                } else {
                    DatabaseManager dm = new DatabaseManager(username, password, "student");
                    dm.search(new DatabaseManager.searchCallback() {
                        @Override
                        public void callback(boolean data) {
                            if (data) {
                                Toast myToast = Toast.makeText(getContext(), "Login Success!", Toast.LENGTH_SHORT);
                                myToast.show();

                                //Generates list of student courses
                                //Sets student name in student courses
                                CourseManager temp = new CourseManager();
                                StudentCourses.setStudentName(username);
                                temp.generateStudentCourseList(username);
                                login();
                            }
                            else {
                                Toast myToast = Toast.makeText(getContext(), "Incorrect username or password. Please try again.", Toast.LENGTH_SHORT);
                                myToast.show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    void login() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_studentLogin_to_studentCourseList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}