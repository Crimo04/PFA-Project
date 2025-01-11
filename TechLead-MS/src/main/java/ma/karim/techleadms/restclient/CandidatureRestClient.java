package ma.karim.techleadms.restclient;

import ma.karim.techleadms.dto.CandidatureDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "Candidat-MS", url = "${candidat.service.url}")
public interface CandidatureRestClient {
    @GetMapping("/candidature/api/allvalidatetechlead")
    List<CandidatureDTO> getallvalidatetechleadCandidature();


    @GetMapping("/candidature/api/validatefinal/{id}")
    public CandidatureDTO validatetechleadCandidature( @PathVariable Long id) ; // VALIDER CANDIDATURE POUR EMBAUCHE OU PFE



}