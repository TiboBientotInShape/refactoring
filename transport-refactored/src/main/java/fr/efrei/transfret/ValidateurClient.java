package fr.efrei.transfret;

import java.util.Optional;

/**
 * Vérifie qu'une demande de création de client est valide.
 */
public class ValidateurClient {

    private static final int LONGUEUR_SIRET = 14;

    /** @return le motif du refus, ou vide si le client est valide */
    public Optional<String> valider(String nom, String email, TypeClient type, String siret) {
        if (nom == null || nom.trim().length() == 0) {
            return Optional.of("nom obligatoire");
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return Optional.of("email invalide (" + email + ")");
        }
        if (type == null) {
            return Optional.of("type de client inconnu");
        }
        if (type.isSiretObligatoire() && (siret == null || siret.length() != LONGUEUR_SIRET)) {
            return Optional.of("SIRET obligatoire (14 chiffres) pour une entreprise");
        }
        return Optional.empty();
    }
}
