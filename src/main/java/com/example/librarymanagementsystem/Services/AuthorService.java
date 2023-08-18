package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Repositories.AuthorRepository;
import com.example.librarymanagementsystem.RequestDTO.UpdateNameAndPenNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    public String addAuthor(Author author) throws Exception{
        // validation check
        if(author.getAuthorId()!=null)
            throw new Exception("Author Id should not be sent as a parameter");

        authorRepository.save(author);
        return "Author has been successfully added in the db";
    }
    public String updateNameAndPenName(UpdateNameAndPenNameRequest request) throws Exception{
        Optional<Author> authorOptional=authorRepository.findById(request.getAuthorId());
        if(!authorOptional.isPresent())
            throw new Exception("AuthorId is invalid");
        Author author=authorOptional.get();
        author.setName(request.getNewName());
        author.setPenName(request.getNewPenName());

        authorRepository.save(author);
        return "Author name and PenName has been updated";
    }
    public Author getAuthorById(Integer authorId){
        Author author=authorRepository.findById(authorId).get();
        return author;
    }
}
