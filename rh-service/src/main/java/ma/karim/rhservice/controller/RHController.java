package ma.karim.rhservice.controller;
import ma.karim.rhservice.dto.CandidatureDTO;
import ma.karim.rhservice.dto.TechLeadDTO;
import ma.karim.rhservice.entities.Entretien;
import ma.karim.rhservice.services.Rhservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/RH")
public class RHController {
    @Autowired
    Rhservice service;

    @GetMapping("/candidatures")
    public List<CandidatureDTO> afficherCandidature() {
     return service.returnAllCandidatures();
    }
    @GetMapping("/candidatures/{id}")
    public CandidatureDTO afficherCandidatureByID(@PathVariable Long id) {
        return service.CandidatureByid(id);
    }

    @GetMapping("/candidatures/validate/{id}")
    public CandidatureDTO ValidateCandidature(@PathVariable Long id) {
        return service.ValidateCandidature(id);
    }

    @GetMapping("/candidatures/refuse/{id}")
    public CandidatureDTO RefuseCandidature(@PathVariable Long id) {
        return service.RefuseCandidature(id);
    }

// je l'ai recuperer depuis ms - techlead
    @GetMapping("/techlead/all")
    public List<TechLeadDTO> getalltechlead() {

        return service.getAllTechLeads();
    }

//SERA ENVOYER AU MS DE TECHLEAD
    @GetMapping("/techlead/entretien/{id}")
    @ResponseBody
    public List<Entretien> GetallEntretienByID(@PathVariable Long id) {

        return service.GetEntretienByIDTechLead(id);
    }


    @PostMapping("/entretien/add")
    @ResponseBody
    public Entretien addentretien(@RequestBody Entretien entretien) {

        return service.addEntretien(entretien);
    }


    @GetMapping("/entretien/all")
    @ResponseBody
    public List<Entretien> allentretien() {

        return service.GetAllEntretien();
    }



}
