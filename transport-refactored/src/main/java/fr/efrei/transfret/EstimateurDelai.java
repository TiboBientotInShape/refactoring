package fr.efrei.transfret;

import java.util.Map;

/**
 * Estime le délai de livraison d'une expédition, en jours.
 */
public class EstimateurDelai {

    private static final int DELAI_MINIMUM_JOURS = 1;

    private final Map<ModeTransport, DelaiTransport> delais;

    public EstimateurDelai(Map<ModeTransport, DelaiTransport> delais) {
        this.delais = delais;
    }

    public int estimer(int distance, ModeTransport mode, boolean urgent) {
        DelaiTransport delai = delais.get(mode);
        if (delai == null) {
            throw new IllegalArgumentException("Pas de délai défini pour le mode " + mode);
        }
        return Math.max(delai.nombreDeJours(distance, urgent), DELAI_MINIMUM_JOURS);
    }
}
