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
//import android.widget.Toast;
//
//import com.example.b07projectapp.databinding.FragmentAdminAddCourseBinding;
//import com.example.b07projectapp.databinding.FragmentAdminUpdateCourseBinding;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
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
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
package com.example.b07projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07projectapp.databinding.FragmentAdminAddCourseBinding;
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
public class AdminUpdateCourse extends UpdateCourse {

    private FragmentAdminUpdateCourseBinding binding;
    DatabaseReference dRef;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String key, name, code, sessions, prereq;

    public AdminUpdateCourse() {

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

        view.findViewById(R.id.off_update_course_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textToBe = binding.inputCourseToBeUpdated;
                key = textToBe.getText().toString();
                EditText textName = binding.updateCourseName;
                name = textName.getText().toString();
                EditText textCode = binding.updateCourseCode;
                code = textCode.getText().toString().toUpperCase();
                EditText textSessions = binding.updateOfferingSessions;
                sessions = textSessions.getText().toString().toUpperCase();
                EditText textPrereq = binding.updatePrerequisites;
                prereq = textPrereq.getText().toString().toUpperCase();
                /* TODO implement if statement
                 * If (Database already has that username) {
                 * pop up message saying username is already taken
                 * }*/

                if (name.isEmpty()) {
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the updated or original name of the course!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (code.isEmpty()) {
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the updated or original course code!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (sessions.isEmpty()) {
                    Toast myToast = Toast.makeText(getActivity(), "Please provide the updated or original offering sessions of the course!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!SyntaxCheck.isValidCourse(code)){
                    Toast myToast = Toast.makeText(getActivity(), "Invalid course code", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!SyntaxCheck.isValidSession(sessions)){
                    Toast myToast = Toast.makeText(getActivity(), "Invalid session offerings", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if (!SyntaxCheck.isValidCourse(prereq)){
                    Toast myToast = Toast.makeText(getActivity(), "Prerequisites have an invalid course code!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    if (prereq.isEmpty()){
                        prereq = "None";
                    } else {
                        prereq = SyntaxCheck.toValidCourse(prereq);
                    }
                    code = SyntaxCheck.toValidCourse(code);
                    sessions = SyntaxCheck.toValidSession(sessions);

                    CourseManager.generateCourseList();
                    modify_course(key, name, code, sessions, prereq);

                    textToBe.setText("");
                    textName.setText("");
                    textCode.setText("");
                    textPrereq.setText("");
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
        binding = FragmentAdminUpdateCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    void modify_course(String key, String course_name, String course_code, String offering_sess, String prereq) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("course");

        Query query_user = reference.orderByChild("courseName").equalTo(key);
        query_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Checks if username exists
                if (snapshot.getValue() != null) {

                    HashMap Course = new HashMap();
                    Course.put("courseCode", course_code);
                    Course.put("courseName", course_name);
                    Course.put("offeringSessions", offering_sess);
                    Course.put("prerequisites", prereq);


                    dRef = FirebaseDatabase.getInstance().getReference("course");

                    if (key.equals(course_name)) {
                        dRef.child(key).updateChildren(Course).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {
                                    binding.inputCourseToBeUpdated.setText("");
                                    binding.updateCourseName.setText("");
                                    binding.updateCourseCode.setText("");
                                    binding.updateOfferingSessions.setText("");
                                    binding.updatePrerequisites.setText("");
                                    Toast myToast = Toast.makeText(getActivity(), "Course data updated!", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            }

                        });
                    } else {

                        dRef.child(course_name).updateChildren(Course).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (task.isSuccessful()) {
                                    binding.inputCourseToBeUpdated.setText("");
                                    binding.updateCourseName.setText("");
                                    binding.updateCourseCode.setText("");
                                    binding.updateOfferingSessions.setText("");
                                    binding.updatePrerequisites.setText("");
                                    Toast myToast = Toast.makeText(getActivity(), "Course data updated!", Toast.LENGTH_SHORT);
                                    myToast.show();
                                }
                            }

                        });

                        dRef.child(key).removeValue();

                    }

                } else {
                    binding.inputCourseToBeUpdated.setText("");
                    Toast myToast = Toast.makeText(getActivity(), "The course you wish to update does not exist!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }
}

//    FragmentAdminUpdateCourseBinding binding;
//    DatabaseReference dRef;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        binding = FragmentAdminUpdateCourseBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.offUpdateCourseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // String key = binding.inputCourseToBeUpdated.getText().toString();
//                String course_name = binding.updateCourseName.getText().toString();
//                String course_code = binding.updateCourseCode.getText().toString();
//                String offering_sess = binding.updateOfferingSessions.getText().toString();
//                String prereq = binding.updatePrerequisites.getText().toString();
//
//                modify(course_name, course_code, offering_sess, prereq);
//            }
//        });
//    }
//
//    private void modify(String course_name, String course_code, String offering_sess, String prereq) {
//
//        HashMap Course = new HashMap();
//        Course.put("courseCode", course_code);
//        Course.put("courseName", course_name);
//        Course.put("offeringSessions", offering_sess);
//        Course.put("prerequisites", prereq);
//
//        dRef = FirebaseDatabase.getInstance().getReference("course");
//        dRef.child(course_name).updateChildren(Course).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if (task.isSuccessful()) {
//                    binding.updateCourseName.setText("");
//                    binding.updateCourseCode.setText("");
//                    binding.updateOfferingSessions.setText("");
//                    binding.updatePrerequisites.setText("");
//                    Toast.makeText(AdminUpdateCourse.this, "Course information updated", Toast.LENGTH_SHORT).show();
//                } else if (course_name.isEmpty() || course_code.isEmpty() || offering_sess.isEmpty() || prereq.isEmpty()) {
//                    Toast.makeText(AdminUpdateCourse.this, "Please fill in all the fields with original or updated information", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//        });
//    }
//}

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AdminUpdateCourse() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AdminUpdateCourse.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AdminUpdateCourse newInstance(String param1, String param2) {
//        AdminUpdateCourse fragment = new AdminUpdateCourse();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_admin_update_course, container, false);
//    }
//}
