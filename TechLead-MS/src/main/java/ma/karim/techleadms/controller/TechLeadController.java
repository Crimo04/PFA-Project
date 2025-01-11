package ma.karim.techleadms.controller;

import jakarta.ws.rs.Path;
import ma.karim.techleadms.dto.CandidatureDTO;
import ma.karim.techleadms.dto.EntretienDTO;
import ma.karim.techleadms.entity.TechLead;
import ma.karim.techleadms.services.TechLeadservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TECH")
public class TechLeadController {
    @Autowired
    TechLeadservice service;

    @GetMapping("/candidatures")
    public List<CandidatureDTO> afficherCandidature() {
     return service.returnAllCandidatures();
    }

    @GetMapping("/api/all")
    @ResponseBody
    public List<TechLead> getall() {
        return service.getallTechLeads();
    }


    @GetMapping("/api/{id}")
    @ResponseBody
    public TechLead getTechLeadByID(@PathVariable Long id) {
        return service.getTechLeadBYID(id);
    }


    @GetMapping("/api/entretien/{id}")
    @ResponseBody
    public List<EntretienDTO> getallEntretienbyIdTechLead(@PathVariable Long id) {
        return service.GetallEntretien(id);
    }

    @PostMapping("/api/add")
    @ResponseBody
    public TechLead addtechlead(@RequestBody TechLead techLead) {

        return service.addTechLead(techLead);
    }

}
