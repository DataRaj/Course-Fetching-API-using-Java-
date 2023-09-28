package com.pluralsight.courseinfo.server;


import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.Repository;
import com.pluralsight.courseinfo.repository.RepositoryException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Comparator;
import java.util.List;

@Path("/courses")
public class CourseResource {
    private static final Logger LOG  = LoggerFactory.getLogger(CourseResource.class);
    private final Repository repository;

    public CourseResource(Repository repository) {
        this.repository = repository;

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON
    )
    public List<Course> getCourses(){
        try {
            return repository
                    .getAllCourses()
                    .stream()
                    .sorted(Comparator.comparing(Course::ID))
                    .toList();
        }catch (RepositoryException e){
            LOG.error("Could not load the courses from the database", e);
            throw new NotFoundException();
        }
    }
    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addNotes(@PathParam("id") String id, String notes){
        repository.addNotes(id,notes);
    }
}
