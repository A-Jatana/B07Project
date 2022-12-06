package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentStudentLoginBinding;


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
        presenter = new StudentPresenter(new StudentLoginModel(), this);
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
        Toast myToast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        myToast.show();
    }

    @Override
    public void loginToProgram() {
        login();
    }


}