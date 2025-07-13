package com.geetansh.journelApp.journel.controller;

import com.geetansh.journelApp.journel.entity.User;
import com.geetansh.journelApp.journel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveNewuser(user);
    }
}
