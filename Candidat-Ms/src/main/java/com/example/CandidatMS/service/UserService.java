package com.example.CandidatMS.service;

import com.example.CandidatMS.dto.UserDto;
import com.example.CandidatMS.entity.Candidature;
import com.example.CandidatMS.entity.User;
import com.example.CandidatMS.enums.Statut;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    User findbyid(Long id);
    Candidature addCandidature(MultipartFile file, byte[] cvpath , User e);

    public Long findUserIdByUsername(String username);
 //   public Candidature getCandidaturebyId(Long id);
 public Optional<Candidature> getCandidaturebyId(Long id);
    public List<Candidature> getAll();

    Candidature ValidateCandidature(Long id);
    Candidature RefuseCandidature(Long id);

   Candidature UpdateStatutCandidature(Statut statut , Long id);


   Candidature FinaleValidateCandidature(Long id);

   // Candidature GetCandidatureFinaleValidate (Long id);

    List<Candidature> getAllcandidaturesValidate ();

    public List<User> getAllUsers();




}
