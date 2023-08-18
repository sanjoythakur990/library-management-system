package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.Author;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Repositories.AuthorRepository;
import com.example.librarymanagementsystem.RequestDTO.AddBookRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    public String addBook(AddBookRequestDTO request) throws Exception{
        // validation
        // authorId should be valid
        Optional<Author> optionalAuthor=authorRepository.findById(request.getAuthorId());
        if(!optionalAuthor.isPresent())
            throw new Exception("Author Id entered is incorrect");
        Author author=optionalAuthor.get();
        Book book=new Book(request.getTitle(), request.getIsAvailable(), request.getGenre(), request.getPublicationDate(),
                request.getPrice());
        // Only entities will go inside the db and only entities will come out from the db
        // Got the Book obj
        // need to set the FK variable
        // since it's a bidirectional mapping : need to set in both child and parent classes
        // set the parent entity in child class
        book.setAuthor(author);
        // setting in the parent
        List<Book> list=author.getBookList();
        list.add(book);
        author.setBookList(list);
        // now save only the parent, child will be saved automatically bcz of cascading effect
        authorRepository.save(author);
        return "Book has been successfully added and updated";
    }
}
