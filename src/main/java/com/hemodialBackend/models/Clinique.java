package com.hemodialBackend.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "affiliationCaisse"),  
		@UniqueConstraint(columnNames = "matriculeTVA") , 
		@UniqueConstraint(columnNames = "registreCommerce"),
		@UniqueConstraint(columnNames = "codeClinique"),
        @UniqueConstraint(columnNames = "email")
        })
public class Clinique extends AbstractEntity implements Serializable{

    private Integer codeClinique;
    
    @Size(max = 50)
    @Email
    private String email;
    
    private String label;
    
    private Integer codePrestation;
    
    private Integer bureauCaisse;
    
    private Integer affiliationCaisse;
    
    private String matriculeTva;
    
    private String registreCommerce;
    
    @Embedded
    private Adresse adresse;    
    private String tel;
    
    private String fax;
    
    @OneToOne
    @JoinColumn(name = "gerant")
    private User gerant;

    private String medcein;
    
    @Column
    @Enumerated(EnumType.STRING)
    private EtatClinique etat;

    @OneToMany(mappedBy = "clinique")
    @JsonIgnore
    private List<Patient> patient;
}
