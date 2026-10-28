package fr.efrei.transfret;

import java.util.EnumSet;

/**
 * Train électrique : pas de plein à faire, donc il n'est pas {@link Ravitaillable}.
 */
public class Train extends Vehicule {

    public Train(String immatriculation, double capaciteMax) {
        // le réfrigéré est déjà refusé par les règles du mode RAIL
        super(immatriculation, capaciteMax, ModeTransport.RAIL, EnumSet.allOf(TypeMarchandise.class));
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("Le train " + immatriculation + " circule sur " + distanceKm + " km de voies ferrées");
    }
}
