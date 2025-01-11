package com.example.CandidatMS.repository;

import com.example.CandidatMS.entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature,Long> {

}
