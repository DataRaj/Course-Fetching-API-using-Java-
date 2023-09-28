package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.Repository;

import java.util.List;
import java.util.Optional;

public class CourseStorageService {
    private final Repository courseRepository;

    public CourseStorageService(Repository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public void storePluralsightCourses(List<PluralSightCourse> psCourses){
        for(PluralSightCourse psCourse : psCourses){
            Course course = new Course(
                    psCourse.id(),
                    psCourse.title(),
                    psCourse.durationInMinutes(),
//                    psCourse.contentURL()
// put this link later on when you are acutally going to implement the code
                    "/library/courses/java-se-17-big-picture",
                    Optional.empty()
                    );
            courseRepository.saveCourse(course);
        }
    }
}
