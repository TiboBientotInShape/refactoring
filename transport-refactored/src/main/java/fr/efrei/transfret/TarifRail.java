package fr.efrei.transfret;

public class TarifRail implements TarifTransport {

    private static final double PRIX_KM = 0.8;
    private static final double PRIX_KG = 0.07;
    private static final double MINIMUM = 150;

    @Override
    public double prixDeBase(DemandePrix demande) {
        double prix = demande.distance() * PRIX_KM + demande.poidsTotal() * PRIX_KG;
        if (prix < MINIMUM) {
            prix = MINIMUM;
        }
        return prix;
    }
}
