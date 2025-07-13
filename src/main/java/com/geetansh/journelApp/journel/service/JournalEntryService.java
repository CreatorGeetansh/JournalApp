package com.geetansh.journelApp.journel.service;

import com.geetansh.journelApp.journel.Repository.JournalEntryRepository;
import com.geetansh.journelApp.journel.entity.JournalEntry;
import com.geetansh.journelApp.journel.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

//    create this a transaction -> making this a transaction means run the entire function (say SaveEntry completely or do not run this function.
//    two benefits -> saved to both in single shot, if error occur then also it will run in sync.)
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add((saved));
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getall(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteByID(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));  //lambda expression in Spring Boot
            if (removed){
                userService.saveUser(user);  //save
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entries. try again in sometime.");
        }
    }

//    public List<JournalEntry> findByUserName(String userName){
//
//    }
}
