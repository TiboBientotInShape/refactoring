package fr.efrei.transfret;

/**
 * Ajoute l'assurance (2 % de la valeur déclarée, 15 € minimum) si elle est demandée.
 */
public class Assurance extends DecorateurPrix {

    private static final double TAUX = 0.02;
    private static final double MINIMUM = 15;

    public Assurance(CalculPrix calculEnveloppe) {
        super(calculEnveloppe);
    }

    @Override
    public double calculer(DemandePrix demande) {
        double prix = calculEnveloppe.calculer(demande);
        if (demande.assurance()) {
            double montant = demande.valeurTotale() * TAUX;
            if (montant < MINIMUM) {
                montant = MINIMUM;
            }
            prix = prix + montant;
        }
        return prix;
    }
}
