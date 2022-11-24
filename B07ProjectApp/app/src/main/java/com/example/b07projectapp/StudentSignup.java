package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.b07projectapp.databinding.FragmentStudentLoginBinding;
import com.example.b07projectapp.databinding.FragmentStudentSignupBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentSignup extends Fragment {

    private FragmentStudentSignupBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String fullName;

    public StudentSignup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment StudentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentLogin newInstance(String param1, String param2, String param3) {
        StudentLogin fragment = new StudentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textUser = binding.inputUsername;
                username = textUser.getText().toString();
                EditText textPassword = binding.inputPassword;
                password = textPassword.getText().toString();
                EditText textConfirm = binding.inputConfirmPassword;
                confirmPassword = textConfirm.getText().toString();
                EditText textEmail= binding.inputEmail;
                email = textEmail.getText().toString();
                EditText textFullName = binding.inputFullName;
                fullName = textFullName.getText().toString();
                /* TODO implement if statement
                * If (Database already has that username) {
                * pop up message saying username is already taken
                * }*/
                if (password.equals(confirmPassword)){
                    //Pop up message saying passwords don't match
                }
                DatabaseManager.add(username, password);
            }
        });
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
        binding = FragmentStudentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}