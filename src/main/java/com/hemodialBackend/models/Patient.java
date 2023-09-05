package com.hemodialBackend.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Size(max=10)
    private String numeroAssurePrefix;
    
    @Column 
    @Size(max=2)
    private String numeroAssureSuffix;

    @Column
    @Enumerated(EnumType.STRING)
    private TypePatient type;

    @Column
    @Enumerated(EnumType.STRING)
    private EtatPatient etat;

    @ManyToOne
    private Clinique clinique;

    @ManyToOne
    private Caisse caisse;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<PriseEnCharge> priseEnCharges;

}
