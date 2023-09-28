package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.repository.Repository;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class CourseServer {
    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI = "http://localhost:8080/";

    public static void main(String... args) {
        LOG.info("Starting the http server");
        Repository repository = Repository.openCourseRepository("./courses.db");
        ResourceConfig config = new ResourceConfig().register(new CourseResource(repository));
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),config);
    }
}
