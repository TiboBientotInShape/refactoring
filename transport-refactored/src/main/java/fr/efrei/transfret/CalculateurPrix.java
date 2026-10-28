package fr.efrei.transfret;

import java.util.Map;

public class CalculateurPrix {

    private static final double SUPPLEMENT_FRAGILE = 1.15;
    private static final double SUPPLEMENT_DANGEREUX = 1.30;
    private static final double SUPPLEMENT_REFRIGERE = 1.20;
    private static final double MAJORATION_URGENCE = 1.5;

    private static final double TAUX_ASSURANCE = 0.02;
    private static final double MINIMUM_ASSURANCE = 15;

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> remises;

    public CalculateurPrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this.tarifs = tarifs;
        this.remises = remises;
    }

    public double calculerPrixHT(DemandePrix demande) {
        TarifTransport tarif = tarifs.get(demande.mode());
        if (tarif == null) {
            throw new IllegalArgumentException("Mode inconnu : " + demande.mode());
        }
        double prix = tarif.prixDeBase(demande);

        // suppléments
        if (demande.contient(TypeMarchandise.FRAGILE)) prix = prix * SUPPLEMENT_FRAGILE;
        if (demande.contient(TypeMarchandise.DANGEREUX)) prix = prix * SUPPLEMENT_DANGEREUX;
        if (demande.contient(TypeMarchandise.REFRIGERE)) prix = prix * SUPPLEMENT_REFRIGERE;
        if (demande.urgent()) prix = prix * MAJORATION_URGENCE;

        // remise client
        prix = remises.get(demande.typeClient()).appliquer(prix);

        // assurance
        if (demande.assurance()) {
            double montantAssurance = demande.valeurTotale() * TAUX_ASSURANCE;
            if (montantAssurance < MINIMUM_ASSURANCE) montantAssurance = MINIMUM_ASSURANCE;
            prix = prix + montantAssurance;
        }
        return Montant.arrondirAuCentime(prix);
    }
}
