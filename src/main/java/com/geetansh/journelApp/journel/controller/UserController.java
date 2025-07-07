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
@RequestMapping("/user")
public class UserController {

     @Autowired
     private UserService userService;

     @GetMapping
     public  List<User> getAllUsers(){
         return userService.getall();
     }

     @PostMapping
     public void createUser(@RequestBody User user){
         userService.saveEntry(user);
     }

     @PutMapping("/{userName}")
     public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
         User userInDb = userService.findByUserName(userName);
         if (userInDb != null){
             userInDb.setPassword(user.getPassword());
             userService.saveEntry(userInDb);
         }
         return new ResponseEntity<>(HttpStatus.OK);
     }
}
