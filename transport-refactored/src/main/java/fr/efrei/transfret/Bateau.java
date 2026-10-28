package fr.efrei.transfret;

import java.util.EnumSet;
import java.util.Set;

public class Bateau extends Vehicule implements Ravitaillable, Refrigerable {

    private static final double KM_PAR_MILLE_NAUTIQUE = 1.852;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax, ModeTransport.MER, marchandises(conteneursRefrigeres));
    }

    private static Set<TypeMarchandise> marchandises(boolean conteneursRefrigeres) {
        if (conteneursRefrigeres) {
            return EnumSet.allOf(TypeMarchandise.class);
        }
        return EnumSet.complementOf(EnumSet.of(TypeMarchandise.REFRIGERE));
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        int milles = (int) (distanceKm / KM_PAR_MILLE_NAUTIQUE);
        System.out.println("Le navire " + immatriculation + " navigue sur " + milles + " milles nautiques");
        if (chargementContient(TypeMarchandise.REFRIGERE)) {
            activerRefrigeration(TEMPERATURE_CONSERVATION);
        }
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }

    @Override
    public void activerRefrigeration(double temperature) {
        System.out.println("Conteneurs frigorifiques réglés à " + temperature + "°C");
    }
}
