package com.pluralsight.courseinfo.domain;

import java.util.Optional;

public record Course(String ID, String name, long length, String url, Optional<String>notes) {
    public Course {
        filled(ID);
        filled(name);
        filled(url);
        notes.ifPresent(Course::filled);
    }
    private static void filled(String val){
        if(val=="" || val.isBlank() ){
            throw new IllegalArgumentException("No value is present");
        }
    }
}
