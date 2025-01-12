package ma.karim.techleadms.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class EntretienDTO
{

    private Long id;

    private Long techLeadId;



    private Long candidatureId;


   private LocalDate dateEntretien;

   private LocalTime HeureEntretien;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTechLeadId() {
        return techLeadId;
    }

    public void setTechLeadId(Long techLeadId) {
        this.techLeadId = techLeadId;
    }

    public LocalDate getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(LocalDate dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public LocalTime getHeureEntretien() {
        return HeureEntretien;
    }

    public void setHeureEntretien(LocalTime heureEntretien) {
        HeureEntretien = heureEntretien;
    }


    public Long getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(Long candidatureId) {
        this.candidatureId = candidatureId;
    }
}
