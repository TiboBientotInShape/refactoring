package fr.efrei.transfret;

import java.util.List;
import java.util.Optional;

/**
 * Vérifie qu'une demande d'expédition respecte les règles d'acceptation.
 */
public class ValidateurExpedition {

    /** @return le motif du refus, ou vide si la demande est acceptable */
    public Optional<String> valider(Client client, List<Colis> colis, ModeTransport mode, boolean urgent) {
        if (client == null) {
            return Optional.of("client obligatoire");
        }
        if (colis == null || colis.isEmpty()) {
            return Optional.of("au moins un colis est nécessaire");
        }
        for (Colis c : colis) {
            if (c.getPoids() <= 0) {
                return Optional.of("poids invalide pour " + c.getDescription());
            }
        }
        for (Colis c : colis) {
            if (!mode.accepte(c.getType())) {
                return Optional.of(c.getType().getMessageInterdiction() + " en transport " + mode.getLibelle());
            }
        }
        if (urgent && !mode.isUrgenceAutorisee()) {
            return Optional.of("pas d'option urgente pour le transport " + mode.getLibelle());
        }
        return Optional.empty();
    }
}
