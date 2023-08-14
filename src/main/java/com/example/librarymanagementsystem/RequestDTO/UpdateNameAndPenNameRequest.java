package com.example.librarymanagementsystem.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNameAndPenNameRequest {
    private Integer authorId;
    private String newName;
    private String newPenName;
}
