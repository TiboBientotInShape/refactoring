package fr.efrei.transfret;

public class TarifAir implements TarifTransport {

    private static final double PRIX_KM = 0.6;
    private static final double PRIX_KG_TAXABLE = 4.5;
    private static final double KG_PAR_M3 = 167;

    @Override
    public double prixDeBase(DemandePrix demande) {
        // le poids taxable est le plus grand entre le poids réel et le poids volumétrique
        double poidsTaxable = demande.poidsTotal();
        double poidsVolumetrique = demande.volumeTotal() * KG_PAR_M3;
        if (poidsVolumetrique > poidsTaxable) {
            poidsTaxable = poidsVolumetrique;
        }
        return demande.distance() * PRIX_KM + poidsTaxable * PRIX_KG_TAXABLE;
    }
}
