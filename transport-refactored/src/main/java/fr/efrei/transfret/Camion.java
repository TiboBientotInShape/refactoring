package fr.efrei.transfret;

import java.util.EnumSet;
import java.util.Set;

public class Camion extends Vehicule implements Ravitaillable {

    // le réfrigéré part dans le camion frigorifique, le dangereux seulement avec la certification ADR
    private static final Set<TypeMarchandise> SANS_ADR = EnumSet.of(TypeMarchandise.STANDARD, TypeMarchandise.FRAGILE);
    private static final Set<TypeMarchandise> AVEC_ADR =
            EnumSet.of(TypeMarchandise.STANDARD, TypeMarchandise.FRAGILE, TypeMarchandise.DANGEREUX);

    public Camion(String immatriculation, double capaciteMax, boolean adr) {
        this(immatriculation, capaciteMax, adr ? AVEC_ADR : SANS_ADR);
    }

    protected Camion(String immatriculation, double capaciteMax, Set<TypeMarchandise> marchandisesAcceptees) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE, marchandisesAcceptees);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("Le camion " + immatriculation + " roule sur " + distanceKm + " km");
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }
}
