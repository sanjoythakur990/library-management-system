package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Enums.Genre;
import com.example.librarymanagementsystem.RequestDTO.AddBookRequestDTO;
import com.example.librarymanagementsystem.ResponseDTO.BookResponseDTO;
import com.example.librarymanagementsystem.Services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getByGenre")
    public ResponseEntity getBookListByGenre(@RequestParam("genre")Genre genre){
        List<BookResponseDTO> response=bookService.getBooksByGenre(genre);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
