package fr.efrei.transfret;

public class TarifMer implements TarifTransport {

    private static final double PRIX_KM = 0.5;
    private static final double PRIX_KG = 0.03;
    private static final double MINIMUM = 500;

    @Override
    public double prixDeBase(DemandePrix demande) {
        double prix = demande.distance() * PRIX_KM + demande.poidsTotal() * PRIX_KG;
        if (prix < MINIMUM) {
            prix = MINIMUM;
        }
        return prix;
    }
}
