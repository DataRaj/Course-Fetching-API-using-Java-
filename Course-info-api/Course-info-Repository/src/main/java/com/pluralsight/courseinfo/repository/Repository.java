package com.pluralsight.courseinfo.repository;

import com.pluralsight.courseinfo.domain.Course;

import java.util.List;

public interface Repository {
    void saveCourse(Course course);

    List<Course> getAllCourses();

    void addNotes(String id,String notes);
    static Repository openCourseRepository(String databaseFile){
        return new CourseJDBCRepository(databaseFile);
    }

}
