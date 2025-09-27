package com.test.rest_demo.controller;

import com.test.rest_demo.pojo.Employee;
import com.test.rest_demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get")
    public List<Employee> getEmployees() {
        List<Employee> emps = employeeService.getEmployees();
        System.out.println("Fetched-------------------------->>");
        System.out.println(emps.toString());
        return emps;
    }

    @PostMapping("/save")
    public Employee saveEmployee(@RequestBody Employee employee){
        System.out.println("Saved-------------------------->>");
        System.out.println(employee.toString());
        return employeeService.saveEmployee(employee);
    }
}