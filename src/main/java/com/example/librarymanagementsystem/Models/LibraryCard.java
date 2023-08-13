package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardNo;
    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;
    private Integer noOfBooksIssued;
    // Foreign key to be added int the child class
    @OneToOne
    @JoinColumn
//    @PrimaryKeyJoinColumn(name="rollNo")
    private Student student;
    // the child class will have the unidirectional mapping for sure(mandatory).
}
