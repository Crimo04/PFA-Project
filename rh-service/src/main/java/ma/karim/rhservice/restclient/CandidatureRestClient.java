package ma.karim.rhservice.restclient;

import ma.karim.rhservice.dto.CandidatDTO;
import ma.karim.rhservice.dto.CandidatureDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "Candidat-MS", url = "${candidat.service.url}")
public interface CandidatureRestClient {
    @GetMapping(value = "/candidature/api/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<CandidatureDTO> getallCandidatures();

    @GetMapping("/candidature/api/{id}")
    CandidatureDTO CandidatureByID(@PathVariable Long id);

    @GetMapping("/candidature/api/validate/{id}")
     CandidatureDTO ValidateCandidature(@PathVariable Long id);

    @GetMapping("/candidature/api/refuse/{id}")
     CandidatureDTO RefuseCandidature(@PathVariable Long id);







}