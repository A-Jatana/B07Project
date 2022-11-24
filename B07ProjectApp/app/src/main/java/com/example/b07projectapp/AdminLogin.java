package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.b07projectapp.databinding.FragmentAdminLoginBinding;
import com.example.b07projectapp.databinding.FragmentStudentDesicionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAdminLoginBinding binding;

    // TODO: Rename and change types of parameters
    private String username;
    private String password;

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

        view.findViewById(R.id.adminSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textUser = binding.adminTextUsername;
                username = textUser.getText().toString();
                EditText textPassword = binding.adminTextPassword;
                password = textPassword.getText().toString();

                DatabaseManager.search(username, password, "admin");
                //TODO if search comes back true, move to next admin page

            }
        });
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