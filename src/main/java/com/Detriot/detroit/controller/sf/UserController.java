package com.Detriot.detroit.controller.sf;

import com.Detriot.detroit.entity.sf.User;
import com.Detriot.detroit.service.sf.UserService;
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
/*
        TODO:
              * Get All users !
              * get a user by id
              * Create a new user
              * update an  user
              * delete  an user by id

    */