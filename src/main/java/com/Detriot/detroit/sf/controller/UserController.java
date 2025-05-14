package com.Detriot.detroit.sf.controller;

import com.Detriot.detroit.dto.Login;
import com.Detriot.detroit.sf.entity.User;
import com.Detriot.detroit.sf.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }
    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    //get a user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }

    // Log in user
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Login loginCred) {
        return ResponseEntity.ok(userService.getUserByEmail(loginCred.getEmail(), loginCred.getPassword()));
    }

    //update a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
