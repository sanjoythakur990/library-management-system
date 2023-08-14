package com.example.librarymanagementsystem.Controllers;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.RequestDTO.UpdateNameAndPenNameRequest;
import com.example.librarymanagementsystem.Services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
@Slf4j
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody Author author){
        try{
            String result= authorService.addAuthor(author);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (Exception e){
            log.error("Author couldn't be added to the db {}"+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateNameAndPenName")
    public String updateAuthorNameAndPenName(@RequestBody UpdateNameAndPenNameRequest updateNameAndPenNameRequest){
        try{
            String result=authorService.updateNameAndPenName(updateNameAndPenNameRequest);
            return result;
        }catch (Exception e){
            return "Author Id is invalid"+e.getMessage();
        }
    }

}
