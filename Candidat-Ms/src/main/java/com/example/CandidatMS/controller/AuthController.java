package com.example.CandidatMS.controller;

import com.example.CandidatMS.config.CustomUserDetails;
import com.example.CandidatMS.dto.LoginRequest;
import com.example.CandidatMS.dto.UserDto;
import com.example.CandidatMS.entity.Candidature;
import com.example.CandidatMS.entity.User;
import com.example.CandidatMS.enums.Statut;

import com.example.CandidatMS.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;



//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }

//    // Registration endpoint
    @PostMapping("/register")
    public UserDto registerUser(@Valid @RequestBody UserDto userDto) {
        User existing = userService.findByEmail(userDto.getEmail());

       if (existing != null) {
            throw new RuntimeException("There is already an account registered with that email");
        }

        return userService.saveUser(userDto);
  }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Force la création d'une session
            HttpSession session = request.getSession(true);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            return ResponseEntity.ok()
                    .header("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly")
                    .body(Map.of(
                            "userId", userDetails.getId(),
                            "message", "Login successful",
                            "roles", userDetails.getAuthorities(),
                            "sessionId", session.getId()  // pour debug
                    ));

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, Authentication authentication) {
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Effacer le contexte de sécurité
        SecurityContextHolder.clearContext();

        // Log de débogage
        System.out.println("Logout successful for user: " +
                (authentication != null ? authentication.getName() : "Unknown"));

        return ResponseEntity.ok(Map.of(
                "message", "Logout successful"
        ));
    }


//    // Get all users
//    @GetMapping("/use")
//    public List<UserDto> getAllUsers() {
//        return userService.findAllUsers();
//    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get candidature by user ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        User user = userService.findbyid(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    // Add candidature
    @PostMapping("/candidature/{id}")
    public String addCandidature(
            @RequestParam("file") MultipartFile file,
            @PathVariable("id") Long candidatId) throws IOException {

        User candidat = userService.findbyid(candidatId);
        if (candidat == null) {
            throw new RuntimeException("User not found");
        }

        byte[] fileBytes = file.getBytes();
        userService.addCandidature(file, fileBytes, candidat);

        return "Application added successfully";
    }

    // View candidature
    @GetMapping("/candidature/view/{id}")
    public Candidature viewCandidature(@PathVariable Long id) {
        Optional<Candidature> candidature = userService.getCandidaturebyId(id);

        return candidature.orElseThrow(() -> new RuntimeException("Candidature not found"));
    }

    // Update candidature status
    @PutMapping("/candidature/{id}/status")
    public Candidature updateCandidatureStatus(
            @PathVariable Long id,
            @RequestBody Statut statut) {

        return userService.UpdateStatutCandidature(statut, id);
    }



//    @GetMapping("index")
//    public String home(){
//        return "index";
//    }
//
//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }
//
//    // handler method to handle user registration request
//    @GetMapping("register")
//    public String showRegistrationForm(Model model){
//        UserDto user = new UserDto();
//        model.addAttribute("user", user);
//        return "register";
//    }
//
//    // handler method to handle register user form submit request
//    @PostMapping("/register/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto user,
//                               BindingResult result,
//                               Model model){
//        User existing = userService.findByEmail(user.getEmail());
//        if (existing != null) {
//            result.rejectValue("email", null, "There is already an account registered with that email");
//        }
//        if (result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "register";
//        }
//        userService.saveUser(user);
//        return "redirect:/register?success";
//    }
//
//    @GetMapping("/users")
//    public String listRegisteredUsers(Model model){
//        List<UserDto> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "users";
//    }
//    @GetMapping("/candidature/{id}")
//    public String showDashboard(@PathVariable("id") Long userId, Model model) {
//        // Ajouter l'ID au modèle
//        model.addAttribute("userId", userId);
//        return "candidature"; // Nom du fichier HTML
//    }
//
//    @GetMapping("/candidature/addform/{id}")
//    public String afficherFormulaireAjoutCandidature(@PathVariable("id") Long candidatId, Model model) {
//        User candidat = userService.findbyid(candidatId);
//
//        if (candidat.getCandidature() != null) {
//            model.addAttribute("userId", candidatId);
//            return "redirect:/candidature/{id}";
//        }
//        model.addAttribute("candidatId", candidatId);
//        return "addcandidature";
//    }
//  @PostMapping("/candidature/add/{id}")
//    public String ajouterCandidature(@RequestParam("file") MultipartFile file,
//                                     @PathVariable("id") Long candidatId,
//                                     RedirectAttributes redirectAttributes , Model model) {
//        try {
//            User candidat = userService.findbyid(candidatId);
//            byte[] fileBytes = file.getBytes();
//            System.out.println("Bytes length: " + (fileBytes != null ? fileBytes.length : "null"));
//
//
//            userService.addCandidature(file,fileBytes,candidat);
//            redirectAttributes.addFlashAttribute("successMessage", "Application added successfully !");
//            return "redirect:/candidature/" + candidatId;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error class: " + e.getClass());
//            System.out.println("Error message: " + e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", "Erreur: " + e.getMessage());
//            return "redirect:/candidature/" + candidatId;
//        }
//    }
//
//    @GetMapping("/candidature/view/{id}")
//    public String afficherCandidature(@PathVariable Long id, Model model) {
//        Optional<Candidature> candidature = userService.getCandidaturebyId(id);
//
//        if (candidature.isEmpty()) {
//            model.addAttribute("userId", id);
//            return "candidaturefalse";
//        }
//
//        model.addAttribute("candidature", candidature.get());
//        return "candidatureaffichage";
//    }
//    @GetMapping("/candidature/update/{id}")
//    public String updateCandidature(@PathVariable Long id, @RequestParam Statut statut, Model model) {
//        try {
//            Candidature updatedCandidature = userService.UpdateStatutCandidature(statut, id);
//            model.addAttribute("candidature", updatedCandidature);
//            return "candidatureaffichage";
//        } catch (Exception e) {
//            model.addAttribute("error", "Erreur lors de la mise à jour");
//            return "error";
//        }
//    }



    @GetMapping("/candidature/api/all")
@ResponseBody
    public List<Candidature> getallCandidatures() {
      return userService.getAll();
    }

    @GetMapping("/candidature/api/{id}")
    public @ResponseBody Candidature CandidatureByID(@PathVariable Long id) {
        return userService.getCandidaturebyId(id).orElseThrow(
                () -> new RuntimeException("Candidature with id "+ id + " not found")
        );
    }


    @GetMapping("/candidature/api/validate/{id}")
    public @ResponseBody Candidature ValidateCandidature(@PathVariable Long id) {
        return userService.ValidateCandidature(id);
    }

    @GetMapping("/candidature/api/refuse/{id}")
    public @ResponseBody Candidature RefuseCandidature(@PathVariable Long id) {
        return userService.RefuseCandidature(id);
    }

    @GetMapping("/candidature/api/validatefinal/{id}")
    public @ResponseBody Candidature validatetechleadCandidature(@PathVariable Long id) {
        return userService.FinaleValidateCandidature(id);
    }

    @GetMapping("/candidature/api/allvalidatetechlead")
    public @ResponseBody List<Candidature> getallvalidatetechleadCandidature() {
        return userService.getAllcandidaturesValidate();
    }

}

