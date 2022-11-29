package com.example.b07projectapp;
import java.util.*;
public class CourseList {
    static ArrayList <String> courseCode = new ArrayList<String>();
    static ArrayList <ArrayList<String>> sessions = new ArrayList<ArrayList<String>>();
    static ArrayList <ArrayList<String>> prerequisites = new ArrayList<ArrayList<String>>();

    public static void addCourse (String course, String session, String prereq) {

        courseCode.add(course);

        ArrayList <String> sessionsList = new ArrayList<> (Arrays.asList(session.split(",")));
        sessions.add(sessionsList);

        ArrayList <String> prereqList = new ArrayList<> (Arrays.asList(prereq.split(",")));
        prerequisites.add(prereqList);
    }

    public static ArrayList<String> getCourseCodes(){
        return courseCode;
    }
    public static ArrayList <ArrayList<String>> getSessions(){
        return sessions;
    }
    public static ArrayList <ArrayList<String>> getPrereqs(){
        return prerequisites;
    }

}