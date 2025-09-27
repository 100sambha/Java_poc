package com.security.controller;

import com.security.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController {

    List<Student> listOfStuds = new ArrayList<>(Arrays.asList(
       new Student(1,"sambhaji",27),
       new Student(2,"pooja",19)
    ));

    @GetMapping("/students")
    public List<Student> getStudents(){
        return listOfStuds;
    }

    @GetMapping("/csrftoken")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken)request.getAttribute("_csrf");
    }

    @PostMapping("/students")
    public Boolean addStudent(@RequestBody Student student){
        return listOfStuds.add(student);
    }
}
