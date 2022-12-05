package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentAdminAddCourseBinding;
import com.example.b07projectapp.databinding.FragmentAdminDeleteCourseBinding;
import com.example.b07projectapp.databinding.FragmentEntryScreenBinding;
import com.example.b07projectapp.databinding.FragmentStudentSignupBinding;
import android.os.Bundle;

import com.example.b07projectapp.UpdateCourse;
import com.example.b07projectapp.databinding.FragmentAdminUpdateCourseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminUpdateCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminDeleteCourse extends DeleteCourse {

    private FragmentAdminDeleteCourseBinding binding;
    DatabaseReference dRef;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String key, name, code, sessions, prereq;

    public AdminDeleteCourse() {

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
    public static AdminUpdateCourse newInstance(String param1, String param2, String param3, String param4) {
        AdminUpdateCourse fragment = new AdminUpdateCourse();
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

        view.findViewById(R.id.delete_course_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textToBe = binding.inputDeleteCourseName;
                key = textToBe.getText().toString();
                /* TODO implement if statement
                 * If (Database already has that username) {
                 * pop up message saying username is already taken
                 * }*/

                if (key.isEmpty()) {
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the name of the course you wish to delete!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    CourseManager.generateCourseList();
                    delete_course(key);
                    textToBe.setText("");
                }
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
        binding = FragmentAdminDeleteCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    public boolean check_prereq(String course_name){
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("course");
//        DataSnapshot snapshot = null;
//
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot info : snapshot.getChildren()){
//                    String checker = info.child("prerequisites").getValue().toString();
//                    if(course_name.equals(checker)){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })
//    }

    void delete_course(String course_name) {

        Log.i("status", "check1");

        dRef = FirebaseDatabase.getInstance().getReference("course");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("course");
        Query query_user = reference.orderByChild("courseName").equalTo(course_name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("status", "check2");
                // Checks if course exists
                if (snapshot.child(course_name).exists()) {

                    String check_code = snapshot.child(course_name).child("courseCode").getValue().toString();

                    for(DataSnapshot info : snapshot.getChildren()){
                        String checker = info.child("prerequisites").getValue().toString();

                        if(checker.contains(check_code)){
                            binding.inputDeleteCourseName.setText("");
                            Toast myToast = Toast.makeText(getActivity(), "This course is a prerequisite for one or more courses!", Toast.LENGTH_SHORT);
                            myToast.show();
                            return;
                        }
                    }
                    Log.i("status", "check3");



                    dRef.child(course_name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                binding.inputDeleteCourseName.setText("");
                                Toast myToast = Toast.makeText(getActivity(), "Course has been deleted!", Toast.LENGTH_SHORT);
                                myToast.show();
                            }

                        }
                    });
                }
                else {
                    binding.inputDeleteCourseName.setText("");
                    Toast myToast = Toast.makeText(getActivity(), "The course you wish to delete does not exist!", Toast.LENGTH_SHORT);
                    myToast.show();

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Log.i("status", "check4");
    }
}







//package com.example.b07projectapp;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.fragment.NavHostFragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TableRow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.b07projectapp.databinding.FragmentAdminDeleteCourseBinding;
//import android.os.Bundle;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AdminDeleteCourse#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AdminDeleteCourse extends DeleteCourse {
//
//    private FragmentAdminDeleteCourseBinding binding;
//    DatabaseReference dRef;
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private static final String ARG_PARAM3 = "param3";
//    private static final String ARG_PARAM4 = "param4";
//
//    private String key;
//
//    public AdminDeleteCourse() {
//
//    }
//
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @param param3 Parameter 3.
//     * @param param4 Parameter 4.
//     * @return A new instance of fragment StudentLogin.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AdminUpdateCourse newInstance(String param1, String param2, String param3, String param4) {
//        AdminUpdateCourse fragment = new AdminUpdateCourse();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        args.putString(ARG_PARAM3, param3);
//        args.putString(ARG_PARAM4, param4);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        view.findViewById(R.id.delete_course_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText textToBe = binding.inputDeleteCourseName;
//                key = textToBe.getText().toString();
//                /* TODO implement if statement
//                 * If (Database already has that username) {
//                 * pop up message saying username is already taken
//                 * }*/
//
//                if (key.isEmpty()) {
//                    Toast myToast = Toast.makeText(getActivity(), "Please provide the name of the course you wish to delete!", Toast.LENGTH_SHORT);
//                    myToast.show();
//                }
//                else {
//
//                    delete_course(key);
//
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        binding = FragmentAdminDeleteCourseBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////
////        binding.deleteCourseButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String key = binding.inputDeleteCourseName.getText().toString();
////                if (!key.isEmpty()) {
////                    delete_course(key);
////                }
//
////                else{
////
////                }
//
////        });
////    }
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_admin_delete_course, container, false);
//    }
//
//
//
//    void delete_course(String course_name) {
//
//        dRef = FirebaseDatabase.getInstance().getReference("course");
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("course");
//        Query query_user = reference.orderByChild("courseName").equalTo(course_name);
//        query_user.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // Checks if course exists
//                if (snapshot.getValue() != null) {
//
//                    dRef.child(course_name).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful()) {
//                                binding.inputDeleteCourseName.setText("");
////                                Toast myToast = Toast.makeText(getActivity(), "Course has been deleted!", Toast.LENGTH_SHORT);
////                                myToast.show();
//                            }
//
//                        }
//                    });
//                }
////                else {
////                    binding.inputDeleteCourseName.setText("");
////                    Toast myToast = Toast.makeText(getActivity(), "The course you wish to delete does not exist!", Toast.LENGTH_SHORT);
////                    myToast.show();
////
////                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//
//    }
//
//
//}