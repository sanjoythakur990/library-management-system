package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.Department;
import com.example.librarymanagementsystem.Enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id // used for marking primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollNo;
    private String name;
    private int age;
    @Enumerated(value=EnumType.STRING)    // tells mysql to take this enums as string because mysql don't understand user defined datatypes
    private Gender gender; // this gender variable is of user defined datatype: MALE,FEMALE(case sensitive)
    @Enumerated(value=EnumType.STRING)
    private Department department;
    @Column(unique = true)
    private String emailId;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    private LibraryCard libraryCard;
}
