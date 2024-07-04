package com.exam.usercrud.repository;

import com.exam.usercrud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Custom queries can be added here if needed

}