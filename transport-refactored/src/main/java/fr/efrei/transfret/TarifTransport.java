package fr.efrei.transfret;

/**
 * Strategy : tarif de base d'un mode de transport (avant suppléments, urgence et remise).
 */
public interface TarifTransport {

    double prixDeBase(DemandePrix demande);
}
