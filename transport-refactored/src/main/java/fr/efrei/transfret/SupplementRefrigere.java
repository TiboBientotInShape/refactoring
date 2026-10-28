package fr.efrei.transfret;

public class SupplementRefrigere extends DecorateurPrix {

    private static final double COEF = 1.20;

    public SupplementRefrigere(CalculPrix calculEnveloppe) {
        super(calculEnveloppe);
    }

    @Override
    public double calculer(DemandePrix demande) {
        double prix = calculEnveloppe.calculer(demande);
        if (demande.contient(TypeMarchandise.REFRIGERE)) {
            prix = prix * COEF;
        }
        return prix;
    }
}
