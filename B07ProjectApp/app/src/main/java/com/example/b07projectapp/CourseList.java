package com.example.b07projectapp;
import java.util.*;
public class CourseList {
    static ArrayList<String> courseCode = new ArrayList<String>();
    static ArrayList<String> sessions = new ArrayList<String>();
    static ArrayList<String> prerequisites = new ArrayList<String>();

    public static void addCourse(String course, String session, String prereq) {
        courseCode.add(course);
        sessions.add(session);
        prerequisites.add(prereq);
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
}