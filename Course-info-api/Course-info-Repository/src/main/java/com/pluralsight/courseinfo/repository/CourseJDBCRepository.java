package com.pluralsight.courseinfo.repository;

import com.pluralsight.courseinfo.domain.Course;
import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CourseJDBCRepository implements Repository{
    private static final String H2_DATABASE_URL =
            "jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql'";

    private final DataSource dataSource;

    public CourseJDBCRepository(String databaseFile){
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl(H2_DATABASE_URL.formatted(databaseFile));
        this.dataSource = jdbcDataSource;
    }
    private  static final String INSERT_COURSE= """
            MERGE INTO courses(id,name,length,url)
            VALUES(?,?,?,?)
            """;
    private static final String ADD_NOTES = """
            UPDATE course SET notes = ?
            WHERE id = ?
            """;
    @Override
    public void saveCourse(Course course) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_COURSE);
            statement.setString(1, course.ID());
            statement.setString(2, course.name());
            statement.setLong(3, course.length());
            statement.setString(4, course.url());
            statement.execute();
        }catch (SQLException e){
            throw new RepositoryException("Failed to save " + course, e);

        }
    }

    @Override
    public List<Course> getAllCourses() {
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSES");

            List<Course> allCourses = new ArrayList<>();
            while(resultSet.next()){
                Course course = new Course(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getString(4),
                        Optional.ofNullable(resultSet.getString(5))
                );
                allCourses.add(course);

            }
            return Collections.unmodifiableList(allCourses);
        }catch (SQLException e){
            throw new RepositoryException("Could not load the course", e);
        }

    }

    @Override
    public void addNotes(String id, String notes) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_COURSE);
            statement.setString(1, notes);
            statement.setString(2, id);
            statement.execute();
        }catch (SQLException e){
            throw new RepositoryException("Failed to add the notes " + id, e);

        }
    }
}
