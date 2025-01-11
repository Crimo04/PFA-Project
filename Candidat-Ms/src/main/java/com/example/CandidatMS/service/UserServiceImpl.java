package com.example.CandidatMS.service;

import com.example.CandidatMS.dto.CandidatureDTO;
import com.example.CandidatMS.dto.UserDto;
import com.example.CandidatMS.entity.Candidature;
import com.example.CandidatMS.entity.Role;
import com.example.CandidatMS.entity.User;
import com.example.CandidatMS.enums.Statut;
import com.example.CandidatMS.repository.CandidatureRepository;
import com.example.CandidatMS.repository.RoleRepository;
import com.example.CandidatMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private CandidatureRepository candidatureRepository;




    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder ,
                           CandidatureRepository candidatureRepository ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.candidatureRepository = candidatureRepository;

    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setNom(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getNom().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
    @Override
    public User findbyid(Long id) {
        return userRepository.findById(id).get();
    }
    @Override
    public Candidature addCandidature(MultipartFile file,byte[] cvpath , User e) {
        Candidature c = new Candidature();
        c.setCvPath(cvpath);
        c.setStatut(Statut.Pending);
        c.setCandidat(e);
        c.setFileName(file.getOriginalFilename());
        return candidatureRepository.save(c);
    }
  /*  @Override
    public Candidature getCandidaturebyId(Long id) {

        return candidatureRepository.findById(id).get();
    }
    */
  public Optional<Candidature> getCandidaturebyId(Long id) {
      return candidatureRepository.findById(id);
  }

    @Override
    public List<Candidature> getAll() {
        return candidatureRepository.findAll();
    }

    @Override
    public Candidature ValidateCandidature(Long id) {
       Candidature c = candidatureRepository.getById(id);
       c.setStatut(Statut.ValidForTechLead);
       return  candidatureRepository.save(c);
    }
    @Override
    public Candidature FinaleValidateCandidature(Long id) {
        Candidature c = candidatureRepository.getById(id);
        c.setStatut(Statut.Valid);
        return  candidatureRepository.save(c);
    }



    @Override
    public List<Candidature> getAllcandidaturesValidate() {

        return candidatureRepository.findAll().stream()
                .filter(x -> x.getStatut() == Statut.ValidForTechLead).toList();
        //  List<Candidature> candidatures =  new ArrayList<Candidature>();
//        List<Candidature> candidatures =  new ArrayList<Candidature>();
//        for( Candidature c : candidatures)
//        {
//            if(c.getStatut()==Statut.Valid)
//            {
//                candidaturesValidate.add(c);
//            }
//        }
//        return candidaturesValidate;
    }



    public Long findUserIdByUsername(String username) {
        return userRepository.findByNom(username).getId();
    }

    @Override
    public Candidature RefuseCandidature(Long id) {
        Candidature c = candidatureRepository.getById(id);
        c.setStatut(Statut.Refused);
        return  candidatureRepository.save(c);
    }

    @Override
    public Candidature UpdateStatutCandidature(Statut statut, Long id) {
        Candidature c = candidatureRepository.getById(id);
        c.setStatut(statut);
        return  candidatureRepository.save(c);
    }
}
