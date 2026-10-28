package fr.efrei.transfret;

/**
 * Interface commune au tarif de base et à tous les décorateurs (suppléments, remise, assurance).
 */
public interface CalculPrix {

    double calculer(DemandePrix demande);
}
