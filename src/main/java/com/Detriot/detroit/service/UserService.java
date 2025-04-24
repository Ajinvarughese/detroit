package com.Detriot.detroit.service;

import com.Detriot.detroit.entity.User;
import com.Detriot.detroit.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //get all user
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //get Specific user by id
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found At id:"+id));
    }

    //Create a new user
    public User addUser(User user){
        return userRepository.save(user);
    }

    //update an existing user
    public User updateUser(Long id, User updateUser){
        return userRepository.findById(id).map( user -> {
            user.setFullName(updateUser.getFullName());
            user.setEmail(updateUser.getEmail());
            user.setPhone(updateUser.getPhone());
            user.setAddress(updateUser.getAddress());
            user.setOrganization(updateUser.getOrganization());
            user.setRole(updateUser.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new IllegalArgumentException("User not found with id:"+id));
    }

    //delete an user
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        userRepository.deleteById(id);
    }

}
/*
        TODO:
              * Get All users !
              * get Specific user by id
              * Create a new user
              * update an existing user
              * delete  an user by id

    */