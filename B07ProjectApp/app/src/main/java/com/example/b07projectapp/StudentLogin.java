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


public class StudentLogin extends Login implements Control.View{
    private FragmentStudentLoginBinding binding;

    private String username;
    private String password;

    public StudentLogin() {
        // Required empty public constructor
    }

    private Control.Presenter presenter;

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
                presenter.checkLogin();


/*
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
                */
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
        presenter = new Presenter (new StudentLoginModel(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public String getUsername(){
        EditText textUser = binding.inputUsername;
        username = textUser.getText().toString();
        textUser.setText("");
        return username;
    }

    @Override
    public String getPassword() {
        EditText textPassword = binding.inputPassword;
        password = textPassword.getText().toString();
        textPassword.setText("");
        return password;
    }

    @Override
    public void displayMessage(String msg) {

    }

    @Override
    public void loginToProgram() {

    }


}