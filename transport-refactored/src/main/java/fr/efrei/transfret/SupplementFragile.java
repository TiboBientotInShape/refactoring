package fr.efrei.transfret;

public class SupplementFragile extends DecorateurPrix {

    private static final double COEF = 1.15;

    public SupplementFragile(CalculPrix calculEnveloppe) {
        super(calculEnveloppe);
    }

    @Override
    public double calculer(DemandePrix demande) {
        double prix = calculEnveloppe.calculer(demande);
        if (demande.contient(TypeMarchandise.FRAGILE)) {
            prix = prix * COEF;
        }
        return prix;
    }
}
