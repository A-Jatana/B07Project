package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentAdminLoginBinding;
import com.google.firebase.auth.FirebaseAuth;


public class AdminLogin extends Login implements Control.View{

    private FragmentAdminLoginBinding binding;
    private Control.Presenter presenter;

    // TODO: Rename and change types of parameters
    private String username;
    private String password;
    private FirebaseAuth auth;

    static int check = 0;

    public AdminLogin() {
        // Required empty public constructor
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        view.findViewById(R.id.adminSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkLogin();
            }

        });
    }

    @Override
    void login() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_adminLogin_to_adminCourseList);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AdminPresenter(new AdminLoginModel(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public String getUsername() {
        EditText textUser = binding.adminTextUsername;
        username = textUser.getText().toString();
        textUser.setText("");
        return username;
    }

    @Override
    public String getPassword() {
        EditText textPassword = binding.adminTextPassword;
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
