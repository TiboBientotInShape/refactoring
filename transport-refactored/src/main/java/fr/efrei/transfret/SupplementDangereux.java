package fr.efrei.transfret;

public class SupplementDangereux extends DecorateurPrix {

    private static final double COEF = 1.30;

    public SupplementDangereux(CalculPrix calculEnveloppe) {
        super(calculEnveloppe);
    }

    @Override
    public double calculer(DemandePrix demande) {
        double prix = calculEnveloppe.calculer(demande);
        if (demande.contient(TypeMarchandise.DANGEREUX)) {
            prix = prix * COEF;
        }
        return prix;
    }
}
