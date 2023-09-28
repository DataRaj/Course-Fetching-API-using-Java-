package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class courseRetrievalService {
    public static final String PS_URI= "https://app.pluralsight.com/profile/data/author/%s/all-content";

    private static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public List<PluralSightCourse> getCoursesFor(String authorID){
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(PS_URI.formatted(authorID)))
                .GET()
                .build();
        try{
            HttpResponse<String> response =   CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return  switch (response.statusCode()){
                case 200 -> toPluralsightCourses(response);

                case 404 -> List.of();
                default -> throw new RuntimeException("Pluralsight API failed with status code " + response);
            };
        }catch (IOException | InterruptedException e){
            throw new RuntimeException("Could not call PluralSight API", e);
        }

    }

    private List<PluralSightCourse> toPluralsightCourses(HttpResponse<String> response) throws JsonProcessingException {
        JavaType returnType = OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, PluralSightCourse.class);
        return OBJECT_MAPPER.readValue(response.body(),returnType);
    }
}
