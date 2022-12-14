package com.example.b07projectapp;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentCourses {
    static ArrayList<String> courseCode = new ArrayList<String>();
    static ArrayList<String> sessions = new ArrayList<String>();
    static ArrayList<String> prerequisites = new ArrayList<String>();
    static ArrayList<String> coursesToTake = new ArrayList<String>();
    static ArrayList<String> coursesToAdd = new ArrayList<String>();
    static String studentName;

    public static void addCourse(String course, String session, String prereq) {
        courseCode.add(course);
        sessions.add(session);
        prerequisites.add(prereq);
    }
    public static void setStudentName(String name){
        studentName = name;
    }

    public static String getStudentName(){
        return studentName;
    }
    public static ArrayList<String> getCourseCodes() {
        return courseCode;
    }

    public static ArrayList<String> getSessions() {
        return sessions;
    }

    public static ArrayList<String> getPrereqs() {
        return prerequisites;
    }

    public static ArrayList<ArrayList<String>> getSessionsAsList() {
        ArrayList<ArrayList<String>> sessionList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < sessions.size(); i++) {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(sessions.get(i).split(",")));
            sessionList.add(temp);
        }
        return sessionList;
    }

    public static ArrayList<ArrayList<String>> getPrereqsAsList() {
        ArrayList<ArrayList<String>> prereqList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < prerequisites.size(); i++) {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(prerequisites.get(i).split(",")));
            prereqList.add(temp);
        }
        return prereqList;
    }
    public static void setCoursesToTake(ArrayList<String> list){
        coursesToTake = list;
    }
    public static ArrayList<String> getCoursesToTake(){
        return coursesToTake;
    }
    public static void setCoursesToAdd(ArrayList<String> list){
        coursesToAdd = list;
    }
    public static ArrayList<String> getCoursesToAdd(){
        return coursesToAdd;
    }
}
