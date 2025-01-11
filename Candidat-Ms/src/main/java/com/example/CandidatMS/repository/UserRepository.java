package com.example.CandidatMS.repository;

import com.example.CandidatMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByNom(String nom);
}
