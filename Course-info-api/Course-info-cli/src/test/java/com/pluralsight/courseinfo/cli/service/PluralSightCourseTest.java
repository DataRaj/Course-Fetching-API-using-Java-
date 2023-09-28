package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PluralSightCourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            02:23:23.3243, 143
            00:00:23, 0
            00:59:00, 59
            """)
    public void durationInMinutes(String input, long expected){
        PluralSightCourse course = new PluralSightCourse("id","title",input,"url",true);
        assertEquals(expected,course.durationInMinutes());
    }
}
