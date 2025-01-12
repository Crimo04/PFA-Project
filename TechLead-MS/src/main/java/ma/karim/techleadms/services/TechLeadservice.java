package ma.karim.techleadms.services;


import ma.karim.techleadms.Repositories.TechLeadRepository;
import ma.karim.techleadms.dto.CandidatureDTO;
import ma.karim.techleadms.dto.EntretienDTO;
import ma.karim.techleadms.entity.TechLead;
import ma.karim.techleadms.restclient.CandidatureRestClient;
import ma.karim.techleadms.restclient.EntretienRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TechLeadservice {

    CandidatureRestClient restClient;

    TechLeadRepository techLeadRepository;

    EntretienRestClient entretienRestClient;

    @Autowired
    public TechLeadservice(CandidatureRestClient restClient , TechLeadRepository techLeadRepository,
                           EntretienRestClient entretienRestClient) {

        this.restClient = restClient;
        this.techLeadRepository = techLeadRepository;
        this.entretienRestClient =entretienRestClient;

    }

    public List<CandidatureDTO> returnAllCandidatures() {
        return restClient.getallvalidatetechleadCandidature();
    }

    public List<TechLead> getallTechLeads(){
        return techLeadRepository.findAll();
    }

    public TechLead getTechLeadBYID(Long id){
        return techLeadRepository.findById(id).get();
    }


    public List<EntretienDTO> GetallEntretien(Long id)
    {
         return entretienRestClient.GetallEntretienByID(id);
    }


    public TechLead addTechLead(TechLead techLead) {
        TechLead t = new TechLead();
        t.setNom(techLead.getNom());
        t.setEmail(techLead.getEmail());

        return techLeadRepository.save(t);
    }

}
