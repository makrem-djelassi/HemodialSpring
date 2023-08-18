package com.hemodialBackend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class Adresse {

    private String ville;
    private String codePostal;
    private String pays;
    private String adresseLigne1;
    private String adresseLigne2;

    // Constructeurs, getters, setters et autres méthodes éventuelles
}