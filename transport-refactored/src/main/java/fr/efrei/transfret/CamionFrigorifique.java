package fr.efrei.transfret;

import java.util.EnumSet;

/**
 * Camion réservé aux produits réfrigérés. Au lieu de refuser les colis au chargement (ce qui empêchait de
 * l'utiliser à la place d'un Camion), il n'accepte que le réfrigéré dans {@link #peutPrendreEnCharge}.
 */
public class CamionFrigorifique extends Camion implements Refrigerable {

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, EnumSet.of(TypeMarchandise.REFRIGERE));
    }

    @Override
    public void partir(int distanceKm) {
        super.partir(distanceKm);
        activerRefrigeration(TEMPERATURE_CONSERVATION);
    }

    @Override
    public void activerRefrigeration(double temperature) {
        System.out.println("Réfrigération activée à " + temperature + "°C sur " + immatriculation);
    }
}
