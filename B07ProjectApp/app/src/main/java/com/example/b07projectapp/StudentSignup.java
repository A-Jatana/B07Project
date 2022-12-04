package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentStudentLoginBinding;
import com.example.b07projectapp.databinding.FragmentStudentSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentSignup extends Login {

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
    DatabaseReference dRef;
    ArrayList<String> list = new ArrayList<>();
    final private String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final private String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    final private String numbers = "0123456789";
    final private String symbols = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/";
    final private int minPwdChar = 8;
    final private int maxPwdChar= 20;



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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dRef = FirebaseDatabase.getInstance().getReference().child("student");

        if (dRef != null) {
            dRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds.child("username").getValue(String.class));

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }

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
        //
        return binding.getRoot();
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


                if (username.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Username cannot be empty!", Toast.LENGTH_SHORT);
                    myToast.show();
                }

                else if (list.contains(username)){
                    Toast myToast = Toast.makeText(getActivity(), "Username already exists!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (password.isEmpty() || confirmPassword.isEmpty()){
                    Toast myToast = Toast.makeText(getActivity(), "Password cannot be empty!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!password.equals(confirmPassword)){
                    Toast myToast = Toast.makeText(getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (password.length()<minPwdChar){
                    Toast myToast = Toast.makeText(getActivity(), "Password too short! Must have atleast 8 characters!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (password.length()>maxPwdChar){
                    Toast myToast = Toast.makeText(getActivity(), "Password too long! Must have less than 21 characters!", Toast.LENGTH_SHORT);
                    myToast.show();
                }

                else if (!containsUpperCase(password) || !containsLowerCase(password) || (!containsNumber(password)&&!containsSymbol(password))){
                    Toast myToast = Toast.makeText(getActivity(), "Password must contain an uppercase, a lowercase, and a symbol/number!", Toast.LENGTH_LONG);
                    myToast.show();
                }
                else {
                    DatabaseManager dm = new DatabaseManager(username, password, "student");
                    dm.add(new DatabaseManager.addCallback() {
                        @Override
                        public void callback(boolean data) {
                            Toast myToast;
                            if (data) {
                                myToast = Toast.makeText(getContext(), "Signup Success!", Toast.LENGTH_SHORT);
                                login();
                            }
                            else {
                                myToast = Toast.makeText(getContext(), "Username already exists!", Toast.LENGTH_SHORT);
                            }
                            myToast.show();
                        }
                    });
                }
            }
        });
    }

    @Override
    void login() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_studentSignup_to_studentLogin);
        // Why do the ids contain 2?
    }

    private boolean containsUpperCase(String password){
        char[] upperCasech = upperCase.toCharArray();
        for (char ch: upperCasech) {
            if (password.indexOf(ch)!=-1){
                return true;
            }
        }
        return false;
    }
    private boolean containsLowerCase(String password){
        char[] lowerCasech = lowerCase.toCharArray();
        for (char ch: lowerCasech) {
            if (password.indexOf(ch)!=-1){
                return true;
            }
        }
        return false;

    }

    private boolean containsNumber(String password){
        char[] numbersch = numbers.toCharArray();
        for (char ch: numbersch) {
            if (password.indexOf(ch)!=-1){
                return true;
            }
        }
        return false;

    }

    private boolean containsSymbol(String password){
        char[] symbolsch = symbols.toCharArray();
        for (char ch: symbolsch) {
            if (password.indexOf(ch)!=-1){
                return true;
            }
        }
        return false;

    }





}