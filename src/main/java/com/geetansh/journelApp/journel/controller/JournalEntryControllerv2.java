package com.geetansh.journelApp.journel.controller;

import com.geetansh.journelApp.journel.entity.JournalEntry;
import com.geetansh.journelApp.journel.entity.User;
import com.geetansh.journelApp.journel.service.JournalEntryService;
import com.geetansh.journelApp.journel.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getallJournalEnteriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if( all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryByID(@PathVariable ObjectId myId){
//      return journalEntryService.findById(myId).orElse(null);
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}") //? suggest any datatype
    public ResponseEntity<?> DeleteEntryByID(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteByID(myId, userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalByID(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry, @PathVariable String userName){
        JournalEntry oldentry = journalEntryService.findById(id).orElse(null);
        if(oldentry != null){
            oldentry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldentry.getTitle());
            oldentry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldentry.getContent());
            journalEntryService.saveEntry(oldentry);
            return new ResponseEntity<>(oldentry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
