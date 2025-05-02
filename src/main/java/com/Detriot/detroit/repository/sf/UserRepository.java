package com.Detriot.detroit.repository.sf;

import com.Detriot.detroit.entity.sf.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Long> {
    List<User> findByEmail(String email);
}
