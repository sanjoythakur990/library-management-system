package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.RequestDTO.AddBookRequestDTO;
import com.example.librarymanagementsystem.Services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody AddBookRequestDTO addBookRequestDTO){
        try{
            String result= bookService.addBook(addBookRequestDTO);
            return new ResponseEntity(result, HttpStatus.OK);
        }catch (Exception e) {
            log.error("Book couldn't be added ot the db " + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
