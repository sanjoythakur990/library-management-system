package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.CustomExceptions.BookNotAvailableException;
import com.example.librarymanagementsystem.CustomExceptions.BookNotFoundException;
import com.example.librarymanagementsystem.Enums.CardStatus;
import com.example.librarymanagementsystem.Enums.TransactionStatus;
import com.example.librarymanagementsystem.Enums.TransactionType;
import com.example.librarymanagementsystem.Models.Book;
import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Transaction;
import com.example.librarymanagementsystem.Repositories.AuthorRepository;
import com.example.librarymanagementsystem.Repositories.BookRepository;
import com.example.librarymanagementsystem.Repositories.CardRepository;
import com.example.librarymanagementsystem.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Value("${book.maxLimit}")
    private Integer maxBookLimit;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CardRepository cardRepository;
    public String issueBook(Integer bookId, Integer cardId) throws Exception{
        Transaction transaction=new Transaction(TransactionStatus.PENDING, TransactionType.ISSUE,0);
        // Book related exception handling
        Optional<Book> optionalBook=bookRepository.findById(bookId);
        if(!optionalBook.isPresent())
            throw new BookNotFoundException("Book Id is incorrect");
        Book book=optionalBook.get();
        if(book.getIsAvailable()==Boolean.FALSE)
            throw new BookNotAvailableException("Book is not available");

        // Card related exception handling
        Optional<LibraryCard> optionalLibraryCard=cardRepository.findById(cardId);
        if(!optionalLibraryCard.isPresent())
            throw new Exception("Card Id entered is invalid");
        LibraryCard card= optionalLibraryCard.get();
        if(!card.getCardStatus().equals(CardStatus.ACTIVE)) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Card is not in right status");
        }

        if(card.getNoOfBooksIssued()==maxBookLimit) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Already max limit books are issued");
        }

        // we have reached to a success point
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        // update the card and book entity
        book.setIsAvailable(Boolean.FALSE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()+1);

        // we need to do unidirectional mapping
        transaction.setBook(book);
        transaction.setLibraryCard(card);
        // Now we are saving transaction beforehand and not relying on cascading effect because it would create
        // 2 separate transactions, which is wrong. that's why we are generating transaction with Id first then
        // adding that to the list of parent classes. This way the transaction only gets updated, not duplicated by
        // cascading effect of the save function in parent entities.
        Transaction newTransactionWithId= transactionRepository.save(transaction);
        // we need to do in parent classes too
        book.getTransactionList().add(newTransactionWithId); // it will update the existing transaction entity
        card.getTransactionList().add(newTransactionWithId); // it will update the existing transaction entity
        // save the parent entities
        bookRepository.save(book);
        cardRepository.save(card);
        return "Transaction has been saved successfully";
    }
}
