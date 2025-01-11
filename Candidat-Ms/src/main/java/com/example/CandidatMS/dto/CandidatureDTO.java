package com.example.CandidatMS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CandidatureDTO {
    private Long id;
    private String fileName;
    private String statut;
    private LocalDateTime dateCandidature;
    private String cvBase64; // Contient les données PDF encodées en base64
}

