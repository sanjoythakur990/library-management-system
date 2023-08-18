package com.example.librarymanagementsystem.Models;

import com.example.librarymanagementsystem.Enums.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    public Book(String title, Boolean isAvailable, Genre genre, Date publicationDate, Integer price) {
        this.title = title;
        this.isAvailable = isAvailable;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    @Column(unique = true)
    private String title;
    private Boolean isAvailable;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Date publicationDate;
    private Integer price;

    @ManyToOne
    @JoinColumn
    private Author author;
    // Unidirectional Mapping

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<Transaction> transactionList=new ArrayList<>();

}
