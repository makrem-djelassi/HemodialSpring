package com.hemodialBackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriseEnCharge extends AbstractEntity{
    @Column
    private String nom;

    @Column
    private String codeBureau;

    @Column
    private String annee;

    @Column
    private String pec;

    @Column
    @Enumerated(EnumType.STRING)
    private TypePec typePec = TypePec.F0;

    @ManyToOne
    private Patient patient;


}
