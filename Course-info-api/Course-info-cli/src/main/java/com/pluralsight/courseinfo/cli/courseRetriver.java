package com.pluralsight.courseinfo.cli;

import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.courseRetrievalService;
import com.pluralsight.courseinfo.cli.service.PluralSightCourse;
import com.pluralsight.courseinfo.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.function.Predicate.not;

class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);
    public static void main(String[] args) {
//        this was only the trial of the fetching the API but we want to fetch a list of the courses that author might have
        LOG.info("hello there we are finally installed");
        if(args.length == 0 ){
            LOG.warn("Please provide author name as a first argument");
            return;
        }
        try{
            retrieveCourses(args[0]);

        }catch (Exception e){

            LOG.error("Unexpected Interrupt",e);
        }
    }


    private static final void retrieveCourses(String AuthorID){
        LOG.info("Retrieving Courses for author '{}'" , AuthorID);
        courseRetrievalService courseRetrievelService = new courseRetrievalService();

        Repository repository = Repository.openCourseRepository("./courses.db");
        CourseStorageService courseStorageService = new CourseStorageService(repository);

        List<PluralSightCourse> coursesToStore =  courseRetrievelService.getCoursesFor(AuthorID)
                .stream()
                .filter(not(PluralSightCourse::isRetired))
                .toList();
        LOG.info(/*courseRetrievalService.PS_URI + */ "Retrieved the  following {} Courses '{}'",coursesToStore.size(), coursesToStore);
        courseStorageService.storePluralsightCourses(coursesToStore);
        LOG.info("Coures are successfully stored");
    }
}
