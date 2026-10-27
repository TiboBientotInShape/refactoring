package fr.efrei.transfret;

import java.util.Random;

/**
 * Produit les identifiants d'expédition : un compteur suivi d'un suffixe aléatoire.
 */
public class GenerateurIdentifiant {

    private static final int SUFFIXE_MIN = 1000;
    private static final int SUFFIXE_AMPLITUDE = 9000;

    private final Random hasard = new Random();
    private int compteur = 0;

    public String suivant() {
        compteur++;
        return "EXP-" + compteur + "-" + (SUFFIXE_MIN + hasard.nextInt(SUFFIXE_AMPLITUDE));
    }
}
