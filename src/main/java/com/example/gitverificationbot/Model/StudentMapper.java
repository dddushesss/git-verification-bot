package com.example.gitverificationbot.Model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setFirs_Name(rs.getString("First_Name"));
        student.setLast_Name(rs.getString("Last_Name"));
        student.setRepository(rs.getString("Repository"));
        student.setGitLogin(rs.getString("GitLogin"));
        student.setId(rs.getInt("id"));
        return student;
    }
}
