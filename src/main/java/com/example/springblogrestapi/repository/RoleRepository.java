package com.example.springblogrestapi.repository;

import com.example.springblogrestapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);
}
