package ma.karim.techleadms.restclient;

import ma.karim.techleadms.dto.CandidatureDTO;
import ma.karim.techleadms.dto.EntretienDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "rh-service", url = "http://localhost:8084")
public interface EntretienRestClient {
    @GetMapping("RH/techlead/entretien/{id}")
    public List<EntretienDTO> GetallEntretien(@PathVariable Long id);
}