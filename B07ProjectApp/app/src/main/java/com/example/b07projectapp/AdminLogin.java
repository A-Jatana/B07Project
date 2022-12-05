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
public class AdminLogin extends Login{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAdminLoginBinding binding;

    // TODO: Rename and change types of parameters
    private String username;
    private String password;
    private FirebaseAuth auth;

    static int check = 0;

    public AdminLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminLogin.
     */
    /*
    View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        showCountTextView = binding.textviewFirst;
        return binding.getRoot();
     */
    // TODO: Rename and change types and number of parameters
    public static AdminLogin newInstance(String param1, String param2) {
        AdminLogin fragment = new AdminLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        view.findViewById(R.id.adminSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textUser = binding.adminTextUsername;
                username = textUser.getText().toString();
                EditText textPassword = binding.adminTextPassword;
                password = textPassword.getText().toString();

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
                            textUser.setText("");
                            textPassword.setText("");
                        }
                    });
                }
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
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
