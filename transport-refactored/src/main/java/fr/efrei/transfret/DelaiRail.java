package fr.efrei.transfret;

public class DelaiRail implements DelaiTransport {

    private static final double KM_PAR_JOUR = 1440.0;
    private static final int JOURS_SUPPLEMENTAIRES = 1;
    private static final int JOURS_GAGNES_SI_URGENT = 1;

    @Override
    public int nombreDeJours(int distance, boolean urgent) {
        int jours = (int) Math.ceil(distance / KM_PAR_JOUR) + JOURS_SUPPLEMENTAIRES;
        if (urgent) {
            jours = jours - JOURS_GAGNES_SI_URGENT;
        }
        return jours;
    }
}
