package com.hemodialBackend.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient  extends AbstractEntity{
	
    @Column
    @Enumerated(EnumType.STRING)
    private Civilite civlite;
    
    @Column
    private String nom;

    @Column
    private String prenom;
    
    @Column
    @Size(min=2)
    private String codeBureau;
    
    @Column
    @Size(min=10)
    private String numeroAssurePrefix;
    
    @Column 
    @Size(min=2)
    private String numeroAssureSuffix;

    @Column
    @Enumerated(EnumType.STRING)
    private EtatPatient etat;

    @ManyToOne
    private Clinique clinique;

}
