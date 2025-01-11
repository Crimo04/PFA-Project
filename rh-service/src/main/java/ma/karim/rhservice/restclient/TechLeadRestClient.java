package ma.karim.rhservice.restclient;

import ma.karim.rhservice.dto.CandidatureDTO;
import ma.karim.rhservice.dto.TechLeadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "TechLead-MS", url = "${techlead.service.url}")
public interface TechLeadRestClient {

    @GetMapping("TECH/api/all")
    @ResponseBody
    List<TechLeadDTO> getall();

    @GetMapping("TECH/api/{id}")
    @ResponseBody
    TechLeadDTO getTechLeadByID(@PathVariable Long id);

}
