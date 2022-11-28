package com.example.demo.Student;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;


//  To map this student to our database the annotation @Entity is used;
//  To create a table inside the database the annotation @Table is used
@Entity
@Table
public class Student {
//  Automatically creates an id for the entity;
    @Id

//  Creates a sequence in the database unique identifier;
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    private long id;
    private String Name;
    private String email;
    private LocalDate dob;
    //  There is no need a column in database
    @Transient
    private Integer age;

    public Student() {
    }

    public Student(String name, String email, LocalDate dob) {
        Name = name;
        this.email = email;
        this.dob = dob;
    }

    public Student(long id, String name, String email, LocalDate dob) {
        this.id = id;
        Name = name;
        this.email = email;
        this.dob = dob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Studant{" + "id=" + id + ", Name='" + Name + '\'' + ", email='" + email + '\'' + ", dob=" + dob + ", age=" + age + '}';
    }
}
