package com.example.b07projectapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentAdminLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminLogin extends Login implements Control.View{

    private FragmentAdminLoginBinding binding;
    private Control.Presenter presenter;
    private AdminLoginModel model;
    // TODO: Rename and change types of parameters
    private String username;
    private String password;
    private FirebaseAuth auth;

    static int check = 0;

    public AdminLogin() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AdminLogin newInstance(String param1, String param2) {
        AdminLogin fragment = new AdminLogin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        view.findViewById(R.id.adminSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkLogin();
                /*
                if (username.isEmpty() || password.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    DatabaseManager dm = new DatabaseManager(username, password, "admin");
                    dm.search(new DatabaseManager.searchCallback() {
                        @Override
                        public void callback(boolean data) {
                            if (data) {
                                Toast myToast = Toast.makeText(getContext(), "Login Success!", Toast.LENGTH_SHORT);
                                myToast.show();
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
                .navigate(R.id.action_adminLogin_to_adminCourseList);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(model, this);
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
