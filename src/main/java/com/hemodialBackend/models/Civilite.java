package com.hemodialBackend.models;

//public enum Civilite {
// M, Mme
//}

public enum Civilite {
    MR("Monsieur"),
    MME("Madame"),
    MLLE("Mademoiselle");

    private final String label;

    Civilite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}