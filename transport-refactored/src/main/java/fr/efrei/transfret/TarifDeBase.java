package fr.efrei.transfret;

import java.util.Map;

/**
 * Le coeur de la chaîne de décorateurs : délègue à la stratégie de tarif du mode demandé.
 */
public class TarifDeBase implements CalculPrix {

    private final Map<ModeTransport, TarifTransport> tarifs;

    public TarifDeBase(Map<ModeTransport, TarifTransport> tarifs) {
        this.tarifs = tarifs;
    }

    @Override
    public double calculer(DemandePrix demande) {
        TarifTransport tarif = tarifs.get(demande.mode());
        if (tarif == null) {
            throw new IllegalArgumentException("Mode inconnu : " + demande.mode());
        }
        return tarif.prixDeBase(demande);
    }
}
