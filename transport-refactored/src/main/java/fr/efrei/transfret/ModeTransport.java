package fr.efrei.transfret;

import java.util.EnumSet;
import java.util.Set;

/**
 * Les modes de transport et leurs règles d'acceptation.
 */
public enum ModeTransport {
    ROUTE("routier", EnumSet.noneOf(TypeMarchandise.class), true),
    RAIL("ferroviaire", EnumSet.of(TypeMarchandise.REFRIGERE), true),
    MER("maritime", EnumSet.noneOf(TypeMarchandise.class), false),
    AIR("aérien", EnumSet.of(TypeMarchandise.DANGEREUX, TypeMarchandise.REFRIGERE), true);

    private final String libelle;
    private final Set<TypeMarchandise> marchandisesInterdites;
    private final boolean urgenceAutorisee;

    ModeTransport(String libelle, Set<TypeMarchandise> marchandisesInterdites, boolean urgenceAutorisee) {
        this.libelle = libelle;
        this.marchandisesInterdites = marchandisesInterdites;
        this.urgenceAutorisee = urgenceAutorisee;
    }

    public String getLibelle() {
        return libelle;
    }

    public boolean accepte(TypeMarchandise type) {
        return !marchandisesInterdites.contains(type);
    }

    public boolean isUrgenceAutorisee() {
        return urgenceAutorisee;
    }
}
