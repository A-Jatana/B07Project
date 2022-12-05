package com.example.b07projectapp;

import android.util.Log;

import java.util.ArrayList;

public class Timeline {
    static ArrayList<ArrayList<String>> finalCourseList = new ArrayList<>();
    static ArrayList<String> finalSessionList = new ArrayList<>();
    /*
    TODO Add course student has taken and total course list
     */
    static ArrayList<String> courseCode = CourseList.getCourseCodes();
    static ArrayList<ArrayList<String>> session = CourseList.getSessionsAsList();
    static ArrayList<ArrayList<String>> prereq = CourseList.getPrereqsAsList();

    //static ArrayList<String> chosenCourses;
    static ArrayList<String> coursesTaken = StudentCourses.getCourseCodes();

    static int year = 2022;
    public Timeline(){

    }


    //Return Courselist as a list of strings
    public static ArrayList<String> getCourseList(){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < finalCourseList.size(); i++){
            temp.add(String.join(", ",finalCourseList.get(i)));
        }
        return temp;
    }
    public static ArrayList<String> getSessionList (){
        return finalSessionList;
    }

    public static void generateTimeline (ArrayList<String> courses, int startingYear){
        year = startingYear;

        addYear();
        timeline(courses);
        addYearNumbers();

        for (int i = 0; i < finalCourseList.size(); i++){
            Log.i("TIMELINE", "Session: " + finalSessionList.get(i) + ", Courses: " + finalCourseList.get(i).toString());
        }
        for (int i = 0; i < coursesTaken.size(); i++){
            Log.i("TIMELINE", "Courses Taken: " + coursesTaken.get(i));
        }
        //Finds index of last non-empty element in list
        int index = -1;
        for (int i = 0; i< finalSessionList.size();i++) {
            if (!finalCourseList.get(i).isEmpty()) {
                index = i;
            }
        }
        //Removes all empty elements at the end of the list
        int length = finalSessionList.size();
        if (index != -1) {
            for (int i = length - 1; i > index; i--) {
                finalCourseList.remove(i);
                finalSessionList.remove(i);
            }
        }
    }

    public static void addYear(){
        finalSessionList.add("Fall");
        finalSessionList.add("Winter");
        finalSessionList.add("Summer");
        for (int i = 0; i < 3; i++) {
            finalCourseList.add(new ArrayList<>());
        }
    }

    public static void addYearNumbers() {
        int length = finalSessionList.size();

        finalSessionList.set(0, finalSessionList.get(0)+ " " + year);

        for (int i = 1; i < length - 2; i += 3) {
            year ++;
            finalSessionList.set (i, finalSessionList.get(i) + " " +(year));
            finalSessionList.set((i+1), finalSessionList.get(i+1) + " " + (year));
            finalSessionList.set((i+2), finalSessionList.get(i+2) + " " + (year));
        }

        if (!(length % 3 == 1)) {
            if (length % 3 == 0) {
                finalSessionList.set(length-2, finalSessionList.get(length-2) + " " + (year + 1));
            }
            finalSessionList.set(length-1,finalSessionList.get(length-1) + " " + (year + 1));
        }
    }

    public static void timeline(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (getIdxInTimeline(list.get(i)) == -1 && !coursesTaken.contains(list.get(i))) {//Course is not already in timeline
                if (!allPrereqInTimeline(list.get(i))){ //Not all prerequisites are in the timeline yet
                    //int index = getIndex(list.get(i)); //Finds corresponding index in coursecode list
                    int index = courseCode.indexOf(list.get(i));//Finds corresponding index in coursecode list
                    timeline(prereq.get(index));//Recursively calls the function to add the prereqs first
                }
                addToTimeline(list.get(i)); //Adds every course to the timeline
            }
        }
    }

    public static int getIndex(String item) {
        for (int i = 0; i< courseCode.size(); i++) {
            if (courseCode.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    //Courses are only added once all prereqs have been added
    public static void addToTimeline(String course) {

        int startIndex = findLastPrereq(course)+1;
        ArrayList<String> availableSessions = session.get(courseCode.indexOf(course));

        boolean foundSession = false;
        boolean addedCourse = false;

        while (!foundSession) {

            //Iterate through the three available sessions
            for (int i = 0; i< 3; i++) {
                //If there isn't enough available sessions to iterate through, add more
                if(startIndex + i >= finalSessionList.size()) {
                    addYear();
                }
                for (int j = 0; j<availableSessions.size();j++) {

                    if (availableSessions.get(j).equals(finalSessionList.get(startIndex+i))&& !addedCourse) {
                        foundSession = true;
                        addedCourse = true;
                        finalCourseList.get(startIndex+i).add(course);
                    }
                }
            }
        }
    }

    //This method is only used once all prereqs are added to the timeline
    public static int findLastPrereq(String course) {
        int index = courseCode.indexOf(course);
        ArrayList<String> prereqList = prereq.get(index);
        if (prereqList.get(0).equals("NONE")) {
            return -1;
        }

        int maxIdx = -1;

        for (int i = 0; i< prereqList.size();i++) {
            int temp = getIdxInTimeline(prereqList.get(i));
            if (temp > maxIdx) {
                maxIdx = temp;
            }
        }
        return maxIdx;
    }

    public static int getIdxInTimeline(String prereq) {
        for (int i = 0; i < finalCourseList.size(); i++) {
            ArrayList<String> temp = finalCourseList.get(i);
            for (int j = 0; j<temp.size();j++) {
                if (temp.get(j).equals(prereq)) {
                    return i;
                }

            }
        }
        return -1;
    }

    public static boolean allPrereqInTimeline(String course) {
        int index = getIndex(course);
        ArrayList<String> prereqList = prereq.get(index);

        if (prereqList.get(0).equals("NONE")) {
            return true;
        }
        for (int i = 0; i < prereqList.size(); i++) {
            if (getIdxInTimeline(prereqList.get(i))== -1) {
                return false;
            }
        }
        return true;
    }
}
