package fr.efrei.transfret;

/**
 * Calcule le prix HT avec la chaîne de décorateurs reçue (voir {@link AssemblagePrix}), arrondi au centime.
 */
public class CalculateurPrix {

    private final CalculPrix chaine;

    public CalculateurPrix(CalculPrix chaine) {
        this.chaine = chaine;
    }

    public double calculerPrixHT(DemandePrix demande) {
        return Montant.arrondirAuCentime(chaine.calculer(demande));
    }
}
