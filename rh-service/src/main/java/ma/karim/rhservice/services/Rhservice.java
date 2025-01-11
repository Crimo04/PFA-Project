package ma.karim.rhservice.services;


import ma.karim.rhservice.dto.CandidatureDTO;
import ma.karim.rhservice.dto.TechLeadDTO;
import ma.karim.rhservice.entities.Entretien;
import ma.karim.rhservice.repositories.EntretienRepository;
import ma.karim.rhservice.restclient.CandidatureRestClient;
import ma.karim.rhservice.restclient.TechLeadRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class Rhservice {

    CandidatureRestClient restClient;

    TechLeadRestClient restClientT;


    EntretienRepository entretienRepository;

    @Autowired
    public Rhservice( CandidatureRestClient restClient , TechLeadRestClient restClientT , EntretienRepository entretienRepository ) {

        this.restClient = restClient;
        this.restClientT = restClientT;
        this.entretienRepository = entretienRepository;

    }

    public List<CandidatureDTO> returnAllCandidatures() {
        return restClient.getallCandidatures();
    }
    public CandidatureDTO CandidatureByid(Long id) {
        return restClient.CandidatureByID(id);
    }


    public CandidatureDTO ValidateCandidature(Long id) {
        return restClient.ValidateCandidature(id);
    }

    public CandidatureDTO RefuseCandidature(Long id) {
        return restClient.RefuseCandidature(id);
    }

    public List<TechLeadDTO> getAllTechLeads(){
        return  restClientT.getall();
    }

    public List<Entretien> GetEntretienByIDTechLead(Long id){
        return entretienRepository.findEntretienByTechLeadId(id);
    }

    public List<Entretien> GetAllEntretien(){
        return entretienRepository.findAll();
    }
    public Entretien addEntretien(Entretien entretien) {
        Entretien e = new Entretien();
        e.setTechLeadId(entretien.getTechLeadId());
        e.setDateEntretien(entretien.getDateEntretien());
        e.setHeureEntretien(entretien.getHeureEntretien());
        e.setTechLead(restClientT.getTechLeadByID(entretien.getTechLeadId()));
        return entretienRepository.save(e);
    }
}
