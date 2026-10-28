package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Ce que tous les véhicules ont en commun : capacité, chargement et disponibilité.
 * Chaque véhicule indique le mode qu'il assure et les marchandises qu'il accepte, et sait comment partir.
 */
public abstract class Vehicule {

    protected final String immatriculation;
    protected final double capaciteMax;
    protected final ModeTransport mode;
    protected final Set<TypeMarchandise> marchandisesAcceptees;
    protected double chargeActuelle;
    protected boolean disponible = true;
    protected List<Colis> chargement = new ArrayList<Colis>();

    protected Vehicule(String immatriculation, double capaciteMax, ModeTransport mode,
                       Set<TypeMarchandise> marchandisesAcceptees) {
        this.immatriculation = immatriculation;
        this.capaciteMax = capaciteMax;
        this.mode = mode;
        this.marchandisesAcceptees = marchandisesAcceptees;
    }

    public abstract void partir(int distanceKm);

    /** Puis-je prendre en charge ces colis, pour ce mode ? (la capacité est vérifiée à part) */
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport modeDemande) {
        if (modeDemande != mode) {
            return false;
        }
        for (Colis c : colis) {
            if (!marchandisesAcceptees.contains(c.getType())) {
                return false;
            }
        }
        return true;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public double getCapaciteMax() {
        return capaciteMax;
    }

    public double getChargeActuelle() {
        return chargeActuelle;
    }

    public double getCapaciteRestante() {
        return capaciteMax - chargeActuelle;
    }

    public ModeTransport getMode() {
        return mode;
    }

    public boolean estDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void charger(Colis colis) {
        if (chargeActuelle + colis.getPoids() > capaciteMax) {
            System.out.println("ERREUR : capacité dépassée pour " + immatriculation);
            return;
        }
        chargement.add(colis);
        chargeActuelle = chargeActuelle + colis.getPoids();
    }

    public void decharger(Colis colis) {
        if (chargement.remove(colis)) {
            chargeActuelle = chargeActuelle - colis.getPoids();
        }
    }

    protected boolean chargementContient(TypeMarchandise type) {
        for (Colis c : chargement) {
            if (c.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
