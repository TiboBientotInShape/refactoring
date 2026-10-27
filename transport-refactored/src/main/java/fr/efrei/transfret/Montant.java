package fr.efrei.transfret;

/**
 * Opérations communes sur les montants en euros.
 */
public final class Montant {

    private static final double CENTIMES_PAR_EURO = 100.0;

    private Montant() {
    }

    public static double arrondirAuCentime(double montant) {
        return Math.round(montant * CENTIMES_PAR_EURO) / CENTIMES_PAR_EURO;
    }
}
