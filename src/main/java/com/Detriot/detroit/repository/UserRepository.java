package com.Detriot.detroit.repository;

import com.Detriot.detroit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Long> {
    List<User> findByEmail(String email);
}
