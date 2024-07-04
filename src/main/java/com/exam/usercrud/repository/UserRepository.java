package com.exam.usercrud.repository;

import com.exam.usercrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Custom queries can be added here if needed

}