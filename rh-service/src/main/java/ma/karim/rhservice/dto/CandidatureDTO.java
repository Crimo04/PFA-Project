package ma.karim.rhservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatureDTO {

    private Long id;

    private String statut;

    private LocalDateTime dateCandidature;

    private CandidatDTO candidat;

    private String fileName;  // nom du fichier

}


