package fr.efrei.transfret;

import java.util.Map;

/**
 * Applique la remise du client (stratégie de l'étape 5) sur le prix après suppléments et urgence.
 */
public class Remise extends DecorateurPrix {

    private final Map<TypeClient, PolitiqueRemise> remises;

    public Remise(CalculPrix calculEnveloppe, Map<TypeClient, PolitiqueRemise> remises) {
        super(calculEnveloppe);
        this.remises = remises;
    }

    @Override
    public double calculer(DemandePrix demande) {
        double prix = calculEnveloppe.calculer(demande);
        PolitiqueRemise remise = remises.get(demande.typeClient());
        if (remise == null) {
            throw new IllegalArgumentException("Pas de remise définie pour " + demande.typeClient());
        }
        return remise.appliquer(prix);
    }
}
