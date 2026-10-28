package fr.efrei.transfret;

public class SupplementUrgence extends DecorateurPrix {

    private static final double COEF = 1.5;

    public SupplementUrgence(CalculPrix calculEnveloppe) {
        super(calculEnveloppe);
    }

    @Override
    public double calculer(DemandePrix demande) {
        double prix = calculEnveloppe.calculer(demande);
        if (demande.urgent()) {
            prix = prix * COEF;
        }
        return prix;
    }
}
