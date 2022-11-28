package com.example.b07projectapp;

public class Course {
    String courseName;
    String courseCode;
    String offeringSessions;
    String prerequisites;

    public Course(){
        //this constructor is required
    }

    public Course(String courseName, String courseCode, String offeringSessions, String prerequisites) {
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

    public String getOfferingSessions() {
        return offeringSessions;
    }

    public String getPrerequisites() {
        return prerequisites;
    }
}
