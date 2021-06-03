package com.example.gitverificationbot.Services;


import com.example.gitverificationbot.Model.Student;
import com.example.gitverificationbot.Model.StudentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseService {
    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;

    public DatabaseService(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
    }

    public List<Student> getStudents() {
        return jdbcTemplate.query("select * from Student", studentMapper);
    }

    public void deleteStudent(int id){
        String sqlQuery = "delete from Student where id =" + id;
        jdbcTemplate.update(sqlQuery);

    }
    public void addStudent(String data){
        String sql = "insert into Student (First_Name, Last_Name, GitLogin, Repository) values ('" + data + "')";
        jdbcTemplate.update(sql);
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student where id = " + id;
        return jdbcTemplate.queryForObject(sql, studentMapper);
    }
}
