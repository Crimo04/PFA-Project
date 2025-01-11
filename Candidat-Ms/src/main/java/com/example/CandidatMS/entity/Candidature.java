package com.example.CandidatMS.entity;


import com.example.CandidatMS.enums.Statut;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="candidatures")
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.Pending;
    @Column(name = "date_candidature")
    private LocalDateTime dateCandidature;
    @JsonIgnore
    @Column(name = "cv_path", columnDefinition = "bytea")  // "bytea" est le type PostgreSQL pour les données binaires
    private byte[] cvPath;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User candidat;
    @Column(name = "file_name")
    private String fileName;  // nom du fichier
    @PrePersist
    protected void onCreate() {
        this.dateCandidature = LocalDateTime.now();
    }

}
