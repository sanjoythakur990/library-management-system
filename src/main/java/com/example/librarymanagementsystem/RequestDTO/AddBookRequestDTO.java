package com.example.librarymanagementsystem.RequestDTO;

import com.example.librarymanagementsystem.Enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddBookRequestDTO {
    private String title;
    private Boolean isAvailable;
    private Genre genre;
    private Date publicationDate;
    private Integer price;
    private Integer authorId;
}
