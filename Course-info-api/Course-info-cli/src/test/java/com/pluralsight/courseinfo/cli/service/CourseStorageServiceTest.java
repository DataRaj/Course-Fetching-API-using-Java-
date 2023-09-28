package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.Repository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseStorageServiceTest {

    @Test
    void storePluralsightCourses() {
        Repository repository = new InMemoryCourseRepository();
        CourseStorageService courseStorageService = new CourseStorageService(repository);

        PluralSightCourse psCourse = new PluralSightCourse
                ("1","Title 1","01:40:00","/%s",true);
        courseStorageService.storePluralsightCourses(List.of(psCourse));
        Course expected = new Course("1", "Title 1", 100 , "/%s", Optional.empty());
        assertEquals(List.of(expected),repository.getAllCourses());

    }

    static class InMemoryCourseRepository implements Repository{
        private final List<Course> courses = new ArrayList<>();


        @Override
        public void saveCourse(Course course) {
            courses.add(course);
        }

        @Override
        public List<Course> getAllCourses() {
            return courses;
        }

        @Override
        public void addNotes(String id, String notes) {
            throw new UnsupportedOperationException();
        }
    }
}