package com.geetansh.journelApp.journel.controller;

import com.geetansh.journelApp.journel.Repository.UserRepository;
import com.geetansh.journelApp.journel.entity.JournalEntry;
import com.geetansh.journelApp.journel.entity.User;
import com.geetansh.journelApp.journel.service.JournalEntryService;
import com.geetansh.journelApp.journel.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

     @Autowired
     private UserService userService;

     @Autowired
     private UserRepository userRepository;

    @GetMapping
    public  List<User> getAllUsers(){
        return userService.getall();
    }


     @PutMapping()
     public ResponseEntity<?> updateUser(@RequestBody User user){
         Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
         String userName = authentication.getName();

         User userInDb = userService.findByUserName(userName);
         if (userInDb != null){
             userInDb.setPassword(user.getPassword());
             userService.saveEntry(userInDb);
         }
         return new ResponseEntity<>(HttpStatus.OK);
     }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
