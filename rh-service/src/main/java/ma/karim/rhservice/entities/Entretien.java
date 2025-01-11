package ma.karim.rhservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.karim.rhservice.dto.TechLeadDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Entretien")
public class Entretien
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TechLead_id", unique = true)
    private Long techLeadId;


    @Column(name = "date_entretien", nullable = true)
    LocalDate dateEntretien;


    @Column(name = "heure_entretien", nullable = true)
    LocalTime HeureEntretien;


    @Transient
    private TechLeadDTO TechLead;


}
