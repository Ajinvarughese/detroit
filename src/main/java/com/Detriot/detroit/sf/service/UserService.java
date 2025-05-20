package com.Detriot.detroit.sf.service;


import com.Detriot.detroit.dto.Login;
import com.Detriot.detroit.sf.entity.User;
import com.Detriot.detroit.sf.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    public final UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    //get all user
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    //get Specific user by id
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found At id:"+id));
    }


    //Create a new user
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Get user by email and verify password
    public User getUserByEmail(Login login) throws EntityNotFoundException {
        User findUser = userRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + login.getEmail()));
        if(login.getEncrypted()) {
            if(login.getPassword().equals(findUser.getPassword()) && login.getRole().equals(findUser.getRole())) {
                return findUser;
            }
            throw new IllegalArgumentException("Wrong credentials");
        }
        else if (passwordEncoder.matches(login.getPassword(), findUser.getPassword()) && login.getRole().equals(findUser.getRole())) {
            return findUser;
        }
        throw new IllegalArgumentException("Wrong credentials");
    }
    //update password
    public User updatePassword(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    // Update an existing user
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