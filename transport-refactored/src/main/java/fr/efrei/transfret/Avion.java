package fr.efrei.transfret;

import java.util.EnumSet;

public class Avion extends Vehicule implements Ravitaillable {

    private static final int ALTITUDE_CROISIERE_PIEDS = 35000;

    public Avion(String immatriculation, double capaciteMax) {
        // les matières dangereuses et le réfrigéré sont déjà refusés par les règles du mode AIR
        super(immatriculation, capaciteMax, ModeTransport.AIR, EnumSet.allOf(TypeMarchandise.class));
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("L'avion " + immatriculation + " roule sur la piste");
        System.out.println("L'avion " + immatriculation + " vole à " + ALTITUDE_CROISIERE_PIEDS + " pieds");
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }
}
