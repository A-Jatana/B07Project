package com.example.b07projectapp;

import java.util.List;

public class Course {
    String courseName;
    String courseCode;
    List<String> offeringSessions;
    List<Course> prerequisites;

    public Course(){
        //this constructor is required
    }

    public Course(String courseName, String courseCode, List<String> offeringSessions, List<Course> prerequisites) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.offeringSessions = offeringSessions;
        this.prerequisites = prerequisites;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<String> getOfferingSessions() { return offeringSessions; }

    public List<Course> getPrerequisites() { return prerequisites; }

}
