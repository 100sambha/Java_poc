package com.test.rest_demo.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Emp_ID")
    public Long id;

    @Column(name = "Name")
    public String name;
}