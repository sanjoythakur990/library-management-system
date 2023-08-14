package com.example.librarymanagementsystem.Services;

import com.example.librarymanagementsystem.Models.LibraryCard;
import com.example.librarymanagementsystem.Models.Student;
import com.example.librarymanagementsystem.Repositories.CardRepository;
import com.example.librarymanagementsystem.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryCardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private StudentRepository studentRepository;
    public String addCard(LibraryCard card){
        cardRepository.save(card);
        return "Card has been added to the database";
    }
    public String associateToStudent(Integer cardId, Integer rollNo) throws Exception{
        //Student should exist
        if(!studentRepository.existsById(rollNo))
            throw new Exception("Student Id is invalid.");
        // Card should also exist
        if(!cardRepository.existsById(cardId))
            throw new Exception("Card No is invalid");
        // I need to update the FK variables
        Optional<Student> optionalStudent=studentRepository.findById(rollNo);
        Student student=optionalStudent.get();

        Optional<LibraryCard> optionalLibraryCard=cardRepository.findById(cardId);
        LibraryCard libraryCard=optionalLibraryCard.get();

         // Set the students obj in card obj
        libraryCard.setStudent(student);
        // since it's a bidirectional mapping set card obj in student obj too
        student.setLibraryCard(libraryCard);
        // any obj that has been updated should be saved
        // Save both of the objs : since both were updated
//        cardRepository.save(libraryCard); // card repo saving can be skipped bcz of cascading effect in student class
        studentRepository.save(student);
        return "Student and Card saved successfully";
    }
}
